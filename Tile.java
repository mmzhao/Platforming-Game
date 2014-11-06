import java.awt.Rectangle;
import java.util.ArrayList;

//class that holds list of all entities besides the player and projectiles
public class Tile {
	
	private int x;
	private int y;
	private int w;
	private int h;
	
//	e: arraylist of all entities on the map
//	p: arraylist of all platforms on the map
//	b: arraylist of all baddies on the map
	private ArrayList<Entity> e;
	private ArrayList<Platform> p;
	private ArrayList<Baddie> b;

// --------------------------------CONSTRUCTOR-------------------------------- //
	
	public Tile(){
		this(0, 0, 0, 0);
	}
	
	public Tile(int x, int y, int w, int h){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		
		e = new ArrayList<Entity>();
		p = new ArrayList<Platform>();
		b = new ArrayList<Baddie>();
	}
	
// --------------------------------UPDATE METHODS-------------------------------- //
	
	//removes entities that need to be removed
	public void update(){
		ArrayList<Entity> moveOut = new ArrayList<Entity>();
		for (int i = e.size() - 1; i >= 0; i--) {
			if (e.get(i).needRemoval()){
				e.remove(i);
			}
			else if(!e.get(i).makeRect().intersects(x, y, w, h)){
				e.remove(e.get(i));
			}
		}
		for (int i = p.size() - 1; i >= 0; i--) {
			if (p.get(i).needRemoval()){
				p.remove(i);
			}
			else if(!p.get(i).makeRect().intersects(x, y, w, h)){
				p.remove(p.get(i));
			}
		}
		for (int i = b.size() - 1; i >= 0; i--) {
			if (b.get(i).needRemoval()){
				b.remove(i);
			}
			else if(b.get(i).makeRect().intersects(x, y, w, h)){
				b.remove(b.get(i));
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
	
	
	
}
