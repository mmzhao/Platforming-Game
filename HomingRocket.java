import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

//NOT FIXED YET NO HOMING
public class HomingRocket extends Projectile{
	private int homingLvl;
	private Baddie b;
	private int range;
	private boolean on;
	private long explodeTimer;
	private boolean explode;
	private double explodeRadius;
	private double knockBack;
	
	private Animation explosion;
	private BufferedImage current = null;
	
	private final String[] EXPLOSIONS = {"DarknessExplosion", "Explosion1", "Explosion2", "Explosion3", "IceExplosion"};
	private final int TERMINAL_VELOCITY = 20;
	
	public HomingRocket(BufferedImage b, double x, double y, double w, double h, double xv, double yv, int dmg, int homingLvl) {
		super(b, x, y, w, h, xv, yv, 0, 0, dmg);
		this.homingLvl = homingLvl;
		this.b = null;
		on = false;
		explodeTimer = -2000;
		explode = false;
		explodeRadius = 40;
		knockBack = 5;
	}
	
	public HomingRocket(BufferedImage b, double x, double y, double w, double h, double xv, double yv, int dmg, int homingLvl, int range) {
		super(b, x, y, w, h, xv, yv, 0, 0, dmg);
		this.homingLvl = homingLvl;
		this.b = null;
		this.range = range;
		on = false;
		explodeTimer = -2000;
		explode = false;
		explosion = Explosions.getRandom();
		explodeRadius = 20;
		knockBack = 3;
	}
	
	public void draw(Graphics g, int offsetX, int offsetY){
		draw(g, offsetX, offsetY, 1, 1);
	}
	
	public void draw(Graphics g, int offsetX, int offsetY, double scaleX, double scaleY) {
		if(explode){
			g.setColor(Color.black);
			g.fillOval((int) ((getMidX() - 2 - offsetX) * scaleX), (int) ((getMidY() - 2 - offsetY) * scaleY), (int) (4 * scaleY), (int) (4 * scaleY));
			g.drawOval((int)((getMidX() - offsetX) * scaleX) - (int) (explodeRadius * scaleX), (int) ((getMidY() - offsetY) * scaleY) - (int) (explodeRadius * scaleY), (int) (2 * explodeRadius * scaleX), (int) (2 * explodeRadius * scaleY));
			g.drawOval((int)((getMidX() - offsetX) * scaleX) - (int) (1.5 * explodeRadius * scaleX), (int) ((getMidY() - offsetY) * scaleY) - (int) (1.5 * explodeRadius * scaleY), (int) (3 * explodeRadius * scaleX), (int) (3 * explodeRadius * scaleY));
			g.drawImage(current, (int)((getMidX() - offsetX) * scaleX) - (int) (explodeRadius * scaleX), (int) ((getMidY() - offsetY) * scaleY) - (int) (explodeRadius * scaleY), (int) (2 * explodeRadius * scaleX), (int) (2 * explodeRadius * scaleY), null);
			return;
		}
		
		g.setColor(Color.black);
		
//		if(b != null){
//			on = true;
//		}
//		else{
//			on = false;
//		}
//		
//		if(on){
//			g.setColor(Color.red);
//		}
//		
//		if(on){
//			int xdif = (int) (b.getMidX() - this.getMidX());
//			int ydif = (int) (b.getMidY() - this.getMidY());
//			int dif = (int) Math.pow(xdif * xdif + ydif * ydif, .5);
//			
//			if(dif != 0){
//				if(xv * xdif > 0){
//					xv += (((double) xdif/dif) * homingLvl);
//				}
//				yv += (((double) ydif/dif) * homingLvl);
//			}
//			g.drawLine((int) ((x - offsetX) * scaleX), (int) ((y - offsetY + 3) * scaleY), (int) ((x - offsetX + xv * 3) * scaleX), (int) ((y - offsetY + yv * 3 + 3) * scaleY));
//		}
//		else
//		g.setColor(Color.green);
//			g.drawLine((int) ((x - offsetX + w/2) * scaleX), (int) ((y - offsetY + h/2) * scaleY), (int) ((x - offsetX + w/2 - xv * 3) * scaleX), (int) ((y - offsetY + h/2 - yv * 3) * scaleY));
//			g.fillOval((int) ((x - offsetX) * scaleX), (int) ((y - offsetY) * scaleY), (int) (w * scaleX), (int) (h * scaleY));
		
		
	}
	
	
	public void explode(){
		x -= 5 * w;
		y -= 5 * h;
		w *= 10;
		h *= 10;
		dmg = 1;
		explode = true;
		dealDamage();
		explosion.start();
	}
	
	public void dealDamage(){
		ArrayList<Baddie> inRange = GamePanel.getEL().getBaddies(new Rectangle((int)(getMidX() - 2 * explodeRadius), (int)(getMidY() - 2 * explodeRadius), (int)(4 * explodeRadius), (int)(4 * explodeRadius)));
		Shape oval = new Ellipse2D.Double(getMidX() - explodeRadius, getMidY() - explodeRadius, 2 * explodeRadius, 2 * explodeRadius);
		for(Baddie b: inRange){
			Shape rect = new Rectangle2D.Double(b.getX(), b.getY(), b.getW(), b.getH());
			if(oval.intersects(rect.getBounds())){
				b.takeDamage(dmg);
				knockBack(b);
			}
		}
		Shape ovalP = new Ellipse2D.Double(getMidX() - 1.5 * explodeRadius, getMidY() - 1.5 * explodeRadius, 3 * explodeRadius, 3 * explodeRadius);
		Shape rect = new Rectangle2D.Double(GamePanel.getPlayer().getX(), GamePanel.getPlayer().getY(), GamePanel.getPlayer().getW(), GamePanel.getPlayer().getH());
		if(ovalP.intersects(rect.getBounds())){
			knockBack(GamePanel.getPlayer());
		}
	}
	
	public void knockBack(Movable m){ //radial from center of explosion radius
//		double xdif = (m.getMidX() - this.getMidX());
//		double ydif = (m.getMidY() - this.getMidY());
//		double dif = Math.pow(xdif * xdif + ydif * ydif, .5);
//		
//		if(m instanceof Baddie){
//			m.setXV(m.getXV() + xdif/dif * knockBack);
//			m.setYV(Math.min(m.getYV() + ydif/dif * knockBack - 15, ydif/dif * knockBack - 15));
//			return;
//		}
//		
//		else if(m instanceof Player){
//			double changeX = xdif/dif * knockBack + (xdif)/Math.abs(xdif) * 2;
//			if(changeX * m.getXV() > 0){
//				m.setXV(m.getXV() + changeX);
//			}
//			else{
//				m.setXV(changeX);
//			}
//			m.setYV(Math.min(m.getYV() + ydif/dif * knockBack * 3 - 5, ydif/dif * knockBack * 3 - 10));
//		}
	}
	
	public void update(double time){
		if(System.currentTimeMillis() < explodeTimer + 500){
			if(!explode){
				explode();
			}
			current = explosion.loop(time);
			return;
		}
		else if(explodeTimer != -2000){
			if(explode){
				kill();
			}
		}
		
		//homing code
//		if(b == null || b.needRemoval() == true){
//			b = null;
//			xv += xa * time;
//			x += time * xv;
//			return;
//		}
//		
//		b = getClosestBaddie();
//		if(b == null){
//			xv += xa * time;
//			x += time * xv;
//			y += time * yv;
//			return;
//		}
//		int xdif = (int) (b.getMidX() - this.getMidX());
//		int ydif = (int) (b.getMidY() - this.getMidY());
//		double dif = Math.pow(xdif * xdif + ydif * ydif, .5);
//		
//		if(dif != 0){
//			if(xv * xdif > 0){
//				xv += (((double) xdif/dif) * homingLvl);
//			}
//			yv += (((double) ydif/dif) * homingLvl);
//		}
//		
//		
//		if(speed() > TERMINAL_VELOCITY){
//			double scale = TERMINAL_VELOCITY / speed();
//			xv *= scale;
//			yv *= scale;
//		}
//		x += time * xv;
//		y += time * yv;
	}
	
	public Baddie getClosestBaddie(){ //improve so that it checks for directness
		Baddie minBad = null;
		int minDist = range + 1;
		for(Baddie bad: GamePanel.getEL().getBaddies(new Rectangle((int)(x - range), (int)(y - range), 2 * range, 2 * range))){
			int dist = distance(bad);
//			System.out.println(dist + " " + (facingRight * (bad.getMidX() - this.getMidX())) + " " + Math.abs(bad.getMidY() - this.getMidY()));
//			if(dist < minDist && (xv * (bad.getMidX() - this.getMidX()) > 0) && Math.abs(bad.getMidY() - this.getMidY()) < .7 * dist){
				boolean intersect = false;
				for(Platform p: GamePanel.getEL().getPlatforms(GamePanel.getCurrScreen(20))){
					if(p.makeRect() .intersectsLine(this.getMidX(), this.getMidY(), bad.getMidX(), bad.getMidY())){
						intersect = true;
						break;
					}
				}
				if(!intersect){
					minDist = dist;
					minBad = bad;
				}
//			}
		}
		return minBad;
	}
	
	public int distance(Baddie b){
		return (int) Math.pow(Math.pow(this.getMidX() - b.getMidX(), 2) + Math.pow(this.getMidY() - b.getMidY(), 2), .5);
	}
	
	
	
	public void onHit(){
		if(explodeTimer == -2000)
			setExplosionTimer();
	}
	
	public void setExplosionTimer(){
		explodeTimer = System.currentTimeMillis();
	}
	

}
