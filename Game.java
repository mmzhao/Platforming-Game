import javax.swing.JFrame;

// This is the main entry point..
public class Game extends JFrame {
	
// --------------------------------CONTRUCTOR-------------------------------- //

	public Game() {	
		int entireW = 1000;
		int entireH = 400;
		int w = 420;
		int h = 330;
		int screenW = 420 * 2;
		int screenH = 330 * 2;
//		int screenW = 1153;
//		int screenH = 720;
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
