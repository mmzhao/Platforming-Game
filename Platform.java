import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


public class Platform extends Movable{
	public final int PLATFORM_EXTRA = 20;

	public Platform(BufferedImage b, double x, double y, double w, double h) {
		super(b, x, y, w, h, true, 0, 0, 0, 0);
		isPlatform = true;
		activated = true;
	}
	
	public Rectangle makeRectExtra(){
		return new Rectangle((int)(x - PLATFORM_EXTRA), (int)(y - PLATFORM_EXTRA), (int)(w + 2 * PLATFORM_EXTRA), (int)(h + 2 * PLATFORM_EXTRA));
	}
	
	public void draw(Graphics g) {
		draw(g, 0, 0);
	}
	
	public void draw(Graphics g, int offsetX, int offsetY){
		draw(g, offsetX, offsetY, 1, 1);
	}
	
	public void draw(Graphics g, int offsetX, int offsetY, double scaleX, double scaleY){
		g.setColor(Color.red);
		Rectangle screen = new Rectangle(-3, -3, 843, 663);
		Rectangle self = new Rectangle((int)((x - offsetX) * scaleX), (int) ((y - offsetY) * scaleY), (int) (w * scaleX), (int) (h * scaleY));
		Rectangle inter = screen.intersection(self);
//		g.fillRect((int)((x - offsetX) * scaleX), (int) ((y - offsetY) * scaleY), (int) (w * scaleX), (int) (h * scaleY));
//		g.drawRect((int)((x - offsetX) * scaleX), (int) ((y - offsetY) * scaleY), (int) (w * scaleX), (int) (h * scaleY));
		g.fillRect((int)(inter.getX()), (int)(inter.getY()), (int)(inter.getWidth()), (int)(inter.getHeight()));
	}
	
	public void update(double time){
		
	}
	
	public boolean isAlive(){
		return true;
	}

}
