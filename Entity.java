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

	public Entity(BufferedImage b, double x, double y, double w, double h, boolean c) {
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
		draw(g, (int) x, (int) y);
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
		if(e.getC() && collidable){
			Rectangle r1 = new Rectangle((int) e.getX(), (int) e.getY(), (int) e.getW(), (int) e.getH());
			Rectangle r2 = new Rectangle((int) x, (int) y, (int) w, (int) h);
			if(r1.intersects(r2)){
				Rectangle inter = r1.intersection(r2);
				Side nsOption = Side.NORTH;
				Side ewOption = Side.WEST;
				if (e.getMidX() > getMidX())
					ewOption = Side.EAST;
				if (e.getMidY() > getMidY())
					nsOption = Side.SOUTH;
				if(inter.getHeight() <= inter.getWidth()){
					return nsOption;
				}
				else
					return ewOption;
			}
		}
		return Side.NONE;
	}
	
	public Side collision2(Entity e) {
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
	
	public void keyPressed(KeyEvent e) {

    }
    
    public void keyReleased(KeyEvent e) {
    	
    }
    
    public void keyTyped(KeyEvent e) {
    
    }


}