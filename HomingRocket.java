import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


public class HomingRocket extends Projectile{
	private int homingLvl;
	private Baddie b;
	private int range;
	private boolean on;
	private long explodeTimer;
	private boolean explode;
	
	private Animation explosion;
	private BufferedImage current = null;
	
	private final String[] EXPLOSIONS = {"DarknessExplosion", "Explosion1", "Explosion2", "Explosion3", "IceExplosion"};
	private final int TERMINAL_VELOCITY = 18;
	
	public HomingRocket(BufferedImage b, double x, double y, double w,
			double h, double xv, double xa, int dmg, int homingLvl) {
		super(b, x, y, w, h, xv, xa, dmg);
		this.homingLvl = homingLvl;
		this.b = null;
		on = false;
		explodeTimer = -2000;
		explode = false;
	}
	
	public HomingRocket(BufferedImage b, double x, double y, double w,
			double h, double xv, double xa, int dmg, int homingLvl, int range) {
		super(b, x, y, w, h, xv, xa, dmg);
		this.homingLvl = homingLvl;
		this.b = null;
		this.range = range;
		on = false;
		explodeTimer = -2000;
		explode = false;
		explosion = Explosions.getRandom();
	}
	
	public void draw(Graphics g, int offsetX, int offsetY){
		draw(g, offsetX, offsetY, 1, 1);
	}
	
	public void draw(Graphics g, int offsetX, int offsetY, double scaleX, double scaleY) {
		if(explode){
			g.drawImage(current, (int)((x - offsetX) * scaleX), (int) ((y - offsetY) * scaleY), (int) (w * scaleX), (int) (h * scaleY), null);
			return;
		}
		
		g.setColor(Color.black);
		
		if(b != null){
			on = true;
		}
		else{
			on = false;
		}
		
		if(on){
			g.setColor(Color.red);
		}
		
		if(on){
			int xdif = (int) (b.getMidX() - this.getMidX());
			int ydif = (int) (b.getMidY() - this.getMidY());
			int dif = (int) Math.pow(xdif * xdif + ydif * ydif, .5);
			
			if(dif != 0){
				if(xv * xdif > 0){
					xv += (((double) xdif/dif) * homingLvl);
				}
				yv += (((double) ydif/dif) * homingLvl);
			}
			g.drawLine((int) ((x - offsetX) * scaleX), (int) ((y - offsetY + 3) * scaleY), (int) ((x - offsetX + xv * 3) * scaleX), (int) ((y - offsetY + yv * 3 + 3) * scaleY));
		}
		else
			g.drawLine((int) ((x - offsetX) * scaleX), (int) ((y - offsetY + 3) * scaleY), (int) ((x - offsetX + xv * 3) * scaleX), (int) ((y - offsetY + yv * 3 + 3) * scaleY));
//			g.fillOval((int) x - offsetX, (int) y - offsetY, (int) w, (int) h);
		
		
	}
	
	
	public void explode(){
		x -= 5 * w;
		y -= 5 * h;
		w *= 10;
		h *= 10;
		dmg = 1;
		explode = true;
		explosion.start();
	}
	
	public void update(double time){
		if(System.currentTimeMillis() < explodeTimer + 1000){
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
//		if(b == null || b.needRemoval() == true){
//			b = null;
//			xv += xa * time;
//			x += time * xv;
//			return;
//		}
		
		b = getClosestBaddie();
		if(b == null){
			xv += xa * time;
			x += time * xv;
			y += time * yv;
			return;
		}
		int xdif = (int) (b.getMidX() - this.getMidX());
		int ydif = (int) (b.getMidY() - this.getMidY());
		double dif = Math.pow(xdif * xdif + ydif * ydif, .5);
		
		if(dif != 0){
			if(xv * xdif > 0){
				xv += (((double) xdif/dif) * homingLvl);
			}
			yv += (((double) ydif/dif) * homingLvl);
		}
		
		
		if(speed() > TERMINAL_VELOCITY){
			double scale = TERMINAL_VELOCITY / speed();
			xv *= scale;
			yv *= scale;
		}
		x += time * xv;
		y += time * yv;
	}
	
	public Baddie getClosestBaddie(){ //improve so that it checks for directness
		Baddie minBad = null;
		int minDist = range + 1;
		for(Baddie bad: GamePanel.getEL().getBaddies(new Rectangle((int)(x - range), (int)(y - range), 2 * range, 2 * range))){
			int dist = distance(bad);
//			System.out.println(dist + " " + (facingRight * (bad.getMidX() - this.getMidX())) + " " + Math.abs(bad.getMidY() - this.getMidY()));
			if(dist < minDist && (xv * (bad.getMidX() - this.getMidX()) > 0) && Math.abs(bad.getMidY() - this.getMidY()) < .7 * dist){
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
			}
		}
		return minBad;
	}
	
	public int distance(Baddie b){
		return (int) Math.pow(Math.pow(this.getMidX() - b.getMidX(), 2) + Math.pow(this.getMidY() - b.getMidY(), 2), .5);
	}
	
	public double speed(){
		return Math.pow(xv * xv + yv * yv, .5);
	}
	
	public void onHit(){
		if(explodeTimer == -2000)
			setExplosionTimer();
	}
	
	public void setExplosionTimer(){
		explodeTimer = System.currentTimeMillis();
	}
	

}
