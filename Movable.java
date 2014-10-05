import java.awt.image.BufferedImage;

public class Movable extends Entity{

	private int xv;
	private int yv;
	private boolean southC;
	private boolean eastC;
	private boolean westC;
	private boolean northC;

//	private final double GRAVITY = -9.81;
	private final double TIME_UNIT = 1;

	public Movable(BufferedImage b, int x, int y, int w, int h, boolean c, int xv, int yv){
		super(b, x, y, w, h, c);
		this.xv = xv;
		this.yv = yv;
	}

	public void update(){
		update(TIME_UNIT);
	}

	public void update(double time){
		if(southC){
			if(yv < 0) yv = 0;
		}
//		else yv += time * GRAVITY;
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
	}

	public int getXV(){
		return xv;
	}

	public void setXV(int xv){
		this.xv = xv;
	}

	public int getYV(){
		return yv;
	}

	public void setYV(int yv){
		this.yv = yv;
	}
	
}