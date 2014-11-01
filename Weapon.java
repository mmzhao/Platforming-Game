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
	protected Movable owner;
	protected int facingRight;
	protected int damage;
	protected double fireX, fireY;
	protected AffineTransform  at;
	
	protected double pivotX = 0;
	protected double pivotY = 0;
	
	protected final double minChange = 1;
	
// --------------------------------CONSTRUCTOR-------------------------------- //
	
	public Weapon(BufferedImage b, String name) {
		super(b, 0, 0, 0, 0, false, name);
	}
	
	public Weapon(String name, BufferedImage b, int facingRight, int damage, Movable owner){
		super(b, 0, 0, 0, 0, false, name);
		this.facingRight = facingRight;
		this.damage = damage;
		this.owner = owner;
		
		at = new AffineTransform();
        at.translate(x, y);
        
	}
	
// --------------------------------DRAW METHODS-------------------------------- //

	public void draw(Graphics g){
		draw(g, 0, 0);
	}
	
	public void draw(Graphics g, int offsetX, int offsetY){
		draw(g, offsetX, offsetY, 1, 1);
	}
	
	public void draw(Graphics g, int offsetX, int offsetY, double scaleX, double scaleY){
		
	}

// --------------------------------UPDATE-------------------------------- //
	
	public void update(double time){

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
	
	
// --------------------------------ATTACK METHODS-------------------------------- //
	
	public void attack() {

	}
	
// --------------------------------GET/SET METHODS-------------------------------- //
	
	public double getAngle(){
		double angle = Math.atan(fireY/fireX);
		return angle;
	}
	
	public double getAngle(int facingRight){
		double angle = Math.atan(fireY/fireX);
		if(facingRight == -1)
			angle += Math.PI;
		return angle;
	}
	
	public void setOwner(Movable m){
		owner = m;
	}
}
