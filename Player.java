import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Player extends Movable {
	protected boolean isRightHeld = false;
	protected boolean isLeftHeld = false;
	protected boolean isUpHeld = false;
	protected boolean isHit = false;
	protected boolean isShooting = false;
	protected long hitTimer = 0;
//	protected Player save;

	protected Weapon currentWeapon;
	protected int standardStep;
	// starts by facing right

	private final double AIR_RESISTANCE = .5;
	private final double MIN_FOR_DOUBLE_COLLISION = 5; //if want spiderman use sidesCollided2() and collision()
	// 5 for slight spiderman
	// <5 for very spiderman
	// 2 for optimal spiderman
	// 1 for retard spiderman

	public Player(BufferedImage b, double x, double y, double w, double h, int health, Weapon currentWeapon) {
		super(b, x, y, w, h, true, 0, 0, health, 1);
		this.currentWeapon = currentWeapon;
		standardStep = 5;
	}

	public Player(BufferedImage b, double x, double y, double w, double h,
			double xv, double yv) {
		super(b, x, y, w, h, xv, yv);
		currentWeapon = null;
		standardStep = 5;
	}

/**	public void sidesCollided2(ArrayList<Entity> es) {
		saveCurrentState();
		northC = false;
		eastC = false;
		southC = false;
		westC = false;
		isHit = false;
		updateC();
		for (Entity e : es) {
			Side s = collision(e);
			if (s == Side.NORTH)
				northC = true;
			else if (s == Side.EAST)
				eastC = true;
			else if (s == Side.SOUTH)
				southC = true;
			else if (s == Side.WEST)
				westC = true;
			else if (s == Side.NORTHEAST) {
				northC = true;
				eastC = true;
			} else if (s == Side.NORTHWEST) {
				northC = true;
				westC = true;
			} else if (s == Side.SOUTHEAST) {
				southC = true;
				eastC = true;
			} else if (s == Side.SOUTHWEST) {
				southC = true;
				westC = true;
			} else if (s == Side.BADDIE) {
				// take dmg, flinch, etc
				// FIX SOMETIME WON'T HIT UNTIL JUMP
				if (!isHit) {
					isHit = true;
					hitTimer = System.currentTimeMillis();
				}
			}
		}
		reset();
	}
**/	
	public void sidesCollided(ArrayList<Entity> es) {
		saveCurrentState();
		northC = false;
		eastC = false;
		southC = false;
		westC = false;
		isHit = false;
		updateC();
		for (Entity e : es) {
			if (e instanceof Baddie) {
				Rectangle r1 = new Rectangle((int) e.getX(), (int) e.getY(),
						(int) e.getW(), (int) e.getH());
				Rectangle r2 = new Rectangle((int) x, (int) y, (int) w, (int) h);
				if (r1.intersects(r2)){
					if (!isHit) {
						isHit = true;
						hitTimer = System.currentTimeMillis();
					}
				}
			}
			else if (e instanceof Platform) {
				Rectangle r1 = new Rectangle((int) e.getX(), (int) e.getY(),
						(int) e.getW(), (int) e.getH());
				Rectangle r2 = new Rectangle((int) x, (int) y, (int) w, (int) h);
				if (r1.intersects(r2)) {
//					Rectangle inter = r1.intersection(r2);
					double distx = Math.abs(save.getMidX() - e.getMidX()) - save.getW() / 2 - e.getW() / 2;
					double disty = Math.abs(save.getMidY() - e.getMidY()) - save.getH() / 2 - e.getH() / 2;
					//System.out.println("x: " + distx + "\n" + "y: " + disty);
					if(distx < 0 && disty < 0){
//						System.out.println("plz kill yourself");
						if(distx > disty){
							distx = 0;
						}
						else
							disty = 0;
//						System.out.println("x: " + distx + "    y: " + disty);
					}
					if(distx < 0){
						if(save.getMidY() > e.getMidY()){
							northC = true;
						}
						else{
							southC = true;
						}
					}
					else if(disty < 0){
						if(save.getMidX() > e.getMidX()){
							westC = true;
						}
						else{
							eastC = true;
						}
					}
					else{
						if(distx / save.getXV() < 0 || disty / save.getYV() < 0) System.out.println("plz kill yourself");
						if(distx / save.getXV() < disty / save.getYV()){
							if(save.getMidY() > e.getMidY()){
								northC = true;
							}
							else{
								southC = true;
							}
						}
						else{
							if(save.getMidX() > e.getMidX()){
								westC = true;
							}
							else{
								eastC = true;
							}
						}
					}
				}
			}
		}
		reset();
	}
	
/**	public Side collision2(Entity e) {
		if (e instanceof Baddie) {
			Rectangle r1 = new Rectangle((int) e.getX(), (int) e.getY(),
					(int) e.getW(), (int) e.getH());
			Rectangle r2 = new Rectangle((int) x, (int) y, (int) w, (int) h);
			if (r1.intersects(r2))
				return Side.BADDIE;
			return Side.NONE;
		} 
		else if (e instanceof Platform) {
			Rectangle r1 = new Rectangle((int) e.getX(), (int) e.getY(),
					(int) e.getW(), (int) e.getH());
			Rectangle r2 = new Rectangle((int) x, (int) y, (int) w, (int) h);
			if (r1.intersects(r2)) {
				Rectangle inter = r1.intersection(r2);
				
			}
		}
		return Side.NONE;
	}

	
	public Side collision(Entity e) {
		if (e instanceof Baddie) {
			Rectangle r1 = new Rectangle((int) e.getX(), (int) e.getY(),
					(int) e.getW(), (int) e.getH());
			Rectangle r2 = new Rectangle((int) x, (int) y, (int) w, (int) h);
			if (r1.intersects(r2))
				return Side.BADDIE;
			return Side.NONE;
		} else if (e instanceof Platform) {
			Rectangle r1 = new Rectangle((int) e.getX(), (int) e.getY(),
					(int) e.getW(), (int) e.getH());
			Rectangle r2 = new Rectangle((int) x, (int) y, (int) w, (int) h);
			if (r1.intersects(r2)) {
				Rectangle inter = r1.intersection(r2);
				Side nsOption = Side.NORTH;
				Side ewOption = Side.WEST;
				Side bothOption = Side.NORTHWEST;
				if (e.getMidX() > getMidX()) {
					ewOption = Side.EAST;
					bothOption = Side.NORTHEAST;
				}
				if (e.getMidY() > getMidY()) {
					nsOption = Side.SOUTH;
					if (bothOption == Side.NORTHEAST)
						bothOption = Side.SOUTHEAST;
					else
						bothOption = Side.SOUTHWEST;
				}
				if (inter.getHeight() >= MIN_FOR_DOUBLE_COLLISION
						&& inter.getWidth() >= MIN_FOR_DOUBLE_COLLISION) {
					return bothOption;
				}
				if (inter.getHeight() <= inter.getWidth()) {
					return nsOption;
				} else
					return ewOption;
			}
		}
		return Side.NONE;
	}
**/
	public int getFacingRight(){
		return facingRight;
	}
	
	public void update(double time) {
		if (!(isRightHeld && isLeftHeld)) {
			if (isRightHeld) {
				xv = standardStep;
			} else if (isLeftHeld) {
				xv = -standardStep;
			}
		}
		if (southC) {
			if (yv > 0)
				yv = 0;
			if(isUpHeld){
				yv = -9; //if this line not commented out, can jump up platform uppersides
				//currently 1 less magnitude than "space key" jump velocity
			}
		} else
			yv += time * GRAVITY;
		if (eastC) {
			if (xv > 0)
				xv = 0;
		}
		if (westC) {
			if (xv < 0)
				xv = 0;
		}
		if (northC) {
			if (yv < 0)
				yv = 0;
		}
		if (yv > TERMINAL_VELOCITY)
			yv = TERMINAL_VELOCITY;
		x += time * xv;
		y += time * yv;
		
		if (isShooting)
			currentWeapon.fire();
		if(currentWeapon != null){
			currentWeapon.update(time);
		}
		// update projectile list
	}

//	public void saveCurrentState() {
//		save = new Player(bi, x, y, h, w, xv, yv);
//	}
		

	public void updateC(double time) {
		// System.out.println(xv + " " + yv);
		yv += time * GRAVITY;
		if (yv > TERMINAL_VELOCITY)
			yv = TERMINAL_VELOCITY;
		if (!(isRightHeld && isLeftHeld)) {
			if (isRightHeld) {
				xv = 5;
			} else if (isLeftHeld) {
				xv = -5;
			}
		}
		x += time * xv;
		y += time * yv;
	}

//	public void reset() {
//		x = save.getX();
//		y = save.getY();
//		w = save.getW();
//		h = save.getH();
//		xv = save.getXV();
//		yv = save.getYV();
//	}

	public Weapon getCurrentWeapon(){
		return currentWeapon;
	}
	
	public void giveCurrentWeapon(Weapon w){
		currentWeapon = w;
	}
	
	public void draw(Graphics g) {
		long currentTime = System.currentTimeMillis();
		if (currentTime - hitTimer < 100) {
			g.setColor(Color.pink);
		} else
			g.setColor(Color.black);
		
		// making dot to designate facing direction
		if (facingRight == 1) {
			g.drawImage(super.bi, (int)x, (int)y, (int)w, (int)h, Color.white, null);
		} else{
			g.drawImage(super.bi, (int)x + (int)w, (int)y, -(int)w, (int)h, Color.white, null);
		}
		if(currentWeapon != null){
			
			for(int i = 0; i < currentWeapon.getProjectiles().size(); i++){
				currentWeapon.getProjectiles().get(i).draw(g);
			}
			currentWeapon.draw(g);
		}
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			isLeftHeld = true;
			setXV(-5);
			facingRight = -1;
		}

		else if (key == KeyEvent.VK_RIGHT) {
			isRightHeld = true;
			setXV(5);
			facingRight = 1;
		}

		else if (key == KeyEvent.VK_UP) {
			isUpHeld = true;
			if (southC)
				setYV(-10);
		}

		else if (key == KeyEvent.VK_A) {
			isLeftHeld = true;
			setXV(-5);
			facingRight = -1;
		}

		else if (key == KeyEvent.VK_D) {
			isRightHeld = true;
			setXV(5);
			facingRight = 1;
		}

		else if (key == KeyEvent.VK_W) {
			isUpHeld = true;
			if (southC)
				setYV(-10);
		}

		else if (key == KeyEvent.VK_SPACE) {
			if(currentWeapon != null)
				isShooting = true;
//			shoot();
		}

	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			isLeftHeld = false;
			setXV(0);
		}

		else if (key == KeyEvent.VK_RIGHT) {
			isRightHeld = false;
			setXV(0);
		}

		else if (key == KeyEvent.VK_UP) {
			isUpHeld = false;
		}

		else if (key == KeyEvent.VK_A) {
			isLeftHeld = false;
			setXV(0);
		}

		else if (key == KeyEvent.VK_D) {
			isRightHeld = false;
			setXV(0);
		}

		else if (key == KeyEvent.VK_W) {
			isUpHeld = false;
		}

		else if (key == KeyEvent.VK_SPACE) {
			isShooting = false;
		}

	}

}