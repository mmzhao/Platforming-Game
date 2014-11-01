import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.io.output.ByteArrayOutputStream;


public class Animation {
//	bi: list of buffered images that are looped through to create animation
//	spriteMap: sprite map that will be split to make bi
//	spriteWidth: width of the sprite
//	spriteHeight: height of the sprite
//	current: buffered image from bi the animation is currently on
//	loopTime: amount of time for one loop
//	elapsedTime: amount of time that has elapsed
//	started: whether or not the animation has started

	protected BufferedImage[] bi;
	protected BufferedImage spriteMap;
	protected int spriteWidth;
	protected int spriteHeight;
	protected int current;
	protected double loopTime;
	protected double elapsedTime;
	protected boolean started = false;
	
	
// --------------------------------CONSTRUCTOR-------------------------------- //
	
	public Animation(String path, int spriteWidth, int spriteHeight, double loopTime){
		spriteMap = loadImage(path);
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
		bi = splitImage(spriteMap, spriteWidth, spriteHeight);
		current = 0;
		this.loopTime = loopTime;
		elapsedTime = 0;
	}
	
	public Animation(String path, int x, int y, int spriteWidth, int spriteHeight, double loopTime){
		spriteMap = ImageGetter.getSVG(path, x, y, this);
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
		bi = splitImage(spriteMap, spriteWidth, spriteHeight);
		current = 0;
		this.loopTime = loopTime;
		elapsedTime = 0;
	}
	
	public Animation(BufferedImage[] bi, int spriteWidth, int spriteHeight, double loopTime){
		this.bi = bi;
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
		current = 0;
		this.loopTime = loopTime;
		elapsedTime = 0;
	}
	
	
// --------------------------------SET UP ANIMATION-------------------------------- //	
	
	//loads initial sprite map image
	private BufferedImage loadImage(String path){
		BufferedImage img = null;
		try{
			img = ImageIO.read(getClass().getResource(path));
		} catch(IOException e){}
		return img;
	}
	
	
	//splits spriteMap into buffered images for bi
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
	
// -----------------------------ANIMATION LOOP CONTROL AND IMAGE FINDING--------------------------- //	
	
	//start-stop for animation loop
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

	//finds which buffered image should be shown
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
	
	
// ------------------------------GET/SET METHODS------------------------------- //
	
	public Animation get(){
		return new Animation(bi, spriteWidth, spriteHeight, loopTime);
	}
	
	public boolean getStarted(){
		return started;
	}
	
}
