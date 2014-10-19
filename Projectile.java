import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Projectile extends Movable{
	
	protected double xa;
	protected int dmg;
	protected final int TERMINAL_VELOCITY = 10;

	public Projectile(BufferedImage b, double x, double y, double w, double h, double xv, double xa, int dmg){
		super(b, x, y, w, h, true, xv, 0, 0, 0);
		this.xa = xa;
		this.dmg = dmg;
	}
	
	public void draw(Graphics g) {
		draw(g, 0, 0);
	}
	
	public void draw(Graphics g, int offsetX, int offsetY){
		draw(g, offsetX, offsetY, 1, 1);
	}
	
	public void draw(Graphics g, int offsetX, int offsetY, double scaleX, double scaleY) {
		g.setColor(Color.green);
		g.fillOval((int)((x - offsetX) * scaleX), (int) ((y - offsetY) * scaleY), (int) (w * scaleX), (int) (h * scaleY));
	}
	
	public void update(double time){
		if(xv > TERMINAL_VELOCITY) xv = TERMINAL_VELOCITY;
		x += time * xv;
		xv += time * xa;
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

	public void onHit() {
		kill();
	}
}
