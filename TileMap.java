import java.util.ArrayList;


public class TileMap {
	private Tile[][] ta;
	private Tile t;
	private int x;
	private int y;
	private int w;
	private int h;
	
	public TileMap(int x, int y, int w, int h){
		ta = new Tile[x][y];
		this.x = x;
		this.y = y;
		this.w = w;
		this.y = y;
	}
	
	public void setTile(Tile t){
		ta[t.getX()/w][t.getY()/h] = t;
	}
	
	public Tile getTile(int x, int y){
		return ta[x][y];
	}
	
	public ArrayList<Tile> getTilesInSpace(double x, double y, double w, double h){
		ArrayList<Tile> al = new ArrayList<Tile>();
		for(int i = (int) Math.floor((double)(x/this.w)); i < (int) Math.ceil((x + w)/this.w); i++){
			for(int j = (int) Math.floor((double)(y/this.h)); j < (int) Math.ceil((y + h)/this.h); j++){
				al.add(ta[i][j]);
			}
		}
		return al;
	}
	
	public ArrayList<Entity> getEntitesInSpace(double x, double y, double w, double h){
		this.t = new Tile();
		for(int i = (int) Math.floor((double)(x/this.w)); i < (int) Math.ceil((x + w)/this.w); i++){
			for(int j = (int) Math.floor((double)(y/this.h)); j < (int) Math.ceil((y + h)/this.h); j++){
				t.addEntity(ta[i][j].getEntities());
			}
		}
		return t.getEntities();
	}
	
	public ArrayList<Platform> getPlatformsInSpace(double x, double y, double w, double h){
		this.t = new Tile();
		for(int i = (int) Math.floor((double)(x/this.w)); i < (int) Math.ceil((x + w)/this.w); i++){
			for(int j = (int) Math.floor((double)(y/this.h)); j < (int) Math.ceil((y + h)/this.h); j++){
				t.addPlatform(ta[i][j].getPlatforms());
			}
		}
		return t.getPlatforms();
	}
	
	public ArrayList<Baddie> getBaddiesInSpace(double x, double y, double w, double h){
		this.t = new Tile();
		for(int i = (int) Math.floor((double)(x/this.w)); i < (int) Math.ceil((x + w)/this.w); i++){
			for(int j = (int) Math.floor((double)(y/this.h)); j < (int) Math.ceil((y + h)/this.h); j++){
				t.addBaddie(ta[i][j].getBaddies());
			}
		}
		return t.getBaddies();
	}
	

	
}
