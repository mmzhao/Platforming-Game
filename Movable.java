import java.awt.image.BufferedImage;

public class Movable extends Entity{

	private int xv;
	private int yv;
	private boolean southC;
	private boolean eastC;
	private boolean westC;
	private boolean northC;

	private final int GRAVITY = 1; //positive acceleration goes SOUTH and EAST
	private final int TIME_UNIT = 1;
	private final int TERMINAL_VELOCITY = 3;

	public Movable(BufferedImage b, int x, int y, int w, int h, boolean c, int xv, int yv){
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
		northC = false;
		eastC = false;
		southC = false;
		westC= false;
	}
	
	public void sidesCollided(Entity e){
		Side s = collision(e);
		if(s == Side.NORTH) northC = true;
		if(s == Side.EAST) eastC = true;
		if(s == Side.SOUTH) southC = true;
		if(s == Side.WEST) westC = true;
	}

	public int getXV(){
		return xv;
	}

	public void setXV(int xv){
		this.xv = xv;
	}
	
	public int getYV(){
		return yv;
	}

	public void setYV(int yv){
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