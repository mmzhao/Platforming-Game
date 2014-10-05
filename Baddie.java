import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class Baddie extends Movable{

	public Baddie(BufferedImage b, int x, int y, int w, int h, boolean c, int xv,int yv) {
		super(b, x, y, w, h, true, 0, 0);
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.red);
		g.fillOval(x, y, w, h);
	}
	
	public void update(){
		super.update();
	}
	
	
}
