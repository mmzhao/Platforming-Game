import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;


public class WeaponGUI {
	
	private BufferedImage[] icons;
	private BufferedImage gui;
	private BufferedImage background;
	private int selected;
	private BufferedImage[] selection;
	private HashMap<String, BufferedImage> m;
	private int numWeapons;
	
	private int leftCircleX;
	private int leftCircleY;
	
	private int currClip;
	private int currAmmo;
	
	private int x;
	private int y;
	private int w;
	private int h;
	private double scaleConstant;
	
	private final String[] WEAPONS = {"Revolver", "RocketLauncher", "MolotovCocktail", "Rifle", "Shotgun"};
	private int[] xcoordinates = {24, 330, 636, 942, 1248};
	private int[] ycoordinates = {180, 180, 180, 180, 180};
	private int length = 286;
	
	public WeaponGUI(int x, int y, int w, int h){
		currClip = -1;
		currAmmo = -1;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		scaleConstant = w/2225.3;
		selected = 0;
		numWeapons = 0;
		icons = new BufferedImage[5];
		m = new HashMap<String, BufferedImage>();
		for(int i = 0; i < icons.length; i++){
			xcoordinates[i] = (int)(xcoordinates[i] * scaleConstant);
			ycoordinates[i] = (int)(ycoordinates[i] * scaleConstant);	
		}
		length = (int)(length * scaleConstant);
		for(int i = 0; i < WEAPONS.length; i ++){
//			System.out.println(WEAPONS[i] + "Icon.svg");
//			m.put(WEAPONS[i], ImageGetter.getSVG(WEAPONS[i] + "Icon.svg", length, length));
//			System.out.println(WEAPONS[i]+"Icon");
			m.put(WEAPONS[i], GamePanel.getMap().getDict(WEAPONS[i] + "Icon"));
		}
		
		
		leftCircleX = (int)(1735*scaleConstant + x);
		leftCircleY = (int)(302*scaleConstant + y);

		
		gui = GamePanel.getMap().getDict("WeaponGUI");
		background = GamePanel.getMap().getDict("WeaponGUIBackground");
	}
	
	public void draw(Graphics g, int offsetX, int offsetY, double scaleX, double scaleY){
		g.drawImage(background, x, y, w, h, null);
		g.setColor(Color.white);
		g.fillRect(xcoordinates[selected] + x, ycoordinates[selected] + y , length + (int)(24*scaleConstant), length + (int)(24*scaleConstant));
		g.drawImage(gui, x, y, w, h, null);
		
		g.setColor(Color.black);
		if(currClip >= 0){
			if(currClip < 10){
				g.drawString(" " + currClip, leftCircleX, leftCircleY);
			}
			else{
				g.drawString("" + currClip, leftCircleX, leftCircleY);
			}
		}
		else{
			//Implement Melee Weapon
		}
		
		for(int i = 0; i < icons.length; i++){
			if(icons[i] != null){
				g.drawImage(icons[i], xcoordinates[i] + x, ycoordinates[i] + y, length, length, null);
			}
		}
	}
	
	public void setAmmo(int ammo){
		currAmmo = ammo;
	}
	
	public void setClip(int clip){
		currClip = clip;
	}
	
	public void addWeapon(String name, int position){
		icons[position] = m.get(name);
		numWeapons++;
	}
	
	public void select(int position){
		selected = position;
	}
}
