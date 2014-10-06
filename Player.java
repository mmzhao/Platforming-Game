import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Movable{
	protected boolean isRightHeld = false;
	protected boolean isLeftHeld = false;
	protected boolean isUpHeld = false;
	protected boolean isHit = false;
	protected long hitTimer = 0;
	
	public Player(BufferedImage b, int x, int y, int w, int h){
		super(b, x, y, w, h, true, 0, 0);
	}
	
	public void sidesCollided(ArrayList<Entity> es){
		northC = false;
		eastC = false;
		southC = false;
		westC= false;
		isHit = false;
		for(Entity e: es){
			Side s = collision(e);
			if(s == Side.NORTH) northC = true;
			else if(s == Side.EAST) eastC = true;
			else if(s == Side.SOUTH) southC = true;
			else if(s == Side.WEST) westC = true;
			else if(s == Side.BADDIE){
				//take dmg, flinch, etc
				//FIX SOMETIME WON'T HIT UNTIL JUMP
				System.out.println("hit");
				isHit = true;
				hitTimer = System.currentTimeMillis();
			}
		}
	}
	
	public Side collision(Entity e) {
		if(e instanceof Baddie){
			double ew = intersect(e.getX(), e.getX() + e.getW(), x, x + w); // east west intersection
			double ns = intersect(e.getY(), e.getY() + e.getH(), y, y + h); // north south interaction
			if (ns > 0 && ew > 0)
				return Side.BADDIE;
			return Side.NONE;
		}
		if (e instanceof Platform) {
			double ew = intersect(e.getX(), e.getX() + e.getW(), x, x + w); // east west intersection
			double ns = intersect(e.getY(), e.getY() + e.getH(), y, y + h); // north south interaction
			Side nsOption = Side.NORTH;
			Side ewOption = Side.WEST;
			if (e.getMidX() > getMidX())
				ewOption = Side.EAST;
			if (e.getMidY() > getMidY())
				nsOption = Side.SOUTH;
			
			if(Math.abs(e.getMidY() - getMidY()) - (e.getH() + h) / 2 <= 0 && Math.abs(e.getMidX() - getMidX()) - (e.getW() + w) / 2 <= 0){
				if(x > e.getX() && x < e.getX() + e.getW()) return nsOption;
				return ewOption;
			}
		}
		return Side.NONE;
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
		long currentTime = System.currentTimeMillis();
		if(currentTime - hitTimer < 150){
			g.setColor(Color.pink);
		}
		else
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