import java.awt.image.BufferedImage;


public class Platform extends Movable{

	public Platform(BufferedImage b, int x, int y, int w, int h) {
		super(b, x, y, w, h, true, 0, 0);
	}

}
