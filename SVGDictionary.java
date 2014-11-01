import java.awt.image.BufferedImage;
import java.util.HashMap;


public class SVGDictionary {
	public HashMap<String, BufferedImage> dict;
	
	public SVGDictionary(){
		dict = new HashMap<String, BufferedImage>();
	}
	
	public void add(String name, BufferedImage bi){
		dict.put(name, bi);
	}
	
}
