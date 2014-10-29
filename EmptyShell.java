import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class EmptyShell extends Projectile {
	
//	bounce: whether or not the bullet has hit the ground once already
	protected boolean bounce;

// --------------------------------CONSTRUCTOR-------------------------------- //
	
	public EmptyShell(BufferedImage b, double x, double y, double xv, double yv){
		super(b, x, y, 5, 3, xv, 0, 0, 0, 0);
		bounce = false;
	}
	
	
// --------------------------------DRAW METHODS-------------------------------- //
		
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
	
// --------------------------------UPDATE-------------------------------- //
	
	public void update(double time){
		
		if(southC){
			if(v.getCY() > 0) v.setCY(-1 * Math.abs(v.getCY()));
		}
		else v.add(g.scale(time));
		if(eastC){
			if(v.getCX() > 0) v.setCX(-v.getCX());
		}
		if(westC){
			if(v.getCX() < 0) v.setCX(-v.getCX());
		}
		if(northC){
			if(v.getCY() < 0) v.setCY(Math.abs(v.getCY()));
		}
		if(northC && southC){
			v.setCY(0);
		}
		if(eastC && westC){
			v.setCX(0);
		}
		if(v.magnitude() > TERMINAL_VELOCITY){
			v = v.scale(TERMINAL_VELOCITY / v.magnitude());
		}
		
		v.add(g.scale(time));
		x += time * v.getCX();
		y += time * v.getCY();
	}

// --------------------------------GET/SET METHODS-------------------------------- //
	
	public boolean getBounce(){
		return bounce;
	}
}
