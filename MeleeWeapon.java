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
	protected MeleeAttack currAttack;
	protected long startTime;
	protected int cycle;
	
	protected AffineTransform  at;
	
	protected double pivotX = 0;
	protected double pivotY = 0;
	
	protected final long LIFETIME = 200;
	protected final double minChange = 1;

	public MeleeWeapon(String name, BufferedImage b, Movable owner, int dmg) {
//		super(b, 0, 0, 0, 0, false, name);
		super(b, name);
		this.owner = owner;
		damage = dmg;
		facingRight = 1;
		initializeMas();
		currAttack = null;
		w = 50;
		h = 5;
		cycle = 0;
		
		at = new AffineTransform();
        at.translate(x, y);
	}
	
	public void draw(Graphics g, int offsetX, int offsetY, double scaleX, double scaleY){
		double finalX = owner.getMouseX() - owner.getMidX();
		double finalY = owner.getMouseY() - owner.getMidY() + 8;
		double length = (new Vector(finalX, finalY)).magnitude();
		finalX *= 50 / length;
		finalY *= 50 / length;
		finalX += owner.getMidX();
		finalY += owner.getMidY() - 8;
//		System.out.println(finalX + " " + finalY);
		g.drawLine((int)((owner.getMidX() - offsetX) * scaleX), (int)((owner.getMidY() - 8 - offsetY) * scaleY), (int)((finalX - offsetX) * scaleX), (int)((finalY - offsetY) * scaleY));

//		mas.get(0).draw(g, offsetX, offsetY, scaleX, scaleY);
		
		if(currAttack != null){
			currAttack.draw(g, offsetX, offsetY, scaleX, scaleY);
		}
			
	}
	
	public void update(double time){
		if(currAttack != null){
			if(System.currentTimeMillis() > startTime + LIFETIME){
				cycle = 0;
				currAttack = null;
			}
		}
		updateFireVector();
		facingRight = owner.getFacingRight();
		if(owner != null){
			double newX = owner.getMidX();
			double newY = owner.getMidY() - 8; //fix this magic number
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
	
	public void fire() {
		attack();
	}
	
	public void attack(){
		startTime = System.currentTimeMillis();
		cycle %= mas.size();
		currAttack = mas.get(cycle);
		currAttack.attack(owner.getMidX(), owner.getMidY(), getAngle(facingRight));
		cycle++;
	}
	
	public void updateFireVector(){
		double difX = owner.getMouseX() - x;
		double difY = owner.getMouseY() - y;
		double magnitude = Math.pow(difX * difX +  difY * difY, .5);
		fireX = difX / magnitude;
		fireY = difY / magnitude;
	}
	
//	public void initializeMas(){
//		mas = new ArrayList<MeleeAttack>();
//		
//		Path p1 = new Path(20, 20);
//		p1.add(new Vector(50, -20));
//		p1.add(new Vector(-50, -20));
//		mas.add(new MeleeAttack(owner, damage, p1));
//		
//		Path p2 = new Path(20, 40);
//		p2.add(new Vector(50, -40));
//		p2.add(new Vector(-50, -40));
//		mas.add(new MeleeAttack(owner, damage, p2));
//	}
	
	public void initializeMas(){
		mas = new ArrayList<MeleeAttack>();
		
		for(int i = 0; i <= 30; i+=2){
			Path p1 = new Path(20, (10 + i));
			p1.add(new Vector(50, -(10 + i)));
			p1.add(new Vector(-50, -(10 + i)));
			mas.add(new MeleeAttack(owner, damage, p1));
		}
		for(int i = 29; i >= 1; i-=2){
			Path p1 = new Path(20, (10 + i));
			p1.add(new Vector(50, -(10 + i)));
			p1.add(new Vector(-50, -(10 + i)));
			mas.add(new MeleeAttack(owner, damage, p1));
		}
		
	}
	
	public ArrayList<Projectile> getProjectiles() {
		return new ArrayList<Projectile>();
	}
	
	public ArrayList<EmptyShell> getEmptyShells() {
		return new ArrayList<EmptyShell>();
	}
	
	
}
