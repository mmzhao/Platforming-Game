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
import java.util.ArrayList;

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
	private Baddie baddie;	
	private ArrayList<Entity> es;
	
	public GamePanel(){
		this(500, 400);
	}
	
	public GamePanel(int width, int height){
		w = width;
		h = height;
		
		player = new Player(loadImage("Mario8BitSprite.png"), 100, 100, 20, 20, 100, null);
		player.giveCurrentWeapon(new Weapon("Pistol", loadImage("SamplePistol.png"), 500, 1, 5, 1000, 2, .25, 5, 10000, player));
		es = new ArrayList<Entity>();
		es.add(new Platform(null, 50, 200, 200, 20));
		es.add(new Platform(null, 30, 180, 20, 40));
		es.add(new Platform(null, 250, 180, 20, 40));
		es.add(new Platform(null, 80, 140, 140, 20));
		baddie = new Baddie(null, 150, 100, 20, 20, true, -5, 0, 40);
		es.add(baddie);
		
		setBackground(Color.white);
		setPreferredSize(new Dimension(w, h));
		setFocusable(true);
		requestFocus();
		readyToQuit();
		this.addKeyListener(new EntityListener(player));
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
			player.sidesCollided(es);
			baddie.sidesCollided(es); 
			if(player.getCurrentWeapon() != null){
				for(Projectile p: player.getCurrentWeapon().getProjectiles()){
					p.sidesCollided(es);
					p.update();
				}
			}
			baddie.update();
			player.update();
		}
		
		boolean troll = true;
		if(troll){
			if(player.getY() >= h){
				player.setY(-player.getH());
			}
			if(baddie.getY() >= h){
				baddie.setY(-baddie.getH());
			}
			if(player.getX() >= w){
				player.setX(player.getW()/2);
			}
			if(player.getX() < 0){
				player.setX(w - player.getW()/2);
			}
			for(Projectile p: player.getCurrentWeapon().getProjectiles()){
				p.sidesCollided(es);
				if(p.getX() >= w){
					p.setX(p.getW()/2);
				}
				if(p.getX() < 0){
					p.setX(w - p.getW()/2);
				}
			}
			
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
		
		player.draw(dbg);
		if(player.getCurrentWeapon() != null){
			for(Projectile p: player.getCurrentWeapon().getProjectiles()){
				p.draw(dbg);
			}
		}
		for(Entity e: es){
			e.draw(dbg);
		}
		
		
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
	
	private BufferedImage loadImage(String path){
		BufferedImage img = null;
		try{
			img = ImageIO.read(getClass().getResource(path));
		} catch(IOException e){}
		return img;
	}
	
}
