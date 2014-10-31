import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Entity {

//	bi: buffered image that is displayed on screen for the entity
//	x: x position on base screen (420 by 330)
//	y: y position on base screen (420 by 330)
//	w: width on base screen (420 by 330)
//	h: height on base screen (420 by 330)
//	collidable: can be collided with others
//	isPlatform: whether or not this entity is a platform
//	remove: whether or not this entity should be removed from entity list in next frame update
//	activated: whether or not this entity (baddie) has been on screen
	protected BufferedImage bi;
	protected double x; // x increases from left/west to right/east
	protected double y; // y increases from top/north to bottom/south
	protected double w;
	protected double h;
	protected boolean collidable;
	protected boolean isPlatform;
	protected boolean remove;
	protected boolean activated;
	
//	EXTRA: extra that is added to very side of an entity when the entity list looks for it
	private final int EXTRA = 0;
	
// --------------------------------CONSTRUCTOR-------------------------------- //
	
	public Entity(){
		
	}
	
	public Entity(BufferedImage b, double x, double y, double w, double h, boolean c) {
		bi = b;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		collidable = c;
		isPlatform = false;
		remove = false;
		activated = false;
	}
	
// --------------------------------DRAW METHODS-------------------------------- //
	
	public void draw(Graphics g, int offsetX, int offsetY, double scaleX, double scaleY){
		
	}
	
	public void draw(Graphics g, int offsetX, int offsetY) {
		
	}
	
	public void draw(Graphics g){
		
	}

// --------------------------------UPDATE-------------------------------- //
	
	public void update(){
		
	}
	
	public void update(double time){
		
	}
	
// --------------------------RECTANGLE-MAKING METHODS FOR COLLISION AND ENTITYLIST METHODS--------------------------- //
	
	public Rectangle makeRect(){
		return new Rectangle((int) x, (int) y, (int) w, (int) h);
	}
	
	public Rectangle makeRectExtra(){
		return new Rectangle((int)(x - EXTRA), (int)(y - EXTRA), (int)(w + 2 * EXTRA), (int)(h + 2 * EXTRA));
	}
	
// --------------------------------START/END LIFE METHODS-------------------------------- //
	
	public boolean needRemoval(){
		return remove;
	}
	
	public void kill(){
		remove = true;
	}
	
	public boolean isActivated(){
		return activated;
	}
	
	public void activate(){
		activated = true;
	}
	
// --------------------------------GET/SET METHODS-------------------------------- //
	
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
	
	
	public BufferedImage getImage(){
		return bi;
	}
	
	public BufferedImage setImage(BufferedImage b){
		return b;
	}
	
// --------------------------------KEY/MOUSE LISTENER METHODS-------------------------------- //
	
	public void keyPressed(KeyEvent e) {

    }
    
    public void keyReleased(KeyEvent e) {
    	
    }
    
    public void keyTyped(KeyEvent e) {
    
    }
    
    public void mousePressed(MouseEvent e) {
		
	}

	public void mouseReleased(MouseEvent e) {
		
	}

	public void mouseMoved(MouseEvent e) {
		
	}

	public void mouseDragged(MouseEvent e) {
		
	}

	


}