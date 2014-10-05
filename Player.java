import java.awt.event.KeyEvent;

public class Player extends Movable{
	//implement some more attributes
	
	public Player(double x, double y, double w, double h, boolean c, double xv, double yv, double xa, double ya){
		super(x, y, w, h, c, xv, yv, xa, ya, false);
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
	        
	        if (key == KeyEvent.VK_LEFT) {
	        	setXV(0);
	        }
	        
	        if (key == KeyEvent.VK_RIGHT) {
	        	setXV(0);
	        }

	}



}