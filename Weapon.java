import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Weapon extends Item{
	
	protected ArrayList<Projectile> ps;
	protected Movable owner;
	protected long lastFired;
	protected int firerate;
	protected int facingRight;
	protected int clipsize;
	protected int reloadSpeed;
	protected double velocity;
	protected double accel;
	protected int bulletsize;
	protected int damage;

	public Weapon(String name, BufferedImage b, int firerate, int facingRight, int clipsize, int reloadSpeed, double velocity,
					double accel, int bulletsize, int damage, Movable owner){
		super(b, 0, 0, 0, 0, false, name);
		ps = new ArrayList<Projectile>();
		lastFired = 0;
		this.firerate = firerate;
		this.facingRight = facingRight;
		this.clipsize = clipsize;
		this.reloadSpeed = reloadSpeed;
		this.velocity = velocity;
		this.accel = accel;
		this.bulletsize = bulletsize;
		this.damage = damage;
		this.owner = owner;
	}
	
	public void update(double time){
//		System.out.println(ps.size());
		if(owner != null){
			facingRight = owner.getFacingRight();
			w = owner.getW()/2;
			h = owner.getH()/2;
			x = owner.getX() + 9;
			y = owner.getY() + 3*(int)h/4;
			x += facingRight * w;
		}
		else{
			x = 0;
			y = 0;
		}
		for(int i = 0; i < ps.size(); i++){
			ps.get(i).update(time);
		}
		updateProjectiles();
	}
	
	protected boolean canFire(){
//		if(System.currentTimeMillis()-lastFired >= firerate){
			return true;
//		}
//		return false;
	}
	
	public void draw(Graphics g){
		draw(g, 0, 0);
	}
	
	public void draw(Graphics g, int offsetX, int offsetY){
		if (facingRight == 1) {
			g.drawImage(super.bi, (int)x - offsetX, (int)y - offsetY, (int)w, (int)h, null, null);
		} else{
			g.drawImage(super.bi, (int)x + (int)w/6 - offsetX, (int)y - offsetY, -(int)w,(int)h, null, null);
		}
	}
	
	public void fire() { 
		if(canFire()){
			lastFired = System.currentTimeMillis();
			ps.add(new Projectile(null, x - 2.5 + facingRight * 10, y - 1, bulletsize, bulletsize, facingRight * velocity, facingRight*accel, damage));
		}
	}
	
	public void updateProjectiles() {
		for (int i = ps.size() - 1; i >= 0; i--) {
			if (ps.get(i).needRemoval())
				ps.remove(i);
		}
	}
	
	public ArrayList<Projectile> getProjectiles() {
		return ps;
	}

	public void setProjectile(ArrayList<Projectile> ps) {
		this.ps = ps;
	}

	public void removeProjectile(Projectile p) {
		ps.remove(p);
	}
	
	public void setOwner(Movable m){
		owner = m;
	}
}
