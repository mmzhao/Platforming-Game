import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Projectile extends Movable{
	
//	xa: x directional acceleration
//	ya: y directional acceleration
//	dmg: damage that this projectile deals upon collision
//	TERMINAL_VELOCITY: max total (pythag) velocity for a projectile
	protected double xa;
	protected double ya;
	protected int dmg;
	protected final int TERMINAL_VELOCITY = 24;

// --------------------------------CONSTRUCTOR-------------------------------- //
	
	public Projectile(BufferedImage b, double x, double y, double w, double h, double xv, double yv, double xa, double ya, int dmg){
		super(b, x, y, w, h, true, xv, yv, 0, 0);
		this.xa = xa;
		this.ya = ya;
		this.dmg = dmg;
	}
	
// --------------------------------DRAW METHODS-------------------------------- //
	
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
	
// --------------------------------UPDATE-------------------------------- //
	
	public void update(double time){
		xv += time * xa;
		yv += time * ya;
		if(speed() > TERMINAL_VELOCITY){
			double scale = TERMINAL_VELOCITY / speed();
			xv *= scale;
			yv *= scale;
		}
		x += time * xv;
		y += time * yv;
	}
	
// --------------------------------COLLISION METHODS-------------------------------- //
	
	public void onHit() {
		kill();
	}
	
// --------------------------------GET/SET METHODS-------------------------------- //

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
