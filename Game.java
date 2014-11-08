import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

import javax.swing.JFrame;

// This is the main entry point..
public class Game extends JFrame {
	
// --------------------------------CONTRUCTOR-------------------------------- //

	public Game() {	 //old 420x330
		int entireW = 1000;
		int entireH = 400;
		System.out.println(Toolkit.getDefaultToolkit().getScreenSize().getWidth());
		System.out.println(Toolkit.getDefaultToolkit().getScreenSize().getHeight());
//		1280 by 800
//		int w = 592;
//		int h = 333;
		int w = 640 * 1;
		int h = 400 * 1;
		int screenW = w * 2 * 1;
		int screenH = h * 2 * 1;
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
