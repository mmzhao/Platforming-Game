import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Projectile extends Movable{
	
	protected double xa;
	protected int dmg;
	private final int TERMINAL_VELOCITY = 10;

	public Projectile(BufferedImage b, double x, double y, double w, double h, double xv, double xa, int dmg){
		super(b, x, y, w, h, true, xv, 0, 0, 0);
		this.xa = xa;
		this.dmg = dmg;
	}
	
	public void draw(Graphics g) {
		draw(g, 0, 0);
	}
	
	public void draw(Graphics g, int offsetX, int offsetY) {
		g.setColor(Color.green);
		g.fillOval((int) x - offsetX, (int) y - offsetY, (int) w, (int) h);
	}
	
	public void update(double time){
		if(xv > TERMINAL_VELOCITY) xv = TERMINAL_VELOCITY;
		x += time * xv;
		xv += time * xa;
	}
	
	public void sidesCollided(ArrayList<Entity> es) {
		saveCurrentState();
		northC = false;
		eastC = false;
		southC = false;
		westC = false;
		updateC();
		for (Entity e : es) {
			Rectangle r1 = new Rectangle((int) e.getX(), (int) e.getY(),
					(int) e.getW(), (int) e.getH());
			Rectangle r2 = new Rectangle((int) x, (int) y, (int) w, (int) h);
			if (r1.intersects(r2)){
				if(e instanceof Baddie) ((Baddie) e).takeDamage(dmg);
				remove = true;
			}
		}
		reset();
	}
	
	public void sidesCollided2(ArrayList<Entity> es){
		for(Entity e: es){
//			if(!(e instanceof Platform)) continue;
			Side s = collision(e);
			if(s != Side.NONE){
				remove = true;
			}
		}
	}
	
	public double getXA(){
		return xa;
	}
	
	public void setXA(double xa){
		this.xa = xa;
	}
	
	public int getDamage(){
		return dmg;
	}
	
	public void setDamage(int dmg){
		this.dmg = dmg;
	}
}
