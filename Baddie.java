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
	
	public void sidesCollided(ArrayList<Entity> es) {
		saveCurrentState();
		northC = false;
		eastC = false;
		southC = false;
		westC = false;
		updateC();
		for (Entity e : es) { //I check for projectile collision in projectile
/**			if (e instanceof Projectile) {
				System.out.println("shot the pleebs");
				Rectangle r1 = new Rectangle((int) e.getX(), (int) e.getY(),
						(int) e.getW(), (int) e.getH());
				Rectangle r2 = new Rectangle((int) x, (int) y, (int) w, (int) h);
				if (r1.intersects(r2)){
					System.out.println("poosies");
					health -= ((Projectile)e).getDamage();
					e.kill();
				}
			}**/
			if (e instanceof Platform) {
				Rectangle r1 = new Rectangle((int) e.getX(), (int) e.getY(),
						(int) e.getW(), (int) e.getH());
				Rectangle r2 = new Rectangle((int) x, (int) y, (int) w, (int) h);
				if (r1.intersects(r2)) {
//					Rectangle inter = r1.intersection(r2);
					double distx = Math.abs(save.getMidX() - e.getMidX()) - save.getW() / 2 - e.getW() / 2;
					double disty = Math.abs(save.getMidY() - e.getMidY()) - save.getH() / 2 - e.getH() / 2;
//					System.out.println("x: " + distx + "\n" + "y: " + disty);
					if(distx < 0 && disty < 0){
//						System.out.println("plz kill yourself");
						if(distx > disty){
							distx = 0;
						}
						else
							disty = 0;
//						System.out.println("x: " + distx + "    y: " + disty);
					}
//					System.out.println("x: " + distx + "\n" + "y: " + disty + "\n");
					if(distx < 0){
						if(save.getMidY() > e.getMidY()){
							northC = true;
						}
						else{
							southC = true;
						}
					}
					else if(disty < 0){
						if(save.getMidX() > e.getMidX()){
							westC = true;
						}
						else{
							eastC = true;
						}
					}
					else{
						if(distx / save.getXV() < 0 || disty / save.getYV() < 0) System.out.println("plz kill yourself");
						if(distx / save.getXV() < disty / save.getYV()){
							if(save.getMidY() > e.getMidY()){
								northC = true;
							}
							else{
								southC = true;
							}
						}
						else{
							if(save.getMidX() > e.getMidX()){
								westC = true;
							}
							else{
								eastC = true;
							}
						}
					}
				}
			}
		}
		reset();
	}
	
	public void sidesCollided2(ArrayList<Entity> es){
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
//					System.out.println("poosies");
					health -= ((Projectile)e).getDamage();
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
