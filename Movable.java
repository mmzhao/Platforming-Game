import java.awt.Graphics;

public class Movable extends Entity{

	private double xv;
	private double yv;
	private double xa;
	private double ya;
	private boolean southC;
	private boolean eastC;
	private boolean westC;
	private boolean northC;
	private boolean alive;
	private boolean platform;
	private boolean sleeping;
	private boolean invisible;
	private boolean onScreen;
	private boolean relevant;

	private final double GRAVITY = -9.81;
	private final double TIME_UNIT = 1;

	public Movable(double x, double y, double w, double h, boolean c, double xv, double yv, double xa, double ya, boolean p){
		super(x, y, w, h, c);
		this.xv = xv;
		this.yv = yv;
		this.xa = xa;
		this.ya = ya;
		alive = true;
		platform = p;
		sleeping = true;
		onScreen = false;
		relevant = true;
	}

	public void update(){
		update(TIME_UNIT);
	}

	public void update(double time){
		if(southC){
			if(yv < 0) yv = 0;
			if(ya < 0) ya = 0;
		}
		else ya = GRAVITY;
		if(eastC){
			if(xv > 0) xv = 0;
		}
		if(westC){
			if(xv < 0) xv = 0;
		}
		if(northC){
			if(yv > 0) yv = 0;
		}

		x += time * xv;
		y += time * yv;
		yv += time * ya;
	}
	
	public void draw(Graphics g, int x, int y) {
		g.drawImage(bi, x, y, null);
	}
	
	public void draw(Graphics g, int x, int y, int offsetX, int offsetY) {
		draw(g, x + offsetX, y + offsetY);
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

	public double getXA(){
		return xa;
	}

	public void setXA(double xa){
		this.xa = xa;
	}

	public double getYA(){
		return ya;
	}

	public void setYA(double ya){
		this.ya = ya;
	}

	public boolean getSouthC(){
		return southC;
	}

	public void setSouthC(boolean c){
		this.southC = c;
	}

	public boolean getNorthC(){
		return northC;
	}

	public void setNorthC(boolean c){
		northC = c;
	}

	public boolean getEastC(){
		return eastC;
	}

	public void setEastC(boolean c){
		eastC = c;
	}

	public boolean getWestC(){
		return westC;
	}

	public void setWestC(boolean c){
		westC = c;
	}
	
	public boolean isAlive(){
		return alive;
	}
	
	public void kill(){
		alive = false;
	}
	
	public boolean isPlatform(){
		return platform;
	}
	
	public boolean isSleeping(){
		return sleeping;
	}
	
	public void wakeUp(){
		sleeping = true;
	}
	
	public boolean isInvisible(){
		return invisible;
	}
	
	public void setInvisible(boolean i){
		invisible = i;
	}
	
	public boolean isOnScreen(){
		return onScreen;
	}
	
	public void setOnScreen(boolean onScreen){
		this.onScreen = onScreen;
	}
	
	public boolean isRelevant(){
		return relevant;
	}
	
	public void becomeRevelant(){
		relevant = true;
	}
}