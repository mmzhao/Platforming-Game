
public class Explosions {
	
//	EXPLOSTIONS: list of possible explosion animation sprite maps
	private static final Animation[] EXPLOSIONS = {new Animation("Explosion1.png", 5, 4, 1), new Animation("Explosion3.png", 5, 4, 1)};
	
	//returns a random explosion sprite map from EXPLOSIONS
	public static Animation getRandom(){
		return EXPLOSIONS[(int)(Math.random() * EXPLOSIONS.length)];
	}
}
