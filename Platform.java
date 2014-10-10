import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class Platform extends Movable{

	public Platform(BufferedImage b, double x, double y, double w, double h) {
		super(b, x, y, w, h, true, 0, 0, 0, 0);
		isPlatform = true;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.red);
		g.fillRect((int) x, (int) y, (int) w, (int) h);
		g.drawRect((int) x, (int) y, (int) w, (int) h);
	}

}
