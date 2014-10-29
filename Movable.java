import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Movable extends Entity{
	
//	xv: x directional velocity
//	yv: y directional velocity
//	southC: whether or not this movable is collided from below
//	eastC: whether or not this movable is collided from right
//	westC: whether or not this movable is collided from left
//	northC: whether or not this movable is collided from above
//	health: how much health remains until this movable needs to be removed
//	facingRight: whether or not this movable is facing right
	protected double xv;
	protected double yv;
	protected boolean southC;
	protected boolean eastC;
	protected boolean westC;
	protected boolean northC;
	protected int health;
	protected int facingRight;
	
//	save: save state of this movable used for collision detection
	protected Movable save = null;

//	GRAVITY: acceleration due to gravity
//	TIME_UNIT: how much time passes per frame update
//	TERMINAL_VELOCITY: max velocity for one direction for this movable
	protected final double GRAVITY = .5; //positive acceleration goes SOUTH and EAST
	protected final double TIME_UNIT = .7;
	protected final double TERMINAL_VELOCITY = 10;

// --------------------------------CONTRUCTOR-------------------------------- //
	
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

// --------------------------------DRAW METHODS-------------------------------- //
	
	public void draw(Graphics g, int offsetX, int offsetY, double scaleX, double scaleY){
		
	}
	
	public void draw(Graphics g, int offsetX, int offsetY){
		
	}
	
	public void draw(Graphics g) {
		draw(g, 0, 0);
	}
	
// --------------------------------UPDATE-------------------------------- //

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
	
// --------------------------------COLLISION SAVE STATE METHODS-------------------------------- //
	
	public void resetCollisionState(){
		northC = false;
		eastC = false;
		southC = false;
		westC = false;
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
	
// --------------------------------LIFE METHODS-------------------------------- //
	
	public boolean isAlive(){
		if(health > 0){
			return true;
		}
		return false;
	}
	
	public void takeDamage(int dmg){
		health -= dmg;
	}
	
// --------------------------------GET/SET METHODS-------------------------------- //

	public double speed(){
		return Math.pow(xv * xv + yv * yv, .5);
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

	public double getMouseX() {
		return 0;
	}
	
	public double getMouseY() {
		return 0;
	}
}