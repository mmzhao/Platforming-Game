import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class RocketLauncher extends Weapon{
	
//	homingLvl: measure of how much homing will affect projectile movement
//	range: how far the homing rocket can look for a target
	int homingLvl;
	int range;
	
// --------------------------------CONSTRUCTOR-------------------------------- //
	
	public RocketLauncher(int range){
		super("RocketLauncher", null, 15, 1, 20, 100, 10, 0, 4, 100, null);
		BufferedImage img = null;
		try{
			img = ImageIO.read(getClass().getResource("SamplePistol.png"));
		} catch(IOException e){}
		bi = img;
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
	
	public void fire() { //currently fires from center of owner, needs to change
		//reimplement canFire later but now no cuz funsies
		if(canFire){
			lastFired = GamePanel.getUpdateCycle();
			es.add(new EmptyShell(null, x, y, Math.random() * 5 - 2 , -5));
			ps.add(new HomingRocket(null, owner.getMidX(), owner.getMidY(), bulletsize, bulletsize, fireX * velocity, fireY * velocity, damage, homingLvl, range));
//			ps.add(new HomingRocket(null, x - 2.5 - facingRight * 10, y - 1, bulletsize, bulletsize, fireX * velocity, fireY * velocity, damage, homingLvl, range));
		}
	}

}
