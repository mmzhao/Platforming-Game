import java.awt.Rectangle;
import java.util.ArrayList;

public class CollisionHandler {
	//should not use rectangles to check intersection for greater efficiency
	public CollisionHandler() {

	}

	public void playerCollision(Player p, EntityList el, Rectangle screen) {
		p.saveCurrentState();
		p.resetCollisionState();
		p.updateC();
		for (Entity e : el.getEntities(screen)) {
			if (e instanceof Baddie) {
				if (e.makeRect().intersects(p.makeRect())) {
					if (!p.isHit()) {
						p.setIsHit(true);;
						p.setHitTimer(System.currentTimeMillis());
					}
				}
			} else if (e instanceof Platform) {
				if (e.makeRect().intersects(p.makeRect())) {
					double distx = Math.abs(p.getSave().getMidX() - e.getMidX())
							- p.getSave().getW() / 2 - e.getW() / 2;
					double disty = Math.abs(p.getSave().getMidY() - e.getMidY())
							- p.getSave().getH() / 2 - e.getH() / 2;
					if (distx < 0 && disty < 0) {
						if (distx > disty) {
							distx = 0;
						} else
							disty = 0;
					}
					if (distx < 0) {
						if (p.getSave().getMidY() > e.getMidY()) {
							p.setNorthC(true);
						} else {
							p.setSouthC(true);
						}
					} else if (disty < 0) {
						if (p.getSave().getMidX() > e.getMidX()) {
							p.setWestC(true);
						} else {
							p.setEastC(true);
						}
					} else {
						if (distx / p.getSave().getXV() < 0
								|| disty / p.getSave().getYV() < 0)
						if (distx / p.getSave().getXV() < disty / p.getSave().getYV()) {
							if (p.getSave().getMidY() > e.getMidY()) {
								p.setNorthC(true);
							} else {
								p.setSouthC(true);
							}
						} else {
							if (p.getSave().getMidX() > e.getMidX()) {
								p.setWestC(true);
							} else {
								p.setEastC(true);
							}
						}
					}
				}
			}
		}
		p.reset();
		
		//projectiles
		if(p.getCurrentWeapon() != null){
			projectileCollision(p.getCurrentWeapon().getProjectiles(), el, screen);
		}
	}
	
	public void projectileCollision(ArrayList<Projectile> ps, EntityList el, Rectangle screen){
		for(Projectile p: ps){
//			p.saveCurrentState();
			p.resetCollisionState();
//			p.updateC();
			for (Entity e : el.getEntities(screen)) {
				if (e.makeRect().intersects(p.makeRect())){
//					System.out.println("eee");
					if(e instanceof Baddie) ((Baddie) e).takeDamage(p.getDamage());
					p.kill();
				}
			}
			if(!p.makeRect().intersects(screen)){
				p.kill();
			}
//			p.reset();
		}
	}
	
	
	public void entityCollision(EntityList el, Rectangle screen){
		for(Baddie b: el.getBaddies(screen)){
			entityCollision(b, el, screen);
		}
	}
	
	public void entityCollision(Baddie b, EntityList el, Rectangle screen){
		b.saveCurrentState();
		b.resetCollisionState();
		b.updateC();
		for (Entity e : el.getEntities(screen)) {
			if (e instanceof Platform) {
				if (e.makeRect().intersects(b.makeRect())) {
					double distx = Math.abs(b.getSave().getMidX() - e.getMidX())
							- b.getSave().getW() / 2 - e.getW() / 2;
					double disty = Math.abs(b.getSave().getMidY() - e.getMidY())
							- b.getSave().getH() / 2 - e.getH() / 2;
					if (distx < 0 && disty < 0) {
						if (distx > disty) {
							distx = 0;
						} else
							disty = 0;
					}
					if (distx < 0) {
						if (b.getSave().getMidY() > e.getMidY()) {
							b.setNorthC(true);
						} else {
							b.setSouthC(true);
						}
					} else if (disty < 0) {
						if (b.getSave().getMidX() > e.getMidX()) {
							b.setWestC(true);
						} else {
							b.setEastC(true);
						}
					} else {
						if (distx / b.getSave().getXV() < 0
								|| disty / b.getSave().getYV() < 0)
						if (distx / b.getSave().getXV() < disty / b.getSave().getYV()) {
							if (b.getSave().getMidY() > e.getMidY()) {
								b.setNorthC(true);
							} else {
								b.setSouthC(true);
							}
						} else {
							if (b.getSave().getMidX() > e.getMidX()) {
								b.setWestC(true);
							} else {
								b.setEastC(true);
							}
						}
					}
				}
			}
		}
		b.reset();
	}

}
