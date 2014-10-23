import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Player extends Movable {
	
//	isRightHeld: whether or not right move button is held
//	isLeftHeld: whether or not left move button is held
//	isUpHeld: whether or not up move button is held
//	isHit: whether or not player is currently collided with a baddie
//	isShooting: whether or not the player is currently firing his weapon
//	mouseX: x position (420 by 330) of the mouse relative to the game panel
//	mouseY: y position (420 by 330) of the mouse relative to the game panel
//	realMouseX: actual on screen x position of the mouse relative to the game panel
//	realMouseY: actual on screen y position of the mouse relative to the game panel
//	hitTimer: timer that keeps track of most recent time the player has been hit by a baddie
	protected boolean isRightHeld = false;
	protected boolean isLeftHeld = false;
	protected boolean isUpHeld = false;
	protected boolean isHit = false;
	protected boolean isShooting = false;
	protected double mouseX = 0;
	protected double mouseY = 0;
	protected double realMouseX = 0;
	protected double realMouseY = 0;
	protected long hitTimer = 0;

//	runningAni: animation for when the player is running
//	standingImg: image used when the player is standing still
//	currentImg: image that should be currently drawn to the screen
	protected Animation runningAni;
	protected BufferedImage standingImg;
	protected BufferedImage currentImg;

//	currentWeapon: weapon that the player is currently using
//	standardStep: magnitude of velocity player gets when presses left or right move keys
//	isRightPressed: whether or not the right move key is pressed this frame
//	isLeftPressed: whether or not the left move key is pressed this frame
	protected Weapon currentWeapon;
	protected int standardStep;
	protected boolean isRightPressed = false;
	protected boolean isLeftPressed = false;

//	AIR_RESISTANCE: factor by which movement is reduced in air
	private final double AIR_RESISTANCE = .5;

// --------------------------------CONSTRUCTOR-------------------------------- //
	
	public Player(BufferedImage b, double x, double y, double w, double h, int health, Weapon currentWeapon) {
		super(b, x, y, w, h, true, 0, 0, health, 1);
		standingImg = b;
		currentImg = b;
		this.currentWeapon = currentWeapon;
		standardStep = 5;
		runningAni = new Animation("Running.png", 6, 2, 3);
	}

	public Player(BufferedImage b, double x, double y, double w, double h,
			double xv, double yv) {
		super(b, x, y, w, h, xv, yv);
		currentWeapon = null;
		standardStep = 5;
	}
	
// --------------------------------DRAW METHODS-------------------------------- //
	
	public void draw(Graphics g){
		draw(g, 0, 0);
	}
	
	public void draw(Graphics g, int offsetX, int offsetY){
		draw(g, offsetX, offsetY, 1, 1);
	}
	
	public void draw(Graphics g, int offsetX, int offsetY, double scaleX, double scaleY){
		// MAKE HIT COLOR CHANGE FRAMES SOMETIME
		// making dot to designate facing direction
		g.drawOval((int)((mouseX - offsetX) * scaleX - 1), (int)((mouseY - offsetY) * scaleY - 1), 2, 2);
		if (facingRight == 1) {
			g.drawImage(currentImg, (int) ((x - offsetX) * scaleX), (int) ((y - offsetY) * scaleY), (int) (w * scaleX), (int) (h * scaleY), null, null);
		} else{
			g.drawImage(currentImg, (int)((x + (int)w - offsetX) * scaleX), (int)((y - offsetY) * scaleY), -(int) (w * scaleX), (int) (h * scaleY), null, null);
		}
				
		if(currentWeapon != null){
			for(int i = 0; i < currentWeapon.getProjectiles().size(); i++){
				currentWeapon.getProjectiles().get(i).draw(g, offsetX, offsetY, scaleX, scaleY);
			}
			currentWeapon.draw(g, offsetX, offsetY, scaleX, scaleY);
		}
	}

// --------------------------------UPDATE-------------------------------- //
	
	public void update(double time) {
//		System.out.println(mouseX + " " + mouseY);
		setMousePos();
		
		if(runningAni.getStarted()){
			currentImg = runningAni.loop(time);
		}
		else{
			currentImg = standingImg;
		}
		if(!(isRightPressed && isLeftPressed)){
			if(isRightPressed && !isLeftPressed){
				facingRight = 1;
			}
			else if(isLeftPressed && !isRightPressed){
				facingRight = -1;
			}
		}
		if (!(isRightHeld && isLeftHeld)) {
			if (isRightHeld && !isLeftHeld) {
				xv = standardStep;
				facingRight = 1;
				if(southC)
					runningAni.start();
			} else if (isLeftHeld && !isRightHeld) {
				xv = -standardStep;
				facingRight = -1;
				if(southC)
					runningAni.start();
			}
		}
		if (southC) {
			if (yv > 0)
				yv = 0;
			if(isUpHeld){
				yv = -9; //if this line not commented out, can jump up platform uppersides
				//currently 1 less magnitude than "space key" jump velocity
			}
		} else
			yv += time * GRAVITY;
		if (eastC) {
			if (xv > 0)
				xv = 0;
		}
		if (westC) {
			if (xv < 0)
				xv = 0;
		}
		if (northC) { //FIX THIS
			if (yv < 0)
				yv = 0;
		}
		if (yv > TERMINAL_VELOCITY)
			yv = TERMINAL_VELOCITY;
		if(!southC){
			x += time * xv * .8;
		}
		else{
			x += time * xv;
		}
		y += time * yv;
		
		if (isShooting){
			currentWeapon.fire();
		}
		if(currentWeapon != null){
			currentWeapon.update(time);
		}
		// update projectile list
		
		isRightPressed = false;
		isLeftPressed = false;
	}
	
// --------------------------------COLLISION METHODS-------------------------------- //
	
	public void resetCollisionState(){
		northC = false;
		eastC = false;
		southC = false;
		westC = false;
		isHit = false;
	}

	public void updateC(double time) {
		// System.out.println(xv + " " + yv);
		yv += time * GRAVITY;
		if (yv > TERMINAL_VELOCITY)
			yv = TERMINAL_VELOCITY;
		if (!(isRightHeld && isLeftHeld)) {
			if (isRightHeld) {
				xv = 5;
			} else if (isLeftHeld) {
				xv = -5;
			}
		}
		x += time * xv;
		y += time * yv;
	}

// --------------------------------GET/SET METHODS-------------------------------- //
	
	public Weapon getCurrentWeapon(){
		return currentWeapon;
	}
	
	public void giveCurrentWeapon(Weapon w){
		currentWeapon = w;
		w.setOwner(this);
	}
	
	public void setHitTimer(long t){
		hitTimer = t;
	}
	
	public boolean isHit(){
		return isHit;
	}
	
	public void setIsHit(boolean hit){
		isHit = hit;
	}


	public double getMouseX(){
		return mouseX;
	}
	
	public double getMouseY(){
		return mouseY;
	}
	
	public void setMousePos(){
		mouseX = ((double) realMouseX) / GamePanel.getScaleX() + GamePanel.getOffsetX();
		mouseY = ((double) realMouseY) / GamePanel.getScaleY() + GamePanel.getOffsetY();
	}

// --------------------------------KEY/MOUSE LISTENER METHODS-------------------------------- //
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
//		if (key == KeyEvent.VK_LEFT) {
//			isLeftHeld = true;
//			isLeftPressed = true;
//			setXV(-5);
//
//		}
//
//		else if (key == KeyEvent.VK_RIGHT) {
//			isRightHeld = true;
//			isRightPressed = true;
//			setXV(5);
//		}
//
//		else if (key == KeyEvent.VK_UP) {
//			isUpHeld = true;
//			if (southC)
//				setYV(-10);
//		}

		if (key == KeyEvent.VK_A) {
			isLeftHeld = true;
			isLeftPressed = true;
			setXV(-5);
		}

		else if (key == KeyEvent.VK_D) {
			isRightHeld = true;
			isRightPressed = true;
			setXV(5);
		}

		else if (key == KeyEvent.VK_W) {
			isUpHeld = true;
			if (southC)
				setYV(-10);
		}
//
//		else if (key == KeyEvent.VK_SPACE) {
//			if(currentWeapon != null)
//				isShooting = true;
////			shoot();
//		}
		
		else if (key == KeyEvent.VK_T) {
			if(currentWeapon != null)
				currentWeapon.reload();
//			shoot();
		}

	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

//		if (key == KeyEvent.VK_LEFT) {
//			runningAni.stop();
//			isLeftHeld = false;
//			setXV(0);
//		}
//
//		else if (key == KeyEvent.VK_RIGHT) {
//			runningAni.stop();
//			isRightHeld = false;
//			setXV(0);
//		}
//
//		else if (key == KeyEvent.VK_UP) {
//			isUpHeld = false;
//		}

		if (key == KeyEvent.VK_A) {
			runningAni.stop();
			isLeftHeld = false;
			setXV(0);
		}

		else if (key == KeyEvent.VK_D) {
			runningAni.stop();
			isRightHeld = false;
			setXV(0);
		}

		else if (key == KeyEvent.VK_W) {
			isUpHeld = false;
		}

//		else if (key == KeyEvent.VK_SPACE) {
//			isShooting = false;
//		}

	}
	
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1){
			if(currentWeapon != null)
				isShooting = true;
		}
	}

	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1){
			isShooting = false;
		}
	}
	
	public void mouseDragged(MouseEvent e){
		realMouseX = e.getX();
		realMouseY = e.getY();
		setMousePos();
	}
	
	public void mouseMoved(MouseEvent e){
		realMouseX = e.getX();
		realMouseY = e.getY();
		setMousePos();
	}

}