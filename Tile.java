import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class Tile extends Entity{
	
	public Tile(BufferedImage b, int x, int y){
		super(b, x, y, 0, 0, true);
		w = getWidth();
		h = getHeight();
	}
	
	public void draw(Graphics g, int pixelX, int pixelY) {
		g.drawImage(bi, pixelX, pixelY, null); //fix this BufferedImages don't really work here
	}
	
	public int getWidth(){
		return bi.getWidth();
	}
	
	public int getHeight(){
		return bi.getHeight();
	}

	
}
