import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class MeleeAttack {
	
	private Movable owner;
	private int dmg;
	private Path base;
	private Path real;
	private Animation ani;
	private BufferedImage current;
	private boolean attacking = false;
	
	public MeleeAttack(Movable owner, int dmg, Path p){
		this.owner = owner;
		this.dmg = dmg;
		base = p;
		real = p.get();
		ani = new Animation("Slash2.png", 7, 2, 1);
		current = null;
	}
	
	public void draw(Graphics g, int offsetX, int offsetY, double scaleX, double scaleY){
		double x = real.getX();
		double y = real.getY();
		ArrayList<Vector> vs = real.getVS();
		g.setColor(Color.green);
		for(Vector v: vs){
//			System.out.println(x + " " + y + " " + (x + v.getCX()) + " " + (y + v.getCY()));
			g.drawLine((int)((x - offsetX) * scaleX), (int)((y - offsetY) * scaleY), (int)((x + v.getCX() - offsetX) * scaleX), (int)((y + v.getCY() - offsetY) * scaleY));
			x += v.getCX();
			y += v.getCY();
		}
		if(current != null)
			g.drawImage(current, (int)((owner.getMidX() - current.getWidth()/2 - offsetX) * scaleX), (int)((owner.getMidY() - current.getHeight()/2 - offsetY) * scaleY), (int)((current.getWidth()) * scaleX), (int)((current.getHeight()) * scaleY), null);
	}
	
	public void attack(double x, double y, double angle){
		double pivotX = owner.getMidX();
		double pivotY = owner.getMidY() - 8;
		
		ani.start();
		attacking = true;
		
		real = base.rotate(pivotX, pivotY, angle);
		
		ArrayList<Baddie> bs = GamePanel.getEL().getBaddies(real.getRect());
		
		for(Baddie b: bs){
			for(Line2D l: real.getLines()){
				if(b.makeRect().intersectsLine(l)){
					b.takeDamage(dmg);
					break;
				}
			}
		}
		
	}
	
	public void update(double time){
		if(attacking){
			current = ani.loop(time);
		}
	}
	
	
	
}
