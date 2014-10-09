import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Movable extends Entity{

	protected double xv;
	protected double yv;
	protected boolean southC;
	protected boolean eastC;
	protected boolean westC;
	protected boolean northC;
	
	protected Movable save; // for better collision detection

	protected final double GRAVITY = 1; //positive acceleration goes SOUTH and EAST
	protected final double TIME_UNIT = 1;
	protected final double TERMINAL_VELOCITY = 5;

	public Movable(BufferedImage b, double x, double y, double w, double h, boolean c, double xv, double yv){
		super(b, x, y, w, h, c);
		this.xv = xv;
		this.yv = yv;
	}

	public void update(){
		update(TIME_UNIT);
	}

	public void update(double time){
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
		save = new Movable(bi, x, y, h, w, true, xv, yv);
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
	
}