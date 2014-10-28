import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class Pistol extends Weapon{
	
	public Pistol(){
		super("Pistol", null, 5, 1, 20, 1000, 20, .25, 5, 1, null);
		BufferedImage img = null;
		try{
			img = ImageIO.read(getClass().getResource("SamplePistol.png"));
		} catch(IOException e){}
		bi = img;	
	}
	
	public Pistol(BufferedImage img){
		super("Pistol", null, 5, 1, 20, 1000, 20, .25, 5, 1, null);
		bi = img;	
	}
}
