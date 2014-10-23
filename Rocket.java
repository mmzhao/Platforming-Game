import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Rocket extends Projectile{
	
//	explodeTimer: set to initial time when collision happens to control how long explosion lasts
//	explode: whether or not this rocket has exploded
//	explodeRadius: radius of explosion for baddies, 1.5x as much for player
//	knockBack: measure of knock back intensity
	private long explodeTimer;
	private boolean explode;
	private double explodeRadius;
	private double knockBack;
	
//	explosion: animation for explosion
//	current: image that is currently showing for the rocket
	private Animation explosion;
	private BufferedImage current = null;
	
//	EXPLOSIONS: string list of possible explosion types that are randomly chosen from
//	TERMINAL_VELOCITY: max magnitude of velocity (x and y combined)
//	LIFE_DURATION: number of milliseconds that this projectile stays before exploding
	private final String[] EXPLOSIONS = {"DarknessExplosion", "Explosion1", "Explosion2", "Explosion3", "IceExplosion"};
	private final int TERMINAL_VELOCITY = 20;
	private final static long LIFE_DURATION = 2000;
	
// --------------------------------CONTRUCTOR-------------------------------- //	
	
	public Rocket(BufferedImage b, double x, double y, double w, double h, double xv, double yv, int dmg) {
		super(b, x, y, w, h, xv, yv, 0, 0, dmg);
		explodeTimer = -2000;
		explode = false;
		explosion = Explosions.getRandom();
		explodeRadius = 20;
		knockBack = 5;
	}
	
// --------------------------------DRAW METHODS-------------------------------- //
	
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
		
		g.setColor(Color.blue);
		g.drawLine((int) ((x - offsetX + w/2) * scaleX), (int) ((y - offsetY + h/2) * scaleY), (int) ((x - offsetX + w/2 - xv * 3) * scaleX), (int) ((y - offsetY + h/2 - yv * 3) * scaleY));
		g.fillOval((int) ((x - offsetX) * scaleX), (int) ((y - offsetY) * scaleY), (int) (w * scaleX), (int) (h * scaleY));
	}
	
// --------------------------------UPDATE-------------------------------- //
	
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
		else if(!explode && System.currentTimeMillis() > timer + Rocket.LIFE_DURATION){
			explode();
			setExplosionTimer();
			return;
		}
		
		if(speed() > TERMINAL_VELOCITY){
			double scale = TERMINAL_VELOCITY / speed();
			xv *= scale;
			yv *= scale;
		}
		x += time * xv;
		y += time * yv;
	}

// --------------------------------COLLISION METHODS-------------------------------- //

	public void onHit(){
		if(explodeTimer == -2000)
			setExplosionTimer();
	}
	
	public void setExplosionTimer(){
		explodeTimer = System.currentTimeMillis();
	}
	
// --------------------------------EXPLOSION METHODS-------------------------------- //
	
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
		double xdif = (m.getMidX() - this.getMidX());
		double ydif = (m.getMidY() - this.getMidY());
		double dif = Math.pow(xdif * xdif + ydif * ydif, .5);
		
		if(m instanceof Baddie){
			m.setXV(m.getXV() + xdif/dif * knockBack);
			m.setYV(Math.min(m.getYV() + ydif/dif * knockBack - 15, ydif/dif * knockBack - 15));
			return;
		}
		
		else if(m instanceof Player){
			double changeX = xdif/dif * knockBack + (xdif)/Math.abs(xdif) * 2;
			if(changeX * m.getXV() > 0){
				m.setXV(m.getXV() + changeX);
			}
			else{
				m.setXV(changeX);
			}
			m.setYV(Math.min(m.getYV() + ydif/dif * knockBack * 3 - 5, ydif/dif * knockBack * 3 - 10));
		}
	}
	

}
