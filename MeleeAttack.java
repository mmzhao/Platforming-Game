import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class MeleeAttack extends Entity{
	
	private Movable owner;
	private MeleeWeapon mw;
	private int dmg;
	private Path base;
	private Path real;
	private long duration;
	private long startTime;
	private Animation ani;
	private BufferedImage current;
	private boolean attacking = false;
	
	public MeleeAttack(Movable owner, MeleeWeapon w, int dmg, Path p, long duration){
		this.owner = owner;
		this.mw = w;
		this.dmg = dmg;
		base = p;
		real = p.get();
		this.duration = duration;
		ani = new Animation("Slash6.svg", 400, 846, 4, 6, 1);
		current = null;
	}
	
	public void draw(Graphics g, int offsetX, int offsetY, double scaleX, double scaleY){
		double x = real.getX();
		double y = real.getY();
		ArrayList<Vector> vs = real.getVS();
		g.setColor(Color.green);
		
//		double l = real.pathLength();
//		System.out.println((double)((System.currentTimeMillis() - startTime) / (double) duration));
//		double length = l * (double)((System.currentTimeMillis() - startTime) / (double) duration);
//		double length2 = l * (double)((System.currentTimeMillis() - startTime - 100) / (double) duration);
//		System.out.println(length);
//		Vector curr = real.timePlace(length);
//		Vector curr2 = real.timePlace(length2);
//		curr.print();
//		g.drawLine((int)((curr.getCX() - offsetX) * scaleX), (int)((curr.getCY() - offsetY) * scaleY), (int)((curr2.getCX() - offsetX) * scaleX), (int)((curr2.getCY() - offsetY) * scaleY));
//		g.fillOval((int)((curr.getCX() - 2 - offsetX) * scaleX), (int)((curr.getCY() - 2 - offsetY) * scaleY), (int)(4 * scaleX), (int)(4 * scaleY));
		
//		for(Vector v: vs){
//			g.drawLine((int)((x - offsetX) * scaleX), (int)((y - offsetY) * scaleY), (int)((x + v.getCX() - offsetX) * scaleX), (int)((y + v.getCY() - offsetY) * scaleY));
//			x += v.getCX();
//			y += v.getCY();
//		}
		
		
		if(current != null){
			double pivotX = (owner.getMidX() - offsetX) * scaleX;
			double pivotY =  (owner.getMidY() - offsetY) * scaleY;
			AffineTransform at = new AffineTransform();
			at.setToRotation(mw.getAngle(), pivotX, pivotY);
			
			Graphics2D newGraphics = (Graphics2D)g.create();
			newGraphics.setTransform(at);
			
//			if (owner.getFacingRight() == 1) {
//				newGraphics.drawImage(current, (int)(( - offsetX) * scaleX), (int)((y - offsetY) * scaleY), (int)(w * scaleX), (int)(h * scaleY), null, null);
//			} else{
//				newGraphics.drawImage(current, (int)((x - offsetX + w) * scaleX), (int)((y - offsetY) * scaleY), -(int)(w * scaleX), (int)(h * scaleY), null, null);
//			}
//			g.drawImage(current, (int)((owner.getMidX() + owner.getFacingRight() * current.getWidth()/2 - offsetX) * scaleX), (int)((owner.getMidY() - current.getHeight()/2 - offsetY) * scaleY), (int)((-owner.getFacingRight() * current.getWidth()) * scaleX), (int)((current.getHeight()) * scaleY), null);
			newGraphics.drawImage(current, (int)((owner.getMidX() + owner.getFacingRight() * current.getWidth()/2 - offsetX) * scaleX), (int)((owner.getMidY() - current.getHeight()/2 - offsetY) * scaleY), (int)((-owner.getFacingRight() * current.getWidth()) * scaleX), (int)((current.getHeight()) * scaleY), null);
		}
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
		if(System.currentTimeMillis() > startTime + duration){
			ani.stop();
		}
		if(attacking){
			current = ani.loop(time);
		}
	}
	
	
	
}
