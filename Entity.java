import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Entity {

	protected BufferedImage bi;
	protected int x; // x increases from left/west to right/east
	protected int y; // y increases from top/north to bottom/south
	protected int w;
	protected int h;
	protected boolean collidable;
	protected boolean isPlatform;

	public Entity(BufferedImage b, int x, int y, int w, int h, boolean c) {
//		bi = resize(b, w, h);
		bi = b;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		collidable = c;
		isPlatform = false;
	}
	
	public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
	    Image tmp = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();

	    return dimg;
	} 
	
	public void draw(Graphics g, int x, int y) {
		g.drawImage(bi, x, y, null);
	}
	
	public void draw(Graphics g){
		draw(g, x, y);
	}

	public double intersect(double x1, double x2, double x3, double x4) {
		// x1 and x2 from entity 1, x1 < x2, x3 and x4 from entity 2, x3< x4
		// returns overlap
		if (x1 > x3 && x1 < x4)
			return x4 - x1;
		if (x2 > x3 && x2 < x4)
			return x2 - x3;
		return 0;
	}
	
	public Side collision(Entity e) {
		if (e.getC() && collidable) {
			double ew = intersect(e.getX(), e.getX() + e.getW(), x, x + w); // east west intersection
			double ns = intersect(e.getY(), e.getY() + e.getH(), y, y + h); // north south interaction
			Side nsOption = Side.NORTH;
			Side ewOption = Side.WEST;
			if (e.getMidX() > getMidX())
				ewOption = Side.EAST;
			if (e.getMidY() > getMidY())
				nsOption = Side.SOUTH;
			
			if(Math.abs(e.getMidY() - getMidY()) - (e.getH() + h) / 2 <= 0 && Math.abs(e.getMidX() - getMidX()) - (e.getW() + w) / 2 <= 0){
				if(x > e.getX() && x < e.getX() + e.getW()) return nsOption;
				return ewOption;
			}
		}
		return Side.NONE;
	}

/**	public Side collision(Entity e) {
		double ew = intersect(e.getX(), e.getX() + e.getW(), x, x + w); // east west intersection
		double ns = intersect(e.getY(), e.getY() + e.getH(), y, y + h); // north south interaction
		if (!(e.getC() && collidable)) {
			if (ns + ew > 0)
				return Side.GENERAL;
			return Side.NONE;
		}
		Side nsOption = Side.NORTH;
		Side ewOption = Side.WEST;
		if (e.getMidX() > getMidX())
			ewOption = Side.EAST;
		if (e.getMidY() > getMidY())
			nsOption = Side.SOUTH;
		
		if(Math.abs(e.getMidY() - getMidY()) - (e.getH() + h) / 2 <= 0 && Math.abs(e.getMidX() - getMidX()) - (e.getW() + w) / 2 <= 0){
			if(x > e.getX() && x < e.getX() + e.getW()) return nsOption;
			return ewOption;
		}
		return Side.NONE;
	}
**/
	
	public int getMidX() {
		return x + w / 2;
	}

	public int getMidY() {
		return y + h / 2;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
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
	
	public void keyPressed(KeyEvent e) {

    }
    
    public void keyReleased(KeyEvent e) {
    	
    }
    
    public void keyTyped(KeyEvent e) {
    
    }


}