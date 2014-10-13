import java.awt.image.BufferedImage;


public class Item extends Entity{
	protected String name;
	
	public Item(BufferedImage b, double x, double y, double w, double h, boolean c, String name){
		super(b, x, y, w, h, c);
		this.name = name;
	}
	
	
	
}
