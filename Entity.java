public class Entity{

	double x; //x increases from left/west to right/east
	double y; //y increases from top/north to bottom/south
	double w;
	double h;
	boolean collidable;

	public Entity(double x, double y, double w, double h, boolean c){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		collidable = c;
	}

	public enum Side{
		NORTH, EAST, SOUTH, WEST, GENERAL, NONE
	}

	public double intersect(double x1, double x2, double x3, double x4){
		//x1 and x2 from entity 1, x1 < x2, x3 and x4 from entity 2, x3< x4
		//returns overlap
		if(x1 > x3 && x1 < x4) return x4 - x1;
		if(x2 > x3 && x2 < x4) return x2 - x3;
		return 0;
	}

	public Side collision(Entity e){
		double ns = intersect(e.getX(), e.getX() + e.getW, x, x + w); //north south intersection
		double ew = intersect(e.getY(), e.getY() + e.getH, y, y + h); //east west interstction
		if(!(e.getC() && collidable)){
			if(ns + ew > 0) return GENERAL;
			return NONE;
		}
		Side nsOption = NORTH;
		Side ewOption = WEST;
		if(e.getMidX() > getMidX()) ewOption = EAST;
		if(e.getMidY() > getMidY()) nsOption = SOUTH;
		double ns = intersect(e.getX(), e.getX() + e.getW, x, x + w); //north south intersection
		double ew = intersect(e.getY(), e.getY() + e.getH, y, y + h); //east west interstction
		if(ns + ew > 0){
			if(ns > ew) return nsOption;
			return ewOption;
		}
		return NONE;
	}

	public double getMidX(){
		return x + w/2;
	}

	public double getMidY(){
		return y + h/2;
	}

	public double getX(){
		return x;
	}

	public void setX(double x){
		this.x = x;
	}

	public double getY(){
		return y;
	}

	public void setY(double y){
		this.y = y;
	}

	public double getW(){
		return w;
	}

	public void setW(double w){
		this.w = w;
	}

	public double getH(){
		return h;
	}

	public void setH(double h){
		this.h = h;
	}

	public boolean getC(){
		return collidable;
	}

	publci void setC(boolean c){
		collidable = c;
	}

}