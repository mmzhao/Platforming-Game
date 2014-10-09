import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Movable {
	protected boolean isRightHeld = false;
	protected boolean isLeftHeld = false;
	protected boolean isUpHeld = false;
	protected boolean isHit = false;
	protected boolean isShooting = false;
	protected long hitTimer = 0;
	protected Player save;

	protected ArrayList<Projectile> ps;

	protected boolean facingRight = true; // starts by facing right

	private final double AIR_RESISTANCE = .5;
	private final double MIN_FOR_DOUBLE_COLLISION = 6;

	// >5 for normal
	// 5 for slight spiderman
	// <5 for very spiderman
	// 2 for optimal spiderman
	// 1 for retard spiderman

	public Player(BufferedImage b, double x, double y, double w, double h) {
		super(b, x, y, w, h, true, 0, 0);
		save = null;
		ps = new ArrayList<Projectile>();
	}

	public Player(BufferedImage b, double x, double y, double w, double h,
			double xv, double yv) {
		super(b, x, y, w, h, true, xv, yv);
	}

	public void sidesCollided(ArrayList<Entity> es) {
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

	public void update(double time) {
		if (!(isRightHeld && isLeftHeld)) {
			if (isRightHeld) {
				xv = 5;
			} else if (isLeftHeld) {
				xv = -5;
			}
		}
		// if(isUpHeld){
		// if(southC) yv = -10;
		// }
		if (southC) {
			if (yv > 0)
				yv = 0;
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
			shoot();
		// update projectile list
		updateProjectiles();
	}

/**	public void saveCurrentState() {
		save = new Player(bi, x, y, h, w, xv, yv);
	}

	public void updateC() {
		updateC(TIME_UNIT);
	}
**/
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

/**	public void reset() {
		x = save.getX();
		y = save.getY();
		w = save.getW();
		h = save.getH();
		xv = save.getXV();
		yv = save.getYV();
	}
**/
	public void shoot() {
		double x = this.x - 3;
		double v = -5;
		double a = -1;
		if (facingRight) {
			x += this.w + 3;
			v *= -1;
			a *= -1;
		}
		Projectile p = new Projectile(null, x, getMidY() - 3, 3, 3, v, a);

		ps.add(p);
		if (ps.size() > 10)
			ps.remove(0);
	}

	public ArrayList<Projectile> getProjectiles() {
		return ps;
	}

	public void setProjectile(ArrayList<Projectile> ps) {
		this.ps = ps;
	}

	public void removeProjectile(Projectile p) {
		ps.remove(p);
	}

	public void updateProjectiles() {
		// projectile list update
		boolean[] delete = new boolean[ps.size()];
		// System.out.println(ps.size());
		for (int i = 0; i < ps.size(); i++) {
			if (ps.get(i).needRemoval()) {
				ps.remove(i);
				i--;
			}
		}
	}

	public void draw(Graphics g) {
		long currentTime = System.currentTimeMillis();
		if (currentTime - hitTimer < 100) {
			g.setColor(Color.pink);
		} else
			g.setColor(Color.black);

		g.fillOval((int) x, (int) y, (int) w, (int) h);

		g.setColor(Color.blue);

		// making dot to designate facing direction
		if (facingRight) {
			g.fillOval((int) (x + w), (int) getMidY() - 3, 3, 3);
		} else
			g.fillOval((int) x - 3, (int) getMidY() - 3, 3, 3);
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			isLeftHeld = true;
			setXV(-5);
			facingRight = false;
		}

		else if (key == KeyEvent.VK_RIGHT) {
			isRightHeld = true;
			setXV(5);
			facingRight = true;
		}

		else if (key == KeyEvent.VK_UP) {
			// isUpHeld = true;
			if (southC)
				setYV(-10);
		}

		else if (key == KeyEvent.VK_A) {
			isLeftHeld = true;
			setXV(-5);
			facingRight = false;
		}

		else if (key == KeyEvent.VK_D) {
			isRightHeld = true;
			setXV(5);
			facingRight = true;
		}

		else if (key == KeyEvent.VK_W) {
			isUpHeld = true;
			if (southC)
				setYV(-10);
		}

		else if (key == KeyEvent.VK_SPACE) {
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