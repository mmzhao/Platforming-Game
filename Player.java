import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Player extends Movable{
<<<<<<< HEAD
	//implement some more attributes
	private boolean hasWeapon;
	private ArrayList<Item> toolbelt;
	private Weapon currentWeapon;
=======
	protected boolean isRightHeld = false;
	protected boolean isLeftHeld = false;
	protected boolean isUpHeld = false;
>>>>>>> 822245ec0429d6d64d4a4a5b6c9dff0609958c4e
	
	public Player(BufferedImage b, int x, int y, int w, int h){
		super(b, x, y, w, h, true, 0, 0);
		hasWeapon = false;
		toolbelt = new ArrayList<>();
		currentWeapon = Null; 
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

	public void giveCurrentWeapon(Weapon w){
		hasWeapon = true;
		toolbelt.add(w);
		currentWeapon = w;
	}

}