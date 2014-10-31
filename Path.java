import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ArrayList;


public class Path {
	
	private double x; // dist from owner mid x
	private double y; // dist from owner mid y
	private ArrayList<Vector> vs;
	
	public Path(double x, double y){
		this.x = x;
		this.y = y;
		vs = new ArrayList<Vector>();
	}
	
	public Path(double x, double y, ArrayList<Vector> v){
		this.x = x;
		this.y = y;
		this.vs = v;
	}
	
	public Vector timePlace(double length){
		Vector v = new Vector(x, y);
		double totalLength = pathLength();
		length %= totalLength;
		int index = 0;
		while(length > vs.get(index).magnitude() && index < vs.size()){
			length -= vs.get(index).magnitude();
			v.add(vs.get(index));
			index++;
		}
		if(index < vs.size()){
			v.add(vs.get(index).scale(length / vs.get(index).magnitude()));
		}
		return v;
		
	}
	
	public double pathLength(){
		double l = 0;
		for(Vector v: vs){
			l += v.magnitude();
		}
		return l;
	}
	
	public ArrayList<Line2D> getLines(){
		ArrayList<Line2D> ls = new ArrayList<Line2D>();
		double currX = x;
		double currY = y;
		for(Vector v: vs){
			ls.add(new Line2D.Double(currX, currY, currX + v.getCX(), currY + v.getCY()));
			currX += v.getCX();
			currY += v.getCY();
		}
		return ls;
	}
	
	public Rectangle getRect(){
		double minX = x;
		double minY = y;
		double maxX = x;
		double maxY = y;
		double currX = x;
		double currY = y;
		for(Vector v: vs){
			currX += v.getCX();
			currY += v.getCY();
			minX = Math.min(minX, currX);
			minY = Math.min(minY, currY);
			maxX = Math.max(maxX, currX);
			maxY = Math.max(maxY, currY);
		}
		return new Rectangle((int) minX, (int) minY, (int) (maxX - minX), (int) (maxY - minY));
	}
	
	public Path rotate(double pX, double pY, double angle){
		Vector start = new Vector(x, y);
		start = start.rotate(angle);
		Path newP = new Path(pX + start.getCX(), pY + start.getCY());
		for(Vector v: vs){
			newP.add(v.rotate(angle));
		}
		return newP;
	}
	
	public Path get(){
		return new Path(x, y, vs);
	}
	
	public void add(Vector v){
		vs.add(v);
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public ArrayList<Vector> getVS(){
		return vs;
	}
	
}
