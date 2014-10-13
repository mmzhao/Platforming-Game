import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
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
	private static int entireW;
	private static int entireH;
	private static int w;
	private static int h;
	private static int offsetX;
	private static int offsetY;
	
	private Thread animator;
	private volatile boolean running = false;
	private volatile boolean gameOver = false;
	
	private Graphics dbg;
	private Image dbImage = null;
	
	private long period = 20;
	
	private CollisionHandler ch;
	
	private Player player;
	private EntityList el;
	
	private final int EXTRA_SCREEN = 20;
	
	public GamePanel(){
		this(500, 400);
	}
	
	public GamePanel(int w, int h){
		this(w, h, w, h);
	}
	
	public GamePanel(int entireW, int entireH, int w, int h){
		this.entireW = entireW;
		this.entireH = entireH;
		this.w = w;
		this.h = h;
		offsetX = 0;
		offsetY = 0;
		
		player = new Player(loadImage("Standing.png"), 100, 50, 20, 20, 100, null);
		player.giveCurrentWeapon(new Pistol());
		el = new EntityList();
		ArrayList<Entity> es = new ArrayList<Entity>();
		es.add(new Platform(null, 50, 200, 200, 20));
		es.add(new Platform(null, 30, 180, 20, 40));
		es.add(new Platform(null, 250, 180, 20, 40));
		es.add(new Platform(null, 80, 140, 140 + 700, 20));
		Baddie baddie = new Baddie(null, 150, 100, 20, 20, true, -3, 0, 40);
		es.add(baddie);
		el.addEntity(es);
		
		ch = new CollisionHandler();
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
//			//update game state
			ArrayList<Entity> es = el.getEntities(getCurrScreen());
//			player.sidesCollided(es);
//			ArrayList<Baddie> bs = el.getBaddies(getCurrScreen());
//			for(int i = 0; i < bs.size(); i++){
//				bs.get(i).sidesCollided(es); 
//			}

			
			ch.playerCollision(player, el, getCurrScreen());
			ch.entityCollision(el, getCurrScreen());
			
//			if(player.getCurrentWeapon() != null){
//				for(Projectile p: player.getCurrentWeapon().getProjectiles()){
//					p.sidesCollided(es);
//					p.update(getCurrScreen());
//				}
//			}

			
			for(int i = 0; i < es.size(); i++){
				es.get(i).update();
			}
			player.update();
			setOffset();
		}
		
		boolean troll = true;
		if(troll){
			if(player.getY() >= entireH){
				player.setY(-player.getH());
			}
//			if(baddie.getY() >= h){
//				baddie.setY(-baddie.getH());
//			}
			if(player.getX() >= entireW){
				player.setX(player.getW()/2);
			}
			if(player.getX() < 0){
				player.setX(entireW - player.getW()/2);
			}
//			for(Projectile p: player.getCurrentWeapon().getProjectiles()){
//				p.sidesCollided(es);
//				if(p.getX() >= w){
//					p.setX(p.getW()/2);
//				}
//				if(p.getX() < 0){
//					p.setX(w - p.getW()/2);
//				}
//			}
		}
	
	}
	
	public void setOffset(){
		offsetX = (int) player.getX() - w / 2;
		offsetY = (int) player.getY() - h / 2;
		if(offsetX < 0){
			offsetX = 0;
		}
		else if(offsetX > entireW - w){
			offsetX = entireW - w;
		}
		if(offsetY < 0){
			offsetY = 0;
		}
		else if(offsetY > entireH - h){
			offsetY = entireH - h;
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
		
		player.draw(dbg, offsetX, offsetY);
		if(player.getCurrentWeapon() != null){
			for(Projectile p: player.getCurrentWeapon().getProjectiles()){
				p.draw(dbg, offsetX, offsetY);
			}
		}
		for(Entity e: el.getEntities(getCurrScreen())){
			e.draw(dbg, offsetX, offsetY);
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
	
	private Rectangle getCurrScreen(){
	//	return new Rectangle(offsetX - EXTRA_SCREEN, offsetY - EXTRA_SCREEN, w + EXTRA_SCREEN, h + EXTRA_SCREEN);
		return new Rectangle(offsetX, offsetY, w, h);
	}
	
	private Rectangle getCurrScreen(int extra){
		return new Rectangle(offsetX - extra, offsetY - extra, w + extra, h + extra);
	}
	
}
