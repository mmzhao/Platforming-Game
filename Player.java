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

	protected Vector a;

	// isRightHeld: whether or not right move button is held
	// isLeftHeld: whether or not left move button is held
	// isUpHeld: whether or not up move button is held
	// isHit: whether or not player is currently collided with a baddie
	// isShooting: whether or not the player is currently firing his weapon
	// mouseX: x position (420 by 330) of the mouse relative to the game panel
	// mouseY: y position (420 by 330) of the mouse relative to the game panel
	// realMouseX: actual on screen x position of the mouse relative to the game
	// panel
	// realMouseY: actual on screen y position of the mouse relative to the game
	// panel
	// hitTimer: timer that keeps track of most recent time the player has been
	// hit by a baddie
	protected boolean isRightHeld = false;
	protected boolean isLeftHeld = false;
	protected boolean isUpHeld = false;
	protected boolean isHit = false;
	protected boolean isAttacking = false;
	protected double mouseX = 0;
	protected double mouseY = 0;
	protected double realMouseX = 0;
	protected double realMouseY = 0;
	protected long hitTimer = 0;

	// runningAni: animation for when the player is running
	// standingImg: image used when the player is standing still
	// currentImg: image that should be currently drawn to the screen
	protected Animation runningAni;
	protected BufferedImage standingImg;
	protected BufferedImage currentImg;

	// currentWeapon: weapon that the player is currently using
	// standardStep: magnitude of velocity player gets when presses left or
	// right move keys
	// accelSpeed: a measure of how past the player will accelerate to standard
	// step speed
	// isRightPressed: whether or not the right move key is pressed this frame
	// isLeftPressed: whether or not the left move key is pressed this frame
	protected Weapon currentWeapon;
	protected double standardStep;
	protected double accelSpeed;
	protected boolean isRightPressed = false;
	protected boolean isLeftPressed = false;

	// AIR_RESISTANCE: factor by which movement is reduced in air
	private final double AIR_RESISTANCE = .5;
	protected Vector g = new Vector(0, .5);
	protected Vector jump = new Vector(0, -18);
	
	//
	private int runningdir = 0;
	
	protected final double minChange = 1;

	// --------------------------------CONSTRUCTOR-------------------------------- //

	public Player(BufferedImage b, double x, double y, double w, double h,
			int health, Weapon currentWeapon) {
		super(b, x, y, w, h, true, 0, 0, health, 1);
		a = new Vector(0, 0);
		standingImg = b;
		currentImg = b;
		this.currentWeapon = currentWeapon;
		standardStep = 7; //only reaches ~4 when standard step is 5
		accelSpeed = .04;
		runningAni = new Animation("Running2.svg", 1171, 474, 6, 2, .2);
	}

	// --------------------------------DRAW METHODS-------------------------------- //

	public void draw(Graphics g) {
		draw(g, 0, 0);
	}

	public void draw(Graphics g, int offsetX, int offsetY) {
		draw(g, offsetX, offsetY, 1, 1);
	}

	public void draw(Graphics g, int offsetX, int offsetY, double scaleX,
			double scaleY) {
		// MAKE HIT COLOR CHANGE FRAMES SOMETIME
		// making dot to designate facing direction
		g.drawOval((int) ((mouseX - offsetX) * scaleX - 1),
				(int) ((mouseY - offsetY) * scaleY - 1), 2, 2);
		if (facingRight == 1) {
			g.drawImage(currentImg, (int) ((x - offsetX) * scaleX),
					(int) ((y - offsetY) * scaleY), (int) (w * scaleX),
					(int) (h * scaleY), null, null);
		} else {
			g.drawImage(currentImg, (int) ((x + (int) w - offsetX) * scaleX),
					(int) ((y - offsetY) * scaleY), -(int) (w * scaleX),
					(int) (h * scaleY), null, null);
		}
		
		g.setColor(Color.green);
		g.drawRect((int) ((x - offsetX) * scaleX), (int) ((y - offsetY) * scaleY), (int) (w * scaleX), (int) (h * scaleY));

		if (currentWeapon != null) {
			if(currentWeapon instanceof RangedWeapon){
				for (int i = 0; i < ((RangedWeapon) currentWeapon).getProjectiles().size(); i++) {
					((RangedWeapon) currentWeapon).getProjectiles().get(i).draw(g, offsetX, offsetY, scaleX, scaleY);
				}
			}
			currentWeapon.draw(g, offsetX, offsetY, scaleX, scaleY);
		}
	}

	// --------------------------------UPDATE-------------------------------- //

	public void update(double time) {
		setMousePos();
//		System.out.println(isHit + " " + health);
		if(System.currentTimeMillis() > hitTimer + 1000){
			isHit = false;
		}
		setA(new Vector(0, 0));

		if (runningAni.getStarted()) {
			currentImg = runningAni.loop(time);
		} else {
			currentImg = standingImg;
		}
		
		if (isRightHeld && !isLeftHeld) {
			runningdir = -1;
			a.add(completeAccel());
			if (southC)
				runningAni.start();
		} else if (isLeftHeld && !isRightHeld) {
			runningdir = 1;
			a.add(completeAccel().scale(-1));
			if (southC)
				runningAni.start();
		}
		
		
		facingRight = 1;
		if(mouseX - x < 0)
			facingRight = -1;
		
		if (a.magnitude() == 0) {
			if (southC) {
				a.add(stoppingFriction().scale(-(v.getCX() + .001) / Math.abs(v.getCX() + .001)));
			} else {
				a.add(stoppingFriction().scale(-.1 * (v.getCX() + .001) / Math.abs(v.getCX() + .001)));
			}
		}
		
		if (southC) {
			a.add(normalForce());
			if (v.getCY() > 0)
				v.setCY(0);
			if (isUpHeld) {
				a.add(jump); // if this line not commented out, can jump up platform
							// uppersides
				// currently 1 less magnitude than "space key" jump velocity
			}
		} 
		a.add(g);
		
//		a.print();
		
		v.add(a.scale(time));

		
		if(eastC){
			if(v.getCX() > 0) v.setCX(0);
		}
		if(westC){
			if(v.getCX() < 0) v.setCX(0);
		}
		if(northC){
			if(v.getCY() < 0) v.setCY(0);
		}
		if(v.magnitude() > TERMINAL_VELOCITY){
			v = v.scale(TERMINAL_VELOCITY / v.magnitude());
		}
		
		if(!(isRightHeld || isLeftHeld) && Math.abs(v.getCX() * time) < 1){
			v.setCX(0);
		}
		
//		a.print();
		
		//don't move if not enough change, also implemented in weapon
		double newX = x + time * v.getCX();
		double newY = y + time * v.getCY();
		if(Math.abs(newX - x) > minChange){
//			System.out.println("x change: " + Math.abs(newX - x));
			x = newX;
		}
		if(Math.abs(newY - y) > minChange){
//			System.out.println("y change: " + Math.abs(newY - y));
			y = newY;
		}
//		System.out.println();
		

		if (isAttacking) {
			currentWeapon.attack();
		}
		if (currentWeapon != null) {
			currentWeapon.update(time);
		}
		// update projectile list

		isRightPressed = false;
		isLeftPressed = false;
	}
	
	// --------------------------------FORCE METHODS-------------------------------- //

	public Vector completeAccel() {// (C1 - v)^2/C1*C2
		double accel = (standardStep + runningdir * v.getCX()) * accelSpeed;
		if(accel > 0) return new Vector(accel, 0);
		return new Vector(0, 0);
		// return (double)(Math.pow(standardStep - Math.abs(xv), 2)/
		// standardStep / accelSpeed);
	}

	public Vector stoppingFriction() {// (v^2 - 2v*C1)/C1*C2
		if(isLeftHeld || isRightHeld){
			return new Vector((Math.abs(v.getCX())) * accelSpeed, 0);
		}
		return new Vector((Math.abs(v.getCX())) * accelSpeed * 5, 0);
	}
	
	public Vector normalForce(){
		return g.scale(-1);
	}
	

	// --------------------------------COLLISION METHODS-------------------------------- //

	public void resetCollisionState() {
		northC = false;
		eastC = false;
		southC = false;
		westC = false;
		isHit = false;
	}

	public void updateC(double time) {
		//don't move if not enough change, also implemented in weapon
		x += time * v.getCX();
		y += time * v.getCY();
		
	}

	// --------------------------------GET/SET METHODS-------------------------------- //

	public Vector getA(){
		return a;
	}
	
	public void setA(Vector a){
		this.a = a;
	}
	
	public void setXA(double xa){
		a.setCX(xa);
	}
	
	public void setYA(double ya){
		a.setCY(ya);
	}
	
	public Weapon getCurrentWeapon() {
		return currentWeapon;
	}

	public void giveCurrentWeapon(Weapon w) {
		currentWeapon = w;
		w.setOwner(this);
	}

	public void setHitTimer(long t) {
		hitTimer = t;
	}

	public boolean isHit() {
		return isHit;
	}

	public void setIsHit(boolean hit) {
		isHit = hit;
	}

	public double getMouseX() {
		return mouseX;
	}

	public double getMouseY() {
		return mouseY;
	}

	public void setMousePos() {
		mouseX = ((double) realMouseX) / GamePanel.getScaleX()
				+ GamePanel.getOffsetX();
		mouseY = ((double) realMouseY) / GamePanel.getScaleY()
				+ GamePanel.getOffsetY();
	}

	// --------------------------------KEY/MOUSE LISTENER METHODS-------------------------------- //

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		// if (key == KeyEvent.VK_LEFT) {
		// isLeftHeld = true;
		// isLeftPressed = true;
		// setXV(-5);
		//
		// }
		//
		// else if (key == KeyEvent.VK_RIGHT) {
		// isRightHeld = true;
		// isRightPressed = true;
		// setXV(5);
		// }
		//
		// else if (key == KeyEvent.VK_UP) {
		// isUpHeld = true;
		// if (southC)
		// setYV(-10);
		// }

		if (key == KeyEvent.VK_A) {
			isLeftHeld = true;
			isLeftPressed = true;
			isRightHeld = false;
			isLeftPressed = false;
			// setXV(-5);
		}

		else if (key == KeyEvent.VK_D) {
			isRightHeld = true;
			isRightPressed = true;
			isLeftHeld = false;
			isLeftPressed = true;
			// setXV(5);
		}

		else if (key == KeyEvent.VK_W) {
			isUpHeld = true;
			if (southC)
				v.setCY(-10);
		}
		
		else if (key == KeyEvent.VK_T) {
			if (currentWeapon != null && currentWeapon instanceof RangedWeapon)
				((RangedWeapon) currentWeapon).reload();
			// shoot();
		}

	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		// if (key == KeyEvent.VK_LEFT) {
		// runningAni.stop();
		// isLeftHeld = false;
		// setXV(0);
		// }
		//
		// else if (key == KeyEvent.VK_RIGHT) {
		// runningAni.stop();
		// isRightHeld = false;
		// setXV(0);
		// }
		//
		// else if (key == KeyEvent.VK_UP) {
		// isUpHeld = false;
		// }

		if (key == KeyEvent.VK_A) {
			runningAni.stop();
			isLeftHeld = false;
			// setXV(0);
		}

		else if (key == KeyEvent.VK_D) {
			runningAni.stop();
			isRightHeld = false;
			// setXV(0);
		}

		else if (key == KeyEvent.VK_W) {
			isUpHeld = false;
		}

		// else if (key == KeyEvent.VK_SPACE) {
		// isShooting = false;
		// }

	}

	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (currentWeapon != null)
				isAttacking = true;
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			isAttacking = false;
		}
	}

	public void mouseDragged(MouseEvent e) {
		realMouseX = e.getX();
		realMouseY = e.getY();
		setMousePos();
	}

	public void mouseMoved(MouseEvent e) {
		realMouseX = e.getX();
		realMouseY = e.getY();
		setMousePos();
	}

}