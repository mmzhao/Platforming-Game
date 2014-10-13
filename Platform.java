import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


public class Platform extends Movable{

	public Platform(BufferedImage b, double x, double y, double w, double h) {
		super(b, x, y, w, h, true, 0, 0, 0, 0);
		isPlatform = true;
	}
	
	public Rectangle makeRect(int extra){
		return new Rectangle((int)(x - extra), (int)(y - extra), (int)(w + extra), (int)(h + extra));
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.red);
		g.fillRect((int) x, (int) y, (int) w, (int) h);
		g.drawRect((int) x, (int) y, (int) w, (int) h);
	}
	
	public void draw(Graphics g, int offsetX, int offsetY){
		g.setColor(Color.red);
		g.fillRect((int) x - offsetX, (int) y - offsetY, (int) w, (int) h);
		g.drawRect((int) x - offsetX, (int) y - offsetY, (int) w, (int) h);

	}

}
