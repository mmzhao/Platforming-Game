import java.awt.Rectangle;
import java.util.ArrayList;

//should not use rectangles to check intersection for greater efficiency

public class CollisionHandler {

// --------------------------------CONSTRUCTOR-------------------------------- //
	
	public CollisionHandler() {

	}

// --------------------------------PLAYER/ATTACK COLLISION-------------------------------- //

	//manages all player collision and weapon collision with all entities
	public void playerCollision(Player p, Rectangle screen) {
		p.saveCurrentState();
		p.resetCollisionState();
		p.updateC();
		for (Entity e : GamePanel.getEL().getEntities()) {
			if (e instanceof Baddie) {
				if (e.makeRect().intersects(p.makeRect())) {
					if (!p.isHit()) {
						p.takeDamage(1); //change eventually to how much damage the baddie actually does
						p.setIsHit(true);
						p.setHitTimer(System.currentTimeMillis());
					}
				}
			} else if (e instanceof Platform) {
				
				
				if (e.makeRect().intersects(p.makeRect())) {
					double distx = Math.abs(p.getSave().getMidX() - e.getMidX())
							- p.getSave().getW() / 2 - e.getW() / 2;
					double disty = Math.abs(p.getSave().getMidY() - e.getMidY())
							- p.getSave().getH() / 2 - e.getH() / 2;
					if(disty < 0 && -disty < Map.UNIT){
						p.getSave().setY(p.getSave().getY() + disty + .5); //add the .5 to make it so he actually collides with platform and doesn't jiggle
						p.setY(p.getY() + disty);
					}
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
						if (distx / p.getSave().getV().getCX() < 0
								|| disty / p.getSave().getV().getCY() < 0)
						if (distx / p.getSave().getV().getCX() < disty / p.getSave().getV().getCY()) {
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
		if(p.getCurrentWeapon() != null && p.getCurrentWeapon() instanceof RangedWeapon){ //fix this crap code
			projectileCollision(((RangedWeapon)(p.getCurrentWeapon())).getProjectiles(), screen);
			emptyShellCollision(((RangedWeapon)(p.getCurrentWeapon())).getEmptyShells(), screen);
		}
	}
	
	public void projectileCollision(ArrayList<Projectile> ps, Rectangle screen){
		for(Projectile p: ps){
//			p.saveCurrentState();
//			p.resetCollisionState();
//			p.updateC();
			for (Entity e : GamePanel.getEL().getEntities()) {
				if (e.makeRect().intersects(p.makeRect())){
					p.onHit();
					if(e instanceof Baddie) ((Baddie) e).takeDamage(p.getDamage());
				}
			}
//			if(!p.makeRect().intersects(screen)){
//				p.kill();
//			}
//			p.reset();
		}
	}
	
	public void emptyShellCollision(ArrayList<EmptyShell> ess, Rectangle screen){
		for(EmptyShell es: ess){
			es.saveCurrentState();
			es.resetCollisionState();
			es.updateC();
			for (Platform e : GamePanel.getEL().getPlatforms()) {
//				if(e.makeRect().contains(es.getSave().makeRect())){
//					es.kill();
//					return;
//				}
				if (e.makeRect().intersects(es.makeRect())) {
					if(es.getBounce()){
						es.kill();
					}
					double distx = Math.abs(es.getSave().getMidX() - e.getMidX())
							- es.getSave().getW() / 2 - e.getW() / 2;
					double disty = Math.abs(es.getSave().getMidY() - e.getMidY())
							- es.getSave().getH() / 2 - e.getH() / 2;
					if (distx < 0 && disty < 0) {
						if (distx > disty) {
							distx = 0;
						} 
						else
							disty = 0;
					}
					if (distx < 0) {
						if (es.getSave().getMidY() > e.getMidY()) {
							es.setNorthC(true);
						} 
						else {
							es.setSouthC(true);
						}
					} 
					else if (disty < 0) {
						if (es.getSave().getMidX() > e.getMidX()) {
							es.setWestC(true);
						} 
						else {
							es.setEastC(true);
						}
					} 
					else {
						if (distx / es.getSave().getV().getCX() < 0 || disty / es.getSave().getV().getCY() < 0)
						if (distx / es.getSave().getV().getCX() < disty / es.getSave().getV().getCY()) {
							if (es.getSave().getMidY() > e.getMidY()) {
								es.setNorthC(true);
							} 
							else {
								es.setSouthC(true);
							}
						} 
//						else {
							if (es.getSave().getMidX() > e.getMidX()) {
								es.setWestC(true);
							} else {
								es.setEastC(true);
							}
//						}
					}
				}
			}
			if(!es.makeRect().intersects(screen)){
				es.kill();
			}
			es.reset();
		}
	}
	
// ----------------------------COLLISION MANAGEMENT FOR ALL OTHER ENTITIES---------------------------- //
	
	public void entityCollision(Rectangle screen){
		for(Baddie b: GamePanel.getEL().getBaddies()){
			entityCollision(b, screen);
		}
	}
	
	public void entityCollision(Baddie b, Rectangle screen){
		b.saveCurrentState();
		b.resetCollisionState();
		b.updateC();
		for (Entity e : GamePanel.getEL().getEntities()) {
			if (e instanceof Platform) {
				if (e.makeRect().intersects(b.makeRect())) {
					double distx = Math.abs(b.getSave().getMidX() - e.getMidX())
							- b.getSave().getW() / 2 - e.getW() / 2;
					double disty = Math.abs(b.getSave().getMidY() - e.getMidY())
							- b.getSave().getH() / 2 - e.getH() / 2;
					if(disty < 0 && -disty < Map.UNIT){
						b.getSave().setY(b.getSave().getY() + disty + .5); // add .5 for the same reason as before
						b.setY(b.getY() + disty);
					}
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
						if (distx / b.getSave().getV().getCX() < 0
								|| disty / b.getSave().getV().getCY() < 0)
						if (distx / b.getSave().getV().getCX() < disty / b.getSave().getV().getCY()) {
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
