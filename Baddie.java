import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class Baddie extends Movable{

	public Baddie(BufferedImage b, int x, int y, int w, int h, boolean c, int xv,int yv) {
		super(b, x, y, w, h, true, xv, yv);
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.red);
		g.fillOval(x, y, w, h);
	}
	
	public void update(){
		int tempXV = xv;
		if(!southC){
			xv = 0;
		}
		super.update();
<<<<<<< HEAD
=======
		xv = tempXV;
>>>>>>> 0a4ccd18a81146a920be896e5d2cad71cf6d6276
	}
	
	
}
