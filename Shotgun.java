import java.awt.image.BufferedImage;


public class Shotgun extends RangedWeapon{

	public Shotgun(String name, BufferedImage b, int firerate, int facingRight,
			int clipsize, int reloadSpeed, double velocity, double accel,
			int bulletsize, int damage, Movable owner) {
		super(name, b, firerate, facingRight, clipsize, reloadSpeed, velocity, accel,
				bulletsize, damage, owner);
		// TODO Auto-generated constructor stub
	}

}
