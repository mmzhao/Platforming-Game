import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Movable extends Entity{

	protected double xv;
	protected double yv;
	protected boolean southC;
	protected boolean eastC;
	protected boolean westC;
	protected boolean northC;
	protected int health;
	protected int facingRight;
	//IMPLEMENT TAKE DMG SOMETIME
	
	protected Movable save = null; // for better collision detection

	protected final double GRAVITY = 1; //positive acceleration goes SOUTH and EAST
	protected final double TIME_UNIT = 1;
	protected final double TERMINAL_VELOCITY = 5;

	public Movable(BufferedImage b, double x, double y, double w, double h, boolean c, double xv, double yv, int health, int facingRight){
		super(b, x, y, w, h, c);
		this.facingRight = facingRight;
		this.xv = xv;
		this.yv = yv;
		this.health = health;
		save = new Movable(b, x, y, w, h, xv, yv);
	}
	
	public Movable(BufferedImage b, double x, double y, double w, double h, double xv, double yv){
		super(b, x, y, w, h, true);
		this.xv = xv;
		this.yv = yv;
	}
	
	public void draw(Graphics g, int offsetX, int offsetY, double scaleX, double scaleY){
		
	}
	
	public void draw(Graphics g, int offsetX, int offsetY){
		
	}
	
	public void draw(Graphics g) {
		draw(g, 0, 0);
	}

	public void update(){
		update(TIME_UNIT);
	}

	public void update(double time){
		if(!isAlive()){
			remove = true;
			return;
		}
		if(yv > TERMINAL_VELOCITY) yv = TERMINAL_VELOCITY;
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
	
	public void resetCollisionState(){
		northC = false;
		eastC = false;
		southC = false;
		westC = false;
	}
	
	public void sidesCollided(ArrayList<Entity> es) {
		saveCurrentState();
		northC = false;
		eastC = false;
		southC = false;
		westC = false;
		updateC();
		for (Entity e : es) {
			if(this == e) continue;
			if(e.getC() && collidable){
				Rectangle r1 = new Rectangle((int) e.getX(), (int) e.getY(),
						(int) e.getW(), (int) e.getH());
				Rectangle r2 = new Rectangle((int) x, (int) y, (int) w, (int) h);
				if (r1.intersects(r2)) {
//					Rectangle inter = r1.intersection(r2);
					double distx = Math.abs(save.getMidX() - e.getMidX()) - save.getW() / 2 - e.getW() / 2;
					double disty = Math.abs(save.getMidY() - e.getMidY()) - save.getH() / 2 - e.getH() / 2;
					//System.out.println("x: " + distx + "\n" + "y: " + disty);
					if(distx < 0 && disty < 0){
//						System.out.println("plz kill yourself");
						if(distx > disty){
							distx = 0;
						}
						else
							disty = 0;
//						System.out.println("x: " + distx + "    y: " + disty);
					}
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
		reset();
	}
	
	public void saveCurrentState() {
		save.setX(x);
		save.setY(y);
		save.setH(h);
		save.setW(w);
		save.setXV(xv);
		save.setYV(yv);
	}
	
	public void updateC() {
		updateC(TIME_UNIT);
	}
	
	public void updateC(double time) {
		yv += time * GRAVITY;
		if (yv > TERMINAL_VELOCITY)
			yv = TERMINAL_VELOCITY;
		x += time * xv;
		y += time * yv;
	}
	
	public void reset() {
		x = save.getX();
		y = save.getY();
		w = save.getW();
		h = save.getH();
		xv = save.getXV();
		yv = save.getYV();
	}
	
	public boolean isAlive(){
		if(health > 0){
			return true;
		}
		return false;
	}
	
	public void takeDamage(int dmg){
		health -= dmg;
	}

	public double getXV(){
		return xv;
	}

	public void setXV(double xv){
		this.xv = xv;
	}
	
	public double getYV(){
		return yv;
	}

	public void setYV(double yv){
		this.yv = yv;
	}
	
	public boolean getNorthC(){
		return northC;
	}

	public void setNorthC(boolean c){
		this.northC = c;
	}
	
	public boolean getEastC(){
		return eastC;
	}

	public void setEastC(boolean c){
		this.eastC = c;
	}
	
	public boolean getSouthC(){
		return southC;
	}

	public void setSouthC(boolean c){
		this.southC = c;
	}
	
	public boolean getWestC(){
		return westC;
	}

	public void setWestC(boolean c){
		this.westC = c;
	}
	
	public Movable getSave(){
		return save;
	}
	
	public int getHealth(){
		return health;
	}
	
	public void setHealth(int h){
		health = h;
	}
	
	public int getFacingRight(){
		return facingRight;
	}
}