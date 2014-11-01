import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class MeleeWeapon extends Weapon{
	protected Movable owner;
	protected int damage;
	protected int facingRight;
	protected ArrayList<MeleeAttack> mas;
	protected ArrayList<MeleeAttack> currAttacks;
	protected long startTime;
	protected int cycle;
	
	protected double pivotX = 0;
	protected double pivotY = 0;
	
	protected final long LIFETIME = 400;
	protected final long COOLDOWN = 300;
	protected final double minChange = 1;

	public MeleeWeapon(String name, BufferedImage b, Movable owner, int dmg) {
//		super(b, 0, 0, 0, 0, false, name);
		super(b, name);
		this.owner = owner;
		damage = dmg;
		facingRight = 1;
		initializeMas();
		currAttacks = new ArrayList<MeleeAttack>();
		w = 50;
		h = 5;
		cycle = 0;
		
		at = new AffineTransform();
        at.translate(x, y);
	}
	
	public void draw(Graphics g, int offsetX, int offsetY, double scaleX, double scaleY){
		double finalX = owner.getMouseX() - owner.getMidX();
		double finalY = owner.getMouseY() - owner.getMidY();
		double length = (new Vector(finalX, finalY)).magnitude();
		finalX *= 50 / length;
		finalY *= 50 / length;
		finalX += owner.getMidX();
		finalY += owner.getMidY();
//		System.out.println(finalX + " " + finalY);
		g.drawLine((int)((owner.getMidX() - offsetX) * scaleX), (int)((owner.getMidY() - offsetY) * scaleY), (int)((finalX - offsetX) * scaleX), (int)((finalY - offsetY) * scaleY));

//		mas.get(0).draw(g, offsetX, offsetY, scaleX, scaleY);
		
//		System.out.println(currAttacks.size());
		
		if(currAttacks.size() != 0){
			for(MeleeAttack m: currAttacks){
				m.draw(g, offsetX, offsetY, scaleX, scaleY);
			}
		}
			
	}
	
	public void update(double time){
		if(System.currentTimeMillis() > startTime + LIFETIME){
			cycle = 0;
			currAttacks.clear();;
		}
		
		for(int i = currAttacks.size() - 1; i >= 0; i--){
			currAttacks.get(i).update(time);
			if(currAttacks.get(i).needRemoval()){
				currAttacks.remove(i);
			}
		}
		
		updateFireVector();
		facingRight = owner.getFacingRight();
		if(owner != null){
			double newX = owner.getMidX();
			double newY = owner.getMidY();
			if(Math.abs(newX - x) > Math.abs(minChange)){
				x = newX;
			}
			if(Math.abs(newY - y) > Math.abs(minChange)){
				y = newY;
			}
			
		}
		else{
			x = 0;
			y = 0;
		}
	}
	
	public void attack(){
		if(System.currentTimeMillis() > startTime + COOLDOWN){
			startTime = System.currentTimeMillis();
			cycle %= mas.size();
			currAttacks.add(mas.get(cycle).getRotatedVersion(owner.getMidX(), owner.getMidY(), getAngle(), facingRight));
			currAttacks.get(currAttacks.size() - 1).attack();
			cycle++;
		}
	}
	
	public void updateFireVector(){
		double difX = owner.getMouseX() - x;
		double difY = owner.getMouseY() - y;
		double magnitude = Math.pow(difX * difX +  difY * difY, .5);
		fireX = difX / magnitude;
		fireY = difY / magnitude;
	}
	
	public void initializeMas(){
		mas = new ArrayList<MeleeAttack>();
		
		Path p1 = new Path(26, 63);
		p1.add(new Vector(8, -5));
		p1.add(new Vector(12, -20));
		p1.add(new Vector(4, -20));
		p1.add(new Vector(0, -20));
		p1.add(new Vector(-4, -8));
		p1.add(new Vector(-8, -20));
		p1.add(new Vector(-16, -20));
		p1.add(new Vector(-25, -16));
		p1.add(new Vector(-32, -1));
		mas.add(new MeleeAttack(this, damage, p1, LIFETIME));
		
//		Path p2 = new Path(20, 40);
//		p2.add(new Vector(50, -40));
//		p2.add(new Vector(-50, -40));
//		mas.add(new MeleeAttack(owner, this, damage, p2, LIFETIME));
	}
	
//	public void initializeMas(){
//		mas = new ArrayList<MeleeAttack>();
//		
//		for(int i = 0; i <= 30; i+=2){
//			Path p1 = new Path(20, (10 + i));
//			p1.add(new Vector(50, -(10 + i)));
//			p1.add(new Vector(-50, -(10 + i)));
//			mas.add(new MeleeAttack(owner, this, damage, p1, LIFETIME));
//		}
//		for(int i = 29; i >= 1; i-=2){
//			Path p1 = new Path(20, (10 + i));
//			p1.add(new Vector(50, -(10 + i)));
//			p1.add(new Vector(-50, -(10 + i)));
//			mas.add(new MeleeAttack(owner, this, damage, p1, LIFETIME));
//		}
//		
//	}
	
	public ArrayList<Projectile> getProjectiles() {
		return new ArrayList<Projectile>();
	}
	
	public ArrayList<EmptyShell> getEmptyShells() {
		return new ArrayList<EmptyShell>();
	}

	public int getFacingRight() {
		return facingRight;
	}
	
	
}
