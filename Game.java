import javax.swing.JFrame;

// This is the main entry point..

public class Game extends JFrame {
	
	public Game() {	
		int entireW = 1000;
		int entireH = 1000;
		int w = 420;
		int h = 330;
		int screenW = 420 * 2;
		int screenH = 330 * 2;
		setSize(screenW, screenH);
		setResizable(false);
		setTitle("Game Frame"); 
		GamePanel panel = new GamePanel(entireW, entireH, w, h, screenW, screenH);
		add(panel);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);	
	}
	
	public static void main(String[] args) {
		new Game();
	}

}
