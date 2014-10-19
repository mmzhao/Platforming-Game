import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class RocketLauncher extends Weapon{
//	EntityList el;
	int homingLvl;
	int range;
	
	public RocketLauncher(int range){
		super("RocketLauncher", null, 200, 1, 20, 100, 3, 0, 5, 100, null);
		BufferedImage img = null;
		try{
			img = ImageIO.read(getClass().getResource("SamplePistol.png"));
		} catch(IOException e){}
		bi = img;	
//		this.el = el;
		homingLvl = 1;
		this.range = range;
	}
	
	public RocketLauncher(int homingLvl, int range){
		super("Pistol", null, 200, 1, 20, 100, 1, 0, 5, 100, null);
		BufferedImage img = null;
		try{
			img = ImageIO.read(getClass().getResource("SamplePistol.png"));
		} catch(IOException e){}
		bi = img;	
//		this.el = el;
		this.homingLvl = homingLvl;
		this.range = range;
	}
	
	public void fire() {
		//reimplement canFirelater but now no cuz funsies
//		if(canFire){
			lastFired = System.currentTimeMillis();
			es.add(new EmptyShell(null, x, y, Math.random() * 5 - 2 , -5));
			ps.add(new HomingRocket(null, x - 2.5 + facingRight * 10, y - 1, bulletsize, bulletsize, facingRight * velocity, facingRight*accel, damage, homingLvl, range));
//		}
	}

}
