import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Weapon extends Item{
	
	protected ArrayList<Projectile> ps;
	protected ArrayList<EmptyShell> es;
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
	protected boolean canFire;
	
	protected int numBullets;
	protected long startReload;
	protected boolean reloading = false;

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
		canFire = true;
		es = new ArrayList<EmptyShell>();
		
		numBullets = clipsize;
		startReload = 0;
	}
	
	public void update(double time){
		if(GamePanel.getUpdateCycle()-lastFired <= firerate)
			canFire = false;
		else if(numBullets == 0)
			canFire = false;
		else if(reloading)
			canFire = false;
		else
			canFire = true;
		if(reloading){
			updateReload();
		}
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
		for(int i = 0; i < es.size(); i++){
			es.get(i).update(time);
		}
		if(es.size() >= 40){
			es.remove(0);
		}  
		updateProjectiles();
		updateEmptyShells();
	}
	
	
	public void draw(Graphics g){
		draw(g, 0, 0);
	}
	
	public void draw(Graphics g, int offsetX, int offsetY){
		draw(g, offsetX, offsetY, 1, 1);
	}
	
	public void draw(Graphics g, int offsetX, int offsetY, double scaleX, double scaleY){
		if (facingRight == 1) {
			g.drawImage(super.bi, (int)((x - offsetX) * scaleX), (int)((y - offsetY) * scaleY), (int)(w * scaleX), (int)(h * scaleY), null, null);
		} else{
			g.drawImage(super.bi, (int)((x + (int)w/6 - offsetX) * scaleX), (int)((y - offsetY) * scaleY), -(int)(w * scaleX),(int)(h * scaleY), null, null);
		}
		for(int i = 0; i < es.size(); i++){
			es.get(i).draw(g, offsetX, offsetY, scaleX, scaleY);
		}
	}
	
	public void reload(){
		if(!reloading){
			reloading = true;
			startReload = GamePanel.getUpdateCycle();
		}
	}
	
	public void updateReload(){
		if(GamePanel.getUpdateCycle() < startReload + reloadSpeed){}
		else{
			numBullets = clipsize;
			reloading = false;
		}
	}
	
	public void fire() { 
		if(canFire){
			lastFired = GamePanel.getUpdateCycle();
			es.add(new EmptyShell(null, x, y, Math.random() * 5 - 2 , -5));
			ps.add(new Projectile(null, x - 2.5 + facingRight * 10, y - 1, bulletsize, bulletsize, facingRight * velocity, facingRight*accel, damage));
		}
	}
	
	public void updateProjectiles() {
		for (int i = ps.size() - 1; i >= 0; i--) {
			if (ps.get(i).needRemoval())
				ps.remove(i);
		}
	}
	
	public void updateEmptyShells() {
		for (int i = es.size() - 1; i >= 0; i--) {
			if (es.get(i).needRemoval())
				es.remove(i);
		}
	}
	
	public ArrayList<Projectile> getProjectiles() {
		return ps;
	}

	public void setProjectile(ArrayList<Projectile> ps) {
		this.ps = ps;
	}
	
	public ArrayList<EmptyShell> getEmptyShells() {
		return es;
	}

	public void setEmptyShells(ArrayList<EmptyShell> es) {
		this.es = es;
	}

	public void removeProjectile(Projectile p) {
		ps.remove(p);
	}
	
	public void setOwner(Movable m){
		owner = m;
	}
}
