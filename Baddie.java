import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Baddie extends Movable{
	
	private BufferedImage current;
	private Animation move;
	
	public Baddie(BufferedImage b, double x, double y, double w, double h, boolean c, double xv, double yv, int health) {
		super(b, x, y, w, h, true, xv, yv, health, 0);
		move = new Animation("shroom run.png", 5, 1, 10);
		move.start();
		current = null;
	}

	
	public void draw(Graphics g) {
		draw(g, 0, 0);
	}
	
	public void draw(Graphics g, int offsetX, int offsetY){
		draw(g, offsetX, offsetY, 1, 1);
	}
	
	public void draw(Graphics g, int offsetX, int offsetY, double scaleX, double scaleY){
		g.drawImage(current, (int)((x - offsetX) * scaleX), (int) ((y - offsetY) * scaleY), (int) (w * scaleX), (int) (h * scaleY), null);
	}
	
	public void update(double time){
//		System.out.println(health);
		if(!isAlive()){ // change to death later this is for the lolz
			kill();
			return;
//			health = (int) (w + h);
//			if(southC){
//				y -= .1 * h;
//			}
//			if(eastC){
//				x -= .1 * w;
//			}
//			w += .1 * w;
//			h += .1 * h;
		}
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
		current = move.loop(time);
	}

	
}
