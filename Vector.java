
public class Vector {
	
	double changeX;
	double changeY;
	
	public Vector(double cx, double cy){
		changeX = cx;
		changeY = cy;
	}
	
	
	
	//returns projection of this vector onto v
	public static Vector project(Vector v1, Vector v2){
		return v2.scale(dot(v1, v2) / dot(v1, v2));
	}
	
	//returns dot product of this vector and v
	public static double dot(Vector v1, Vector v2){
		return v1.getCX() * v2.getCX() + v1.getCY() * v2.getCY();
	}
	
	//returns vector sum of this vector and v
	public void add(Vector v){
		setCX(getCX() + v.getCX());
		setCY(getCY() + v.getCY());
	}
	
	public static Vector add(Vector v1, Vector v2){
		return new Vector(v1.getCX() + v2.getCX(), v1.getCY() + v2.getCY());
	}
	
	//returns unit vector of this vector
	public Vector unit(){
		return new Vector(getCX() / magnitude(), getCY() / magnitude());
	}
	
	public static Vector unit(Vector v){
		return new Vector(v.getCX() / v.magnitude(), v.getCY() / v.magnitude());
	}
	
	//returns this vector rotated angle degrees
	public Vector rotate(double angle){
		angle %= 360;
		angle *= Math.PI/180;
		return new Vector(Math.cos(angle) * getCX() - Math.sin(angle) * getCY(), Math.sin(angle) * getCX() + Math.cos(angle) * getCY());
	}
	
	//return a scaled copy of this vector
	public Vector scale(double scale){
		return new Vector(scale * getCX(), scale * getCY());
	}
	
	//return magnitude of this vector
	public double magnitude(){
		return Math.pow(getCX() * getCX() + getCY() * getCY(), .5);
	}
	
	//return copy of this vector
	public Vector get(){
		return new Vector(getCX(), getCY());
	}
	
	public void print(){
		System.out.println("xdir: " + changeX + ", ydir: " + changeY);
	}
	
	public double getCX(){
		return changeX;
	}
	
	public void setCX(double cx){
		changeX = cx;
	}
	
	public double getCY(){
		return changeY;
	}
	
	public void setCY(double cy){
		changeY = cy;
	}
	
}
