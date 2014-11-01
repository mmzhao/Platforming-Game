
import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class Baddie extends Movable{
	
//	current: buffered animation the animcation is currently on
//	move: the animation for the baddie
	
	private BufferedImage current;
	private Animation move;
	
	protected Vector g = new Vector(0, .5);
	
// --------------------------------CONSTRUCTOR-------------------------------- //
	
	public Baddie(BufferedImage b, double x, double y, double w, double h, boolean c, double xv, double yv, int health) {
		super(b, x, y, w, h, true, xv, yv, health, 0);
		move = new Animation("shroom run.png", 5, 1, 10);
		move.start();
		current = null;
	}

// --------------------------------DRAW METHODS-------------------------------- //
	
	public void draw(Graphics g) {
		draw(g, 0, 0);
	}
	
	public void draw(Graphics g, int offsetX, int offsetY){
		draw(g, offsetX, offsetY, 1, 1);
	}
	
	public void draw(Graphics g, int offsetX, int offsetY, double scaleX, double scaleY){
		g.drawImage(current, (int)((x - offsetX) * scaleX), (int) ((y - offsetY) * scaleY), (int) (w * scaleX), (int) (h * scaleY), null);
	}
	
// --------------------------------UPDATE-------------------------------- //
	
	public void update(double time){
//		System.out.println(health);
		if(!isAlive()){ // change to death later this is for the lolz
			kill();
			return;
//			health = (int) (w + h);
//			if(southC){
//				y -= .1 * h;
//			}
//			if(eastC){
//				x -= .1 * w;
//			}
//			w += .1 * w;
//			h += .1 * h;
		}
		
		
//		double tempXV = xv;
//		if(!southC){
//			xv = 0;
//		}
		
//		System.out.println(northC + " " + eastC + " " + southC + " " + westC);
		
//		Vector v1 = new Vector(10, 5);
//		Vector v2 = new Vector(5, 10);
//		Vector.add(v1, v2).print();
		
		if(southC){
			if(v.getCY() > 0) v.setCY(0);
		}
		else v.add(g.scale(time));
		if(eastC){
			if(v.getCX() > 0) v.setCX(-v.getCX());
		}
		if(westC){
			if(v.getCX() < 0) v.setCX(-v.getCX());
		}
		if(northC){
			if(v.getCY() < 0) v.setCY(0);
		}
		if(v.magnitude() > TERMINAL_VELOCITY){
			v = v.scale(TERMINAL_VELOCITY / v.magnitude());
		}
		
//		v.print();

		x += time * v.getCX();
		y += time * v.getCY();
		
//		if(xv == 0) xv = tempXV;
		current = move.loop(time);
	}

	
}
