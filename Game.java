import javax.swing.JFrame;

// This is the main entry point..
public class Game extends JFrame {
	
// --------------------------------CONTRUCTOR-------------------------------- //

	public Game() {	 //old 420x330
		int entireW = 1000;
		int entireH = 400;
		int w = 592;
		int h = 333;
		int screenW = 592 * 2;
		int screenH = 333 * 2;
		setSize(screenW, screenH);
		setResizable(false);
		setTitle("Game Frame"); 
		GamePanel panel = new GamePanel(entireW, entireH, w, h, screenW, screenH);
		add(panel);
		panel.setDoubleBuffered(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		pack();
	}
	
// --------------------------------MAIN METHOD-------------------------------- //
	
	public static void main(String[] args) {
		new Game();
	}

}
