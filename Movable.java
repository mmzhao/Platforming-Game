public class Movable extends Entity{

	private double xv;
	private double yv;
	private double xa;
	private double ya;
	private boolean grounded;
	private boolean eastC;
	private boolean westC;
	private boolean northC;

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
		if(grounded){
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
		return grounded;
	}

	public void setG(boolean g){
		this.g = g;
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
}