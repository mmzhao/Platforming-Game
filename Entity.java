public class Entity{

	double x;
	double y;
	double w;
	double h;

	public Entity(double x, double y, double w, double h){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public enum Side{
		NORTH, EAST, SOUTH, WEST, NONE
	}

	public boolean between(double x, double low, double high){
		if(x < high && x > low) return true;
		return false;
	}

	public Side collision(Entity e){
	}

	public double getX(){
		return x;
	}

	public void setX(double x){
		this.x = x
	}

	public double getY(){

	}

	public double setY(){
		
	}

	public double getW(){

	}

	public double setW(){
		
	}

	public double getH(){

	}

	public double setH(){
		
	}

}