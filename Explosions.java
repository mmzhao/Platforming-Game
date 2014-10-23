
public class Explosions {
	private static final Animation[] EXPLOSIONS = {new Animation("Explosion.png", 5, 4, 1), new Animation("IceExplosion.png", 5, 6, 1), new Animation("Explosion1.png", 5, 4, 1), new Animation("Explosion2.png", 5, 5, 1), new Animation("Explosion3.png", 5, 4, 1), new Animation("DarknessExplosion.png", 5, 6, 1), new Animation("PinkExplosion.png", 5, 7, 1)};
	
	public static Animation getRandom(){
		return EXPLOSIONS[(int)(Math.random() * EXPLOSIONS.length)];
	}
}
