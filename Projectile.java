import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Projectile extends Movable{
	
	protected double xa;
	private final int TERMINAL_VELOCITY = 10;

	public Projectile(BufferedImage b, double x, double y, double w, double h, double xv, double xa){
		super(b, x, y, w, h, true, xv, 0);
		this.xa = xa;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.green);
		
		g.fillOval((int) x, (int) y, (int) w, (int) h);
	}
	
	public void update(double time){
		x += time * xv;
		if(xv > TERMINAL_VELOCITY) xv = TERMINAL_VELOCITY;
		xv += time * xa;
	}
	
	public void sidesCollided(ArrayList<Entity> es){
		for(Entity e: es){
//			if(!(e instanceof Platform)) continue;
			Side s = collision(e);
			if(s != Side.NONE){
				remove = true;
			}
		}
	}
	
}
