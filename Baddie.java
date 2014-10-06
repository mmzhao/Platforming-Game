import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class Baddie extends Movable{

	public Baddie(BufferedImage b, int x, int y, int w, int h, boolean c, int xv,int yv) {
		super(b, x, y, w, h, true, xv, yv);
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.red);
		g.fillOval(x, y, w, h);
	}
	
	public void update(int time){
		int tempXV = xv;
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
