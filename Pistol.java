import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class Pistol extends Weapon{
	
	public Pistol(){
		super("Pistol", null, 200, 1, 20, 1000, 2, .25, 5, 10000, null);
		BufferedImage img = null;
		try{
			img = ImageIO.read(getClass().getResource("SamplePistol.png"));
		} catch(IOException e){}
		bi = img;	
	}
	
}
