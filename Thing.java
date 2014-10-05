import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Animatible is an abstract class that a class should extend if it wants to be drawn using
 * an Animation. 
 */

abstract public class Thing {
	
	private int offsetX;
	private int offsetY;
	protected BufferedImage bi;

	public abstract void draw(Graphics g, int pixelX, int pixelY);
	public abstract void draw(Graphics g, int pixelX, int pixelY, int offsetX, int offsetY);
	public void update(double time){
		
	}
	
	public void setOffsetX(int offsetX) {
		this.offsetX = offsetX;
	}
	
	public void setOffsetY(int offsetY) {
		this.offsetY = offsetY;
	}
	
	public int getOffsetX() {
		return offsetX;
	}
	
	public int getOffsetY() {
		return offsetY;
	}
	
	public BufferedImage getImage(){
		return bi;
	}
}
