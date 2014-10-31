import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.text.Position;

public class Weapon extends Item{
	
//	ps: list of all projectiles fired from this weapon
//	es: list of all empty shells emitted from this weapon
//	owner: owner of this weapon
//	lastFired: system time for when this weapon was last fired
//	firerate: minimum system millisecond time between shots
//	facingRight: whether or not this weapon is facing right
//	clipsize: max number of projectiles in this weapons clip
//	reloadSpeed: system millisecond time it takes to reload this weapon
//	velocity: magnitude of velocity given to a projectile fired from this weapon
//	accel: magnitude of acceleration given to a projectile fired from this weapon
//	bulletsize: size of the bullet fired from this weapon
//	damage: damage dealt from a bullet fired from this weapon
//	canFire: whether or not this weapon can fire at this point in time
//	fireX, fireY: unit vector direction for a fired projectile
//	numBullets: number of bullets left in the clip
//	startReload: system millisecond start time for the reload
//	reloading: whether or not this weapon is reloading
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
	protected double fireX, fireY;
	protected int numBullets;
	protected long startReload;
	protected boolean reloading = false;
	protected AffineTransform  at;
	
	protected double pivotX = 0;
	protected double pivotY = 0;
	
	protected final double minChange = 1;
	
// --------------------------------CONSTRUCTOR-------------------------------- //
	
	public Weapon(BufferedImage b, String name) {
		super(b, 0, 0, 0, 0, false, name);
	}
	
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
		at = new AffineTransform();
        at.translate(x, y);
        

		w = 58;
		h = 15;
        
	}
	
// --------------------------------DRAW METHODS-------------------------------- //

	public void draw(Graphics g){
		draw(g, 0, 0);
	}
	
	public void draw(Graphics g, int offsetX, int offsetY){
		draw(g, offsetX, offsetY, 1, 1);
	}
	
	public void draw(Graphics g, int offsetX, int offsetY, double scaleX, double scaleY){
//		System.out.println(getMidX());
//		System.out.println(getMidY());
//		System.out.println(x);
//		System.out.println(y);
//		System.out.println(w);
//		System.out.println(h);
//		System.out.println();
//		pivotX = (x - offsetX) * scaleX + w/2;
//		if(facingRight == -1)
//			pivotX -= 3*w/2;
//		pivotY =  (y - offsetY) * scaleY + h/2;
		
//		double newPivotX = (getMidX() - offsetX) * scaleX;
//		double newPivotY = (getMidY() - offsetY) * scaleY;
//		if(Math.abs(newPivotX - pivotX) > Math.abs(minChange * scaleX)){
//			pivotX = newPivotX;
//		}
//		if(Math.abs(newPivotY - pivotY) > Math.abs(minChange * scaleX)){
//			pivotY = newPivotY;
//		}
		
//		double px = w/2;
//		double py = h/2;
//		at.setToRotation(getAngle(facingRight), px, py);
//		
//		((Graphics2D) g).translate((getMidX() - offsetX) * scaleX, (getMidY() - offsetY) * scaleY);
//		
//		if (facingRight == 1) {
//			((Graphics2D) g).drawImage(bi, at, null);
//		} else{
//			((Graphics2D) g).drawImage(bi, at, null);
//		}
//		for(int i = 0; i < es.size(); i++){
//			es.get(i).draw(g, offsetX, offsetY, scaleX, scaleY);
//		}

		
		pivotX = (getMidX() - offsetX) * scaleX;
		pivotY =  (getMidY() - offsetY) * scaleY;
		at.setToRotation(getAngle(), pivotX, pivotY);
		
		Graphics2D newGraphics = (Graphics2D)g.create();
		newGraphics.setTransform(at);
		
		
		if (facingRight == 1) {
			newGraphics.drawImage(bi, (int)((x - offsetX) * scaleX), (int)((y - offsetY) * scaleY), (int)(w * scaleX), (int)(h * scaleY), null, null);
		} else{
			newGraphics.drawImage(bi, (int)((x - offsetX + w) * scaleX), (int)((y - offsetY) * scaleY), -(int)(w * scaleX), (int)(h * scaleY), null, null);
		}
		for(int i = 0; i < es.size(); i++){
			es.get(i).draw(g, offsetX, offsetY, scaleX, scaleY);
		}
		
			
	}

// --------------------------------UPDATE-------------------------------- //
	
	public void update(double time){
		updateFireVector();
		facingRight = owner.getFacingRight();
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
//			facingRight = owner.getFacingRight();
//			x = owner.getMidX() - w/2;
			double newX = owner.getMidX() - 1 * w/2;
			double newY = owner.getMidY() - 2 * owner.getH()/5;
//			if(facingRight == -1)
//				newY -= w/7;
			if(Math.abs(newX - x) > Math.abs(minChange)){
				x = newX;
			}
			if(Math.abs(newY - y) > Math.abs(minChange)){
				y = newY;
			}
//			x = owner.getMidX() - w/2;
//			y = owner.getMidY() - 2*owner.getH()/5;

//			System.out.println(owner.getMidX());
//			System.out.println(owner.getMidY());
//			System.out.println(x);
//			System.out.println(y);
//			System.out.println(w);
//			System.out.println(h);
//			System.out.println();
			
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
	
	public void updateReload(){
		if(GamePanel.getUpdateCycle() < startReload + reloadSpeed){}
		else{
			numBullets = clipsize;
			reloading = false;
		}
	}
	
	public void updateFireVector(){
//		System.out.println(owner.getMouseY() + " " + getMidY());
//		double difX = owner.getMouseX() - (x - 2.5 + facingRight * 10);
//		double difY = owner.getMouseY() - (y - 1);
		double difX = owner.getMouseX() - getMidX();
		double difY = owner.getMouseY() - getMidY();
		double magnitude = Math.pow(difX * difX +  difY * difY, .5);
//		double newFireX = difX / magnitude;
//		double newFireY = difY / magnitude;
//		if(Math.abs(newFireX - fireX) > Math.abs(minChange)){
//			fireX = newFireX;
//		}
//		if(Math.abs(newFireY - fireY) > Math.abs(minChange)){
//			fireY = newFireY;
//		}
		fireX = difX / magnitude;
		fireY = difY / magnitude;
//		if(fireX < 0){
//			facingRight = -1;
//		}
//		else{
//			facingRight = 1;
//		}
//		System.out.println(fireX);
//		System.out.println(fireY);
//		System.out.println();
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
	
// --------------------------------RELOAD AND FIRE METHODS-------------------------------- //

	public void reload(){
		if(!reloading){
			reloading = true;
			startReload = GamePanel.getUpdateCycle();
		}
	}
	
	public void fire() {
//		if(canFire){
			lastFired = GamePanel.getUpdateCycle();
			es.add(new EmptyShell(null, x, y, Math.random() * 5 - 2 , -5));
			ps.add(new Projectile(null, owner.getMidX(), owner.getMidY(), bulletsize, bulletsize, fireX * velocity, fireY * velocity, fireX * accel, fireY * accel, damage));
//			ps.add(new Projectile(null, x - 2.5 - facingRight * 10, y - 1, bulletsize, bulletsize, fireX * velocity, fireY * velocity, fireX * accel, fireY * accel, damage));
//			ps.add(new Projectile(null, x - 2.5 + facingRight * 10, y - 1, bulletsize, bulletsize, facingRight * velocity, facingRight*accel, damage));
			
//		}
	}
	
// --------------------------------GET/SET METHODS-------------------------------- //
	
	public double getAngle(){
		double angle = Math.atan(fireY/fireX);
//		if(facingRight == -1)
//			angle += Math.PI;
		return angle;
	}
	
	public double getAngle(int facingRight){
		double angle = Math.atan(fireY/fireX);
		if(facingRight == -1)
			angle += Math.PI;
		return angle;
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
