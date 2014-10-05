import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Player extends Movable{
	protected boolean isRightHeld = false;
	protected boolean isLeftHeld = false;
	protected boolean isUpHeld = false;
	
	public Player(BufferedImage b, int x, int y, int w, int h){
		super(b, x, y, w, h, true, 0, 0);
	}
	
	public void update(int time){
		if(yv > TERMINAL_VELOCITY) yv = TERMINAL_VELOCITY;
		if(!(isRightHeld && isLeftHeld)){
			if(isRightHeld){
				xv = 5;
			}
			else if(isLeftHeld){
				xv = -5;
			}
		}
		if(isUpHeld){
			if(southC) yv = -10;
		}
		if(southC){
			if(yv > 0) yv = 0;
		}
		else yv += time * GRAVITY;
		if(eastC){
			if(xv > 0) xv = 0;
		}
		if(westC){
			if(xv < 0) xv = 0;
		}
		if(northC){
			if(yv < 0) yv = 0;
		}

		x += time * xv;
		y += time * yv;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.black);
		g.fillOval(x, y, w, h);
	}
	
	public void keyPressed(KeyEvent e) {
	        int key = e.getKeyCode();

	        if (key == KeyEvent.VK_LEFT) {
	        	isLeftHeld = true;
	        	setXV(-5);
	        }
	        
	        if (key == KeyEvent.VK_RIGHT) {
	        	isRightHeld = true;
	        	setXV(5);
	        }
	        
	        if (key == KeyEvent.VK_UP) {
	        	isUpHeld = true;
	        	if(southC) setYV(-10);
	        }
	        
	        if (key == KeyEvent.VK_A) {
	        	isLeftHeld = true;
	        	setXV(-5);
	        }
	        
	        if (key == KeyEvent.VK_D) {
	        	isRightHeld = true;
	        	setXV(5);
	        }
	        
	        if (key == KeyEvent.VK_W) {
	        	isUpHeld = true;
	        	if(southC) setYV(-10);
	        }


	    }
	    
	    public void keyReleased(KeyEvent e) {
	        int key = e.getKeyCode();

	        if (key == KeyEvent.VK_LEFT) {
	        	isLeftHeld = false;
	        	setXV(0);
	        }
	        
	        if (key == KeyEvent.VK_RIGHT) {
	        	isRightHeld = false;
	        	setXV(0);
	        }
	        
	        if (key == KeyEvent.VK_UP) {
	        	isUpHeld = false;
	        }
	        
	        if (key == KeyEvent.VK_A) {
	        	isLeftHeld = false;
	        	setXV(0);
	        }
	        
	        if (key == KeyEvent.VK_D) {
	        	isRightHeld = false;
	        	setXV(0);
	        }
	        
	        if (key == KeyEvent.VK_W) {
	        	isUpHeld = false;
	        }

	}



}