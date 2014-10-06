import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class Baddie extends Movable{

	public Baddie(BufferedImage b, double x, double y, double w, double h, boolean c, double xv, double yv) {
		super(b, x, y, w, h, true, xv, yv);
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.red);
		g.fillOval((int) x, (int) y, (int) w, (int) h);
	}
	
	public void update(double time){
		double tempXV = xv;
		if(!southC){
			xv = 0;
		}
		if(yv > TERMINAL_VELOCITY) yv = TERMINAL_VELOCITY;
		if(southC){
			if(yv > 0) yv = 0;
		}
		else yv += time * GRAVITY;
		if(eastC){
			if(xv > 0) xv = -xv;
		}
		if(westC){
			if(xv < 0) xv = -xv;
		}
		if(northC){
			if(yv < 0) yv = 0;
		}

		x += time * xv;
		y += time * yv;
		if(xv == 0) xv = tempXV;
	}
	
	
}
