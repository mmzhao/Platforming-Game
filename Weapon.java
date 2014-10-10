import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Weapon extends Item{
	
	protected ArrayList<Projectile> ps;
	protected Entity owner;
	protected long lastFired;
	protected int firerate;
	protected int facingRight;
	protected int clipsize;
	protected int reloadSpeed;
	
	
	public Weapon(String name, BufferedImage b, double x, double y, double w, double h, boolean c, int firerate, int fr, int clipsize, int reloadSpeed){
		super(b, x, y, w, h, c, name);
		this.name = name;
		ps = new ArrayList<Projectile>();
		owner = null;
		lastFired = 0;
		this.firerate = firerate;
		facingRight = fr;
		this.clipsize = clipsize;
		this.reloadSpeed = reloadSpeed;
	}
	
	protected boolean canFire(){
		if(System.currentTimeMillis() -  lastFired >= firerate){
			return true;
		}
		return false;
	}
	
	public void fire(){
		
	}
	
	
}
