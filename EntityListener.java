import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EntityListener extends KeyAdapter {
	
	private Entity entity;
	
	public EntityListener(Entity entity) {
		this.entity = entity;
	}
	
	// do while key is released
    public void keyReleased(KeyEvent e) {
        entity.keyReleased(e);
    }

    // do while key is pressed down
    public void keyPressed(KeyEvent e) {
        entity.keyPressed(e);
    } 
    
    public void keyTyped(KeyEvent e) {
        entity.keyTyped(e);
    } 

}