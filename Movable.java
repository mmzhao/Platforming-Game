public class Movable {

	double xv;
	double yv;
	double xa;
	double ya;
	boolean grounded;

	double final GRAVITY = -9.81;
	double final TIME_UNIT = 1;

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

}