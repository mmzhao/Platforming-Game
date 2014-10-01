public class Entity{

	private double x;
	private double y;
	private double w;
	private double h;

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
		if(x < high && x > low) 
			return true;
		return false;
	}

	public Side collision(Entity e){
		
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

}