import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


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
	
	public void sidesCollided(ArrayList<Entity> es){
		saveCurrentState();
		northC = false;
		eastC = false;
		southC = false;
		westC= false;
		updateC();
		for(Entity e: es){
			if(this == e) continue;
			Side s = collision(e);
			if(e instanceof Projectile){
				if(s != Side.NONE){
					//take dmg etc
				}
			}
			else if(e instanceof Platform){
				if(s == Side.NORTH) northC = true;
				else if(s == Side.EAST) eastC = true;
				else if(s == Side.SOUTH) southC = true;
				else if(s == Side.WEST) westC = true;
				else if(s == Side.NORTHEAST){
					northC = true;
					eastC = true;
				}
				else if(s == Side.NORTHWEST){
					northC = true;
					westC = true;
				}
				else if(s == Side.SOUTHEAST){
					southC = true;
					eastC = true;
				}
				else if(s == Side.SOUTHWEST){
					southC = true;
					westC = true;
				}
			}
		}
		reset();
	}
	
	
}
