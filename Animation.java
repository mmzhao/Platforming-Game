import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class Animation {
	protected BufferedImage[] bi;
	protected BufferedImage spriteMap;
	protected int spriteWidth;
	protected int spriteHeight;
	protected int current;
	protected double loopTime; //amount of time for one loop
	protected double elapsedTime;
	protected boolean started = false;
	
	public Animation(String path, int spriteWidth, int spriteHeight, double loopTime){
		spriteMap = loadImage(path);
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
		bi = splitImage(spriteMap, spriteWidth, spriteHeight);
		current = 0;
		this.loopTime = loopTime;
		elapsedTime = 0;
	}
	
	private BufferedImage[] splitImage(BufferedImage spriteMap, int w, int h){
		int pWidth = spriteMap.getWidth() / w; // width of each sprite
		int pHeight = spriteMap.getHeight() / h; // height of each sprite
		BufferedImage[] sprites = new BufferedImage[w*h];
		int n = 0;
		
		for(int y=0; y < h; y++) {
			for(int x = 0; x < w; x++) {
				sprites[n] = new BufferedImage(pWidth, pHeight, 2);
                Graphics2D g = sprites[n].createGraphics(); // retrieve graphics to draw onto the BufferedImage
                // draws a portion of the spriteMap into sprites by directly drawing on the BufferedImage
                g.drawImage(spriteMap, 0, 0, pWidth, pHeight, pWidth*x, pHeight*y, pWidth*x+pWidth, pHeight*y+pHeight, null); 
                g.dispose();
                n++; // next sprite
			}
		}
		return sprites;
	}
	
	public void start(){
		if(!started){
			elapsedTime = 0;
			current = 0;
		}
		started = true;
	}
	
	public void stop(){
		started = false;
	}
	
	public boolean getStarted(){
		return started;
	}
	
	public BufferedImage loop(double time){
		elapsedTime += time;
		if(elapsedTime >= loopTime){
			elapsedTime -= loopTime;
			if(current == bi.length - 1){
				current = 0;
			}
			else
				current++;
		}
		return bi[current];
	}
	
	private BufferedImage loadImage(String path){
		BufferedImage img = null;
		try{
			img = ImageIO.read(getClass().getResource(path));
		} catch(IOException e){}
		return img;
	}
	
}
