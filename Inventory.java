import java.util.ArrayList;


public class Inventory {
	
	private static ArrayList<Item> inventory;
	private static int maxCapacity;
	
	public Inventory(int maxCapacity){
		inventory = new ArrayList<Item>();
		this.maxCapacity = maxCapacity;
	}
	
	
	
}
