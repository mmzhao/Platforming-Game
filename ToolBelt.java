import java.util.ArrayList;


public class ToolBelt {
	private static Weapon[] toolBelt;
	private static int current;

	public ToolBelt(){
		toolBelt = new Weapon[5];
		current = 0;
	}
	
	public static int getCurrent(){
		return current;
	}
	
	public static void addWeapon(Weapon i, int space){
		toolBelt[space] = i;
	}
	
	public static Weapon replaceWeapon(Weapon i, int space){
		Weapon temp = toolBelt[space];
		toolBelt[space] = i;
		return temp;
	}
	
	public static Weapon replaceWeapon(int space){
		Weapon temp = toolBelt[space];
		toolBelt[space] = null;
		return temp;
	}
	
}
