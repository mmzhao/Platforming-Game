import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class MeleeAttack extends Entity{
	
//	private Movable owner;
	private MeleeWeapon mw;
	private int dmg;
	private Path base;
//	private Path real;
	private long duration;
	private long startTime;
	private Animation ani;
	private BufferedImage current;
	private boolean attacking = false;
	private double angle;
	private int facingRight;
	
	public MeleeAttack(MeleeWeapon w, int dmg, Path p, long duration){
//		this.owner = owner;
		this.mw = w;
		this.dmg = dmg;
		base = p;
//		real = p.get();
		this.duration = duration;
		startTime = System.currentTimeMillis();
		ani = new Animation("Slash6.svg", 400, 846, 4, 6, 1);
		current = null;
		angle = 0;
		facingRight = 1;
	}
	
	public MeleeAttack(MeleeWeapon w, double x, double y, double angle, int dmg, Path p, long duration, int facingRight){
//		this.owner = owner;
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.mw = w;
		this.dmg = dmg;
		base = p;
//		real = p.get();
		this.duration = duration;
		startTime = System.currentTimeMillis();
		this.facingRight = facingRight;
		ani = new Animation("Slash6.svg", 400, 846, 4, 6, 1);
		current = null;
	}
	
	public MeleeAttack(MeleeWeapon w, double x, double y, double angle, int dmg, Path p, long duration, int facingRight, Animation ani){
//		this.owner = owner;
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.mw = w;
		this.dmg = dmg;
		base = p;
//		real = p.get();
		this.duration = duration;
		startTime = System.currentTimeMillis();
		this.facingRight = facingRight;
		this.ani = ani;
		current = null;
	}
	
	public void draw(Graphics g, int offsetX, int offsetY, double scaleX, double scaleY){
//		double x = real.getX();
//		double y = real.getY();
//		ArrayList<Vector> vs = real.getVS();

		
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
		
		
		if(current != null){
			double pivotX = (x - offsetX) * scaleX;
			double pivotY =  (y - offsetY) * scaleY;
			AffineTransform at = new AffineTransform();
			at.setToRotation(angle, pivotX, pivotY);
			
			Graphics2D newGraphics = (Graphics2D)g.create();
			newGraphics.setTransform(at);
			newGraphics.drawImage(current, (int)((x + facingRight * current.getWidth()/2 - offsetX) * scaleX), (int)((y - current.getHeight()/2 - offsetY) * scaleY), (int)((-facingRight * current.getWidth()) * scaleX), (int)((current.getHeight()) * scaleY), null);
		}
		
		
//		double xstart = x + base.getX();
//		double ystart = y + base.getY();
//		ArrayList<Vector> vs = base.getVS();
//		g.setColor(Color.green);
//		
//		for(Vector v: vs){
//			g.drawLine((int)((xstart - offsetX) * scaleX), (int)((ystart - offsetY) * scaleY), (int)((xstart + v.getCX() - offsetX) * scaleX), (int)((ystart + v.getCY() - offsetY) * scaleY));
//			xstart += v.getCX();
//			ystart += v.getCY();
//		}
		
	}
	
	public void update(double time){
		if(System.currentTimeMillis() >= startTime + duration){
			kill();
			return;
		}
		if(System.currentTimeMillis() > startTime + duration){
			ani.stop();
		}
		if(attacking){
			current = ani.loop(time);
		}
	}
	
	public MeleeAttack getRotatedVersion(double x, double y, double angle, int facingRight){
		if(facingRight == -1){
			return new MeleeAttack(mw, x, y, angle, dmg, base.rotate(0, 0, -angle).reflect(), duration, facingRight, ani.get());
		}
		return new MeleeAttack(mw, x, y, angle, dmg, base.rotate(0, 0, angle), duration, facingRight, ani.get());
	}
	
	public void attack(){
		ani.start();
		attacking = true;
		
		ArrayList<Baddie> bs = GamePanel.getEL().getBaddies(base.getRect());
		
		for(Baddie b: bs){
			for(Line2D l: base.getLines()){
				if(b.makeRect().intersectsLine(l)){
					b.takeDamage(dmg);
					break;
				}
			}
		}
		
	}
	

	
	
	
}
