import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Movable extends Entity{
	
//	v: velocity vector
//	southC: whether or not this movable is collided from below
//	eastC: whether or not this movable is collided from right
//	westC: whether or not this movable is collided from left
//	northC: whether or not this movable is collided from above
//	health: how much health remains until this movable needs to be removed
//	facingRight: whether or not this movable is facing right
	protected Vector v;
	protected Vector a;
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
	protected Vector g = new Vector(0, .5); //positive acceleration goes SOUTH and EAST
	protected final double TIME_UNIT = .7;
	protected final double TERMINAL_VELOCITY = 10;
	
	protected double accelSpeed;
	protected double standardStep;
	protected boolean applyMoveAccel;
	
	protected int runningdir = 0;

// --------------------------------CONTRUCTOR-------------------------------- //
	
	public Movable(BufferedImage b, double x, double y, double w, double h, boolean c, double xv, double yv, int health, int facingRight){
		super(b, x, y, w, h, c);
		this.facingRight = facingRight;
		this.v = new Vector(xv, yv);
		this.health = health;
		save = new Movable(b, x, y, w, h, v.getCX(), v.getCY());
	}
	
	public Movable(BufferedImage b, double x, double y, double w, double h, double xv, double yv){
		super(b, x, y, w, h, true);
		this.v = new Vector(xv, yv);
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
		if(southC){
			if(v.getCY() > 0) v.setCY(0);
		}
		else v.add(g.scale(time));
		if(eastC){
			if(v.getCX() > 0) v.setCX(0);
		}
		if(westC){
			if(v.getCX() < 0) v.setCX(0);
		}
		if(northC){
			if(v.getCY() < 0) v.setCY(0);
		}
		if(v.magnitude() > TERMINAL_VELOCITY){
			v = v.scale(TERMINAL_VELOCITY / v.magnitude());
		}

		x += time * v.getCX();
		y += time * v.getCY();
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
		save.setV(v.get());
	}
	
	public void updateC() {
		updateC(TIME_UNIT);
	}
	
	public void updateC(double time) {
//		if(v.magnitude() > TERMINAL_VELOCITY){
//			v = v.scale(TERMINAL_VELOCITY / v.magnitude());
//		}

		x += time * v.getCX();
		y += time * v.getCY();
	}
	
	public void reset() {
		x = save.getX();
		y = save.getY();
		w = save.getW();
		h = save.getH();
		v = save.getV();
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

	public Vector getA(){
		return a;
	}
	
	public void setA(Vector a){
		this.a = a;
	}
	
	public void setXA(double xa){
		a.setCX(xa);
	}
	
	public void setYA(double ya){
		a.setCY(ya);
	}
	
	public double speed(){
		return v.magnitude();
	}
	
	public Vector getV(){
		return v;
	}

	public void setV(Vector v){
		this.v = v;
	}
	
	public void setXV(double xv){
		v.setCX(xv);
	}
	
	public void setYV(double yv){
		v.setCY(yv);
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
	
	public int getRunningdir(){
		return runningdir;
	}
	
	public double getStandardStep(){
		return standardStep;
	}
	
	public double getAccelSpeed(){
		return accelSpeed;
	}
	
	public boolean shouldApplyMoveAccel(){
		return applyMoveAccel;
	}

	public double getMouseX() {
		return 0;
	}
	
	public double getMouseY() {
		return 0;
	}
}