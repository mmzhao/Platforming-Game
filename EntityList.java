import java.awt.Rectangle;
import java.util.ArrayList;

//class that holds list of all entities besides the player and projectiles
public class EntityList {
	
//	e: arraylist of all entities on the map
//	p: arraylist of all platforms on the map
//	b: arraylist of all baddies on the map
	private ArrayList<Entity> e;
	private ArrayList<Platform> p;
	private ArrayList<Baddie> b;

// --------------------------------CONSTRUCTOR-------------------------------- //
	
	public EntityList(){
		e = new ArrayList<Entity>();
		p = new ArrayList<Platform>();
		b = new ArrayList<Baddie>();
	}
	
// --------------------------------UPDATE METHODS-------------------------------- //
	
	//removes entities that need to be removed
	public void update(){
		for (int i = e.size() - 1; i >= 0; i--) {
			if (e.get(i).needRemoval()){
				e.remove(i);
			}
		}
		for (int i = p.size() - 1; i >= 0; i--) {
			if (p.get(i).needRemoval()){
				p.remove(i);
			}
		}
		for (int i = b.size() - 1; i >= 0; i--) {
			if (b.get(i).needRemoval()){
				b.remove(i);
			}
		}
	}
	
// --------------------------------LIST CREATING METHODS-------------------------------- //
	
	public void addEntity(Entity e){
		this.e.add(e);
		if(e instanceof Platform){
			p.add((Platform) e);
		}
		else if(e instanceof Baddie){
			b.add((Baddie) e);
		}
	}
	
	public void addEntity(ArrayList<Entity> l){
		this.e.addAll(l);
		for(int i = 0; i < l.size(); i++){
			Entity ent = e.get(i);
			if(ent instanceof Platform){
				p.add((Platform) ent);
			}
			else if(ent instanceof Baddie){
				b.add((Baddie) ent);
			}
		}
	}
	
	public void addPlatform(Platform p){
		e.add(p);
		this.p.add(p);
	}
	
	public void addPlatform(ArrayList<Platform> l){
		e.addAll(l);
		this.p.addAll(l);
	}
	
	public void addBaddie(Baddie b){
		e.add(b);
		this.b.add(b);
	}
	
	public void addBaddie(ArrayList<Baddie> l){
		e.addAll(l);
		this.b.addAll(l);
	}
	
// --------------------------------GET LIST METHODS-------------------------------- //
	
	public ArrayList<Entity> getEntities(){
		ArrayList<Entity> l = new ArrayList<Entity>();
		for(int i = 0; i < e.size(); i++){
			if(e.get(i).isActivated()){
				l.add(e.get(i));
			}
		}
		return l;
	}
	
	public ArrayList<Platform> getPlatforms(){
		ArrayList<Platform> l = new ArrayList<Platform>();
		for(int i = 0; i < p.size(); i++){
			if(p.get(i).isActivated()){
				l.add(p.get(i));
			}
		}
		return l;
	}
	
	public ArrayList<Baddie> getBaddies(){
		ArrayList<Baddie> l = new ArrayList<Baddie>();
		for(int i = 0; i < b.size(); i++){
			if(b.get(i).isActivated()){
				l.add(b.get(i));
			}
		}
		return l;
	}
	
	//GET LIST METHODS THAT ONLY GIVE ENTITES ON THE CURRENT SCREEN AND ACTIVATE ALL THAT ARE ON SCREEN
	
	public ArrayList<Entity> getEntities(Rectangle screen){
		ArrayList<Entity> l = new ArrayList<Entity>();
		for(int i = 0; i < e.size(); i++){
			if(screen.intersects(e.get(i).makeRectExtra())){
				e.get(i).activate();
				l.add(e.get(i));
			}
		}
		return l;
	}
	
	public ArrayList<Platform> getPlatforms(Rectangle screen){
		ArrayList<Platform> l = new ArrayList<Platform>();
		for(int i = 0; i < p.size(); i++){
			if(screen.intersects(p.get(i).makeRectExtra())){
				e.get(i).activate();
				l.add(p.get(i));
			}
		}
		return l;
	}
	
	public ArrayList<Baddie> getBaddies(Rectangle screen){
		ArrayList<Baddie> l = new ArrayList<Baddie>();
		for(int i = 0; i < b.size(); i++){
			if(screen.intersects(b.get(i).makeRect())){
				e.get(i).activate();
				l.add(b.get(i));
			}
		}
		return l;
	}
	
	
	
}
