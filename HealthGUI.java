import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class HealthGUI {
	private BufferedImage gui;
	private BufferedImage bar;
	
	private int healthPercent;
	private int x;
	private int y;
	private int w;
	private int h;
	
	private double ratio = .326;
	
	private double scaleConstant;
	
	public HealthGUI(int x, int y, int w){
		healthPercent = 100;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = (int)(w * ratio);
//		gui = ImageGetter.getSVG("HealthGUI.svg", w, h);
//		bar = ImageGetter.getSVG("HealthBar.svg", w, h);
//		System.out.println(w + " " + h);
		gui = GamePanel.getMap().getDict("HealthGUI");
		bar = GamePanel.getMap().getDict("HealthBar");
	}
	
	public void draw(Graphics g, int offsetX, int offsetY, double scaleX, double scaleY){
		g.drawImage(bar, x, y, (int)(w * healthPercent/100), h, x, y, w, h, null);
		g.drawImage(gui, x, y, w, h, null);
	}
	
	public void setHealthPercent(int health){
		healthPercent = health;
	}
}
