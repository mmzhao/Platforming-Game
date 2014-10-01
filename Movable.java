public class Movable extends Entity{

	private double xv;
	private double yv;
	private double xa;
	private double ya;
	private boolean grounded;

	private double final GRAVITY = -9.81;
	private double final TIME_UNIT = 1;

	public Movable(double x, double y, double w, double h, double xv, double yv, double xa, double ya, boolean g){
		super.Entity(x, y, w, h);
		this.xv = xv;
		this.yv = yv;
		this.xa = xa;
		this.ya = ya;
		grounded = g;
	}

	public next_instant(){
		next_instant(TIME_UNIT);
	}

	public next_instant(double time){
		x += time * xv;
		if(grounded){
			if(yv < 0) yv = 0;
			if(ya < 0) ya = 0;
		}
		else ya = GRAVITY;
		y += time * yv;
		xv += time * xa;
		yv += time * ya;
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

	public boolean getG(){
		return g;
	}

	public void setG(boolean g){
		this.g = g;
	}
}