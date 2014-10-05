import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class GamePanel extends JPanel implements Runnable{
	private static int w;
	private static int h;
	
	private Thread animator;
	private volatile boolean running = false;
	private volatile boolean gameOver = false;
	
	private Graphics dbg;
	private Image dbImage = null;
	
	private long period = 20;
	
	private Player player;
	
	public GamePanel(){
		this(500, 400);
	}
	
	public GamePanel(int width, int height){
		w = width;
		h = height;
		
		BufferedImage img = null;
		try{
			img = ImageIO.read(new File("circle.png"));
		} catch(IOException e) {
			
		}
		
		player = new Player(img, 100, 100, 50, 50);
		setBackground(Color.white);
		setPreferredSize(new Dimension(w, h));
		setFocusable(true);
		requestFocus();
		readyToQuit();
	}
	
	public void addNotify(){
		super.addNotify();
		startGame();
	}
	
	public void startGame(){
		if(animator == null || !running){
			animator = new Thread(this);
			animator.start();
		}
	}
	
	public void stopGame(){
		running = false;
	}
	
	public void run(){
		long beforeTime, timeDiff, sleepTime;
		beforeTime = System.currentTimeMillis();
		running = true;
		while(running){
			gameUpdate();
			gameRender();
			paintScreen();
			
			timeDiff = System.currentTimeMillis() - beforeTime;
			sleepTime = period - timeDiff;
			
			if(sleepTime <= 0){
				sleepTime = 5;
			}
			
			try {
				Thread.sleep(sleepTime);
			}
			catch(InterruptedException ex){}
			
			beforeTime = System.currentTimeMillis();
		}
		System.exit(0);
	}
	
	public void gameUpdate(){
		if(!gameOver){
			//update game state
			player.update();
		}
	}
	
	public void gameRender(){
		if(dbImage == null){
			dbImage = createImage(w, h);
			if(dbImage == null){
				System.out.println("dbImage is null");
				return;
			}
			else{
				dbg = dbImage.getGraphics();
			}
		}
		dbg.setColor(Color.white);
		dbg.fillRect(0, 0, w, h);
		
		dbg.setColor(Color.black);
		player.draw(dbg);
		
		// draw game elements
		
		if(gameOver){
			// you can get rekt later
		}
	}
	
	public void paintScreen(){
		Graphics g;
		try{
			g = this.getGraphics();
			if((g != null) && (dbImage != null)){
				g.drawImage(dbImage, 0, 0, null);
			}
			Toolkit.getDefaultToolkit().sync();
			g.dispose();
		}
		catch(Exception e){
			System.out.println("Graphics context error: " + e);
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(dbImage != null){
			g.drawImage(dbImage, 0, 0, null);
		}
	}
	
	private void readyToQuit(){
		addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				int keyCode = e.getKeyCode();
				if((keyCode == KeyEvent.VK_ESCAPE) || (keyCode == KeyEvent.VK_Q)){
					running = false;
				}
			}
		});
	}
	
	
}
