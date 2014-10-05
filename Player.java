import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Player extends Movable{
	//implement some more attributes
	
	public Player(BufferedImage b, int x, int y, int w, int h){
		super(b, x, y, w, h, true, 0, 0);
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.black);
		g.drawOval(x, y, w, h);
	}
	
	public void keyPressed(KeyEvent e) {
	        int key = e.getKeyCode();

	        if (key == KeyEvent.VK_LEFT) {
	        	setXV(-5);
	        }
	        
	        if (key == KeyEvent.VK_RIGHT) {
	        	setXV(5);
	        }
	        
	        if (key == KeyEvent.VK_UP) {
	        	setYV(10);
	        }
	        
	        if (key == KeyEvent.VK_A) {
	        	setXV(-5);
	        }
	        
	        if (key == KeyEvent.VK_D) {
	        	setXV(5);
	        }
	        
	        if (key == KeyEvent.VK_W) {
	        	setYV(10);
	        }


	    }
	    
	    public void keyReleased(KeyEvent e) {
	        int key = e.getKeyCode();

	        if (key == KeyEvent.VK_LEFT) {
	        	setXV(0);
	        }
	        
	        if (key == KeyEvent.VK_RIGHT) {
	        	setXV(0);
	        }
	        
	        if (key == KeyEvent.VK_A) {
	        	setXV(0);
	        }
	        
	        if (key == KeyEvent.VK_D) {
	        	setXV(0);
	        }

	}



}