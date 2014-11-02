import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class RocketLauncher extends RangedWeapon{
	
//	homingLvl: measure of how much homing will affect projectile movement
//	range: how far the homing rocket can look for a target
	int homingLvl;
	int range;
	
// --------------------------------CONSTRUCTOR-------------------------------- //
	
	public RocketLauncher(int range){
		super("RocketLauncher", null, 35, 1, 10, 100, 20, -2, 9, 100, null);
		bi = ImageGetter.getSVG("RocketLauncher.svg", 404, 105);
		homingLvl = 1;
		this.range = range;
	}
	
	public RocketLauncher(int homingLvl, int range){
		super("Pistol", null, 15, 1, 20, 100, 20, 0, 20, 100, null);
		BufferedImage img = null;
		try{
			img = ImageIO.read(getClass().getResource("SamplePistol.png"));
		} catch(IOException e){}
		bi = img;
		this.homingLvl = homingLvl;
		this.range = range;
	}
	
// --------------------------------FIRE METHOD-------------------------------- //
	
	public void attack() { 
		//reimplement canFire later but now no cuz funsies
		if(canFire){
			lastFired = GamePanel.getUpdateCycle();
//			es.add(new EmptyShell(null, x, y, Math.random() * 5 - 2 , -5));
			double exitX = getMidX() + Math.cos(getAngle()) * w / 2 - owner.facingRight * 15;
			double exitY = getMidY() + Math.sin(getAngle()) * w / 2 - owner.facingRight * 9;
			ps.add(new Rocket(null, (int)(owner.getMidX()), (int)(owner.getMidY()), bulletsize, bulletsize, fireX * velocity, fireY * velocity, damage, getAngle(facingRight)));
//			ps.add(new Rocket(null, owner.getMidX(), y, bulletsize, bulletsize, fireX * velocity, fireY * velocity, damage, super.getAngle()));
//			ps.add(new HomingRocket(null, x - 2.5 - facingRight * 10, y - 1, bulletsize, bulletsize, fireX * velocity, fireY * velocity, damage, homingLvl, range));
			numBullets--;
		}
		if(numBullets == 0){
			reload();
		}
	}
	

}
