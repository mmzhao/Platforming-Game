import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class Platform extends Movable{

	public Platform(BufferedImage b, int x, int y, int w, int h) {
		super(b, x, y, w, h, true, 0, 0);
		isPlatform = true;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(x, y, w, h);
		g.drawRect(x, y, w, h);
	}

}
