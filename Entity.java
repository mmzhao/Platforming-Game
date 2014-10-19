import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Entity {

	protected BufferedImage bi;
	protected double x; // x increases from left/west to right/east
	protected double y; // y increases from top/north to bottom/south
	protected double w;
	protected double h;
	protected boolean collidable;
	protected boolean isPlatform;
	protected boolean remove;
	
	private final int PLATFORM_EXTRA = 0;

	public Entity(BufferedImage b, double x, double y, double w, double h, boolean c) {
//		bi = resize(b, w, h);
		bi = b;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		collidable = c;
		isPlatform = false;
		remove = false;
	}
	
	public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
	    Image tmp = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();

	    return dimg;
	}
	
	public void draw(Graphics g, int offsetX, int offsetY, double scaleX, double scaleY){
		
	}
	
	public void draw(Graphics g, int offsetX, int offsetY) {
		
	}
	
	public void draw(Graphics g){
		
	}

	
	public void update(){
		
	}
	
	public void update(double time){
		
	}
	
	public double getMidX() {
		return x + w / 2;
	}

	public double getMidY() {
		return y + h / 2;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getW() {
		return w;
	}

	public void setW(double w) {
		this.w = w;
	}

	public double getH() {
		return h;
	}

	public void setH(double h) {
		this.h = h;
	}

	public boolean getC() {
		return collidable;
	}

	public void setC(boolean c) {
		collidable = c;
	}
	
	public boolean needRemoval(){
		return remove;
	}
	
	public void kill(){
		remove = true;
	}
	
	public BufferedImage getImage(){
		return bi;
	}
	
	public BufferedImage setImage(BufferedImage b){
		return b;
	}
	
	public Rectangle makeRect(){
		return new Rectangle((int) x, (int) y, (int) w, (int) h);
	}
	
	public Rectangle makeRectExtra(){
		return new Rectangle((int)(x - PLATFORM_EXTRA), (int)(y - PLATFORM_EXTRA), (int)(w + 2 * PLATFORM_EXTRA), (int)(h + 2 * PLATFORM_EXTRA));
	}
	
	public void keyPressed(KeyEvent e) {

    }
    
    public void keyReleased(KeyEvent e) {
    	
    }
    
    public void keyTyped(KeyEvent e) {
    
    }


}