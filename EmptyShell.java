import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class EmptyShell extends Projectile {
	protected boolean bounce;
	
	public EmptyShell(BufferedImage b, double x, double y, double xv, double yv){
		super(b, x, y, 5, 3, xv, 0, 0, 0, 0);
		bounce = false;
	}
	
	public void update(double time){
		if(xv > TERMINAL_VELOCITY) xv = TERMINAL_VELOCITY;
		if(yv > TERMINAL_VELOCITY) yv = TERMINAL_VELOCITY;
		if(northC && southC){
			yv = 0;
		}
		else if(southC){
				yv = -1 * Math.abs(yv);
				bounce = true;
		}
		else if(northC){
				yv =  1 * Math.abs(yv);
		}
		
		if(eastC && westC){
			xv = 0;
		}
		else if(eastC){
				xv = -1 * Math.abs(xv);
		}
		else if(westC){
				xv =  1 * Math.abs(xv);
		}
		x += time * xv;
		xv += time * xa;
		yv += time*GRAVITY;
		y += yv;
	}
	
	public void draw(Graphics g) {
		draw(g, 0, 0);
	}
	
	public void draw(Graphics g, int offsetX, int offsetY) {
		draw(g, offsetX, offsetY, 1, 1);
	}
	
	public void draw(Graphics g, int offsetX, int offsetY, double scaleX, double scaleY) {
		g.setColor(Color.yellow);
		g.fillRect((int)((x - offsetX) * scaleX), (int) ((y - offsetY) * scaleY), (int) (w * scaleX), (int) (h * scaleY));
		g.setColor(Color.black);
		g.drawRect((int)((x - offsetX) * scaleX), (int) ((y - offsetY) * scaleY), (int) (w * scaleX), (int) (h * scaleY));
	}
	
	
	public boolean getBounce(){
		return bounce;
	}
}
