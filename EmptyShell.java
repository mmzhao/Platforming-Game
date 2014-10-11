import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class EmptyShell extends Projectile {
	protected double yv;
	
	public EmptyShell(BufferedImage b, double x, double y, double xv, double yv){
		super(b, x, y, 5, 3, xv, 0, 0);
		this.yv = yv;
	}
	
	public void update(double time){
		super.update(time);
		yv += time*GRAVITY;
		y += yv;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.yellow);
		g.fillRect((int) x, (int) y, (int) w, (int) h);
		g.setColor(Color.black);
		g.drawRect((int) x, (int) y, (int) w, (int) h);
	}
}
