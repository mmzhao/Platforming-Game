import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class EntityListener implements KeyListener, MouseListener, MouseMotionListener{

//	entity: entity that is listened to, should be the player
	private Entity entity;
	
// --------------------------------CONTRUCTOR-------------------------------- //
	
	public EntityListener(Entity entity) {
		this.entity = entity;
	}
	
// --------------------------------KEY LISTENING METHODS-------------------------------- //
	
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

// --------------------------------MOUSE LISTENER METHODS-------------------------------- //
    
	public void mouseClicked(MouseEvent e) {
//		entity.mouseClicked(e);
	}

	public void mousePressed(MouseEvent e) {
		entity.mousePressed(e);
	}

	public void mouseReleased(MouseEvent e) {
		entity.mouseReleased(e);
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}

	public void mouseDragged(MouseEvent e) {
		entity.mouseDragged(e);
	}

	public void mouseMoved(MouseEvent e) {
		entity.mouseMoved(e);
		
	} 

}