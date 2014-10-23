import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class GamePanel extends JPanel implements Runnable{
	
//	entireW: unscaled entire width of map
//	entireH: unscaled entire height of map
//	w: unscaled width of screen
//	h: unscaled height of screen
//	screenW: scaled width of screen
//	screenH: scaled width of screen
//	offsetX: horizontal offset to set viewed screen to center around player
//	offsetY: vertical offset to set viewed screen to center around player
//	scaleX: horizontal ratio between scaled and unscaled screens
//	scaleY: vertical ratio betweem scaled and unscaled screens
	private static int entireW;
	private static int entireH;
	private static int w;
	private static int h;
	private static int screenW;
	private static int screenH;
	private static int offsetX;
	private static int offsetY;
	private static double scaleX;
	private static double scaleY;
	
//	animator: thread that runs the game loop
//	running: whether or not the game is running
//	gameOver: whether or not the game has finished
//	isPaused: whether or not the game has been paused
	private Thread animator;
	private volatile boolean running = false;
	private volatile boolean gameOver = false;
	private volatile boolean isPaused = false;
	
//	dbg: graphics of the game
//	dbImage: image that is drawn to the screen for the painting part of the game loop
	private Graphics dbg;
	private Image dbImage = null;

//	Background: Not moving, Background2: Moving
	private BufferedImage background;
	private BufferedImage background2;
	
//	period: forced minimum time in milliseconds between frames, fps ~ 1000/period
	private long period = 15;
	
//	CollisionHandler: class that handles all collisions
	private CollisionHandler ch;
	
//	player: key and mouse controlled player which a user uses
//	el: entity list that contains all non player and non projectile entities
	private static Player player;
	private static EntityList el;
	
//	updateCycle: number of update frames the game has gone through
//	startTime: system millisecond time that the game starts
//	pauseStart: system millisecond time that the game was most recently paused at
//	pauseTime: total amount of system millisecond time the game has been paused
	private static long updateCycle = 0;
	private long startTime = 0;
	private long pauseStart = 0;
	private long pauseTime = 0;
	
// --------------------------------CONSTRUCTOR-------------------------------- //
	
	public GamePanel(int w, int h){
		this(w, h, w, h);
	}
	
	public GamePanel(int entireW, int entireH, int w, int h){
		this(entireW, entireH, w, h, w, h);
	}
	
	public GamePanel(int entireW, int entireH, int w, int h, int screenW, int screenH){
		GamePanel.entireW = entireW;
		GamePanel.entireH = entireH;
		GamePanel.w = w;
		GamePanel.h = h;
		GamePanel.screenW = screenW;
		GamePanel.screenH = screenH;
		offsetX = 0;
		offsetY = 0;
		setScale();
		
		
		
		try {
			initialState();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setBackground(Color.white);
		setPreferredSize(new Dimension(screenW, screenH));
		setDoubleBuffered(true);
		setFocusable(true);
		requestFocus();
		readyToQuit();
	}
	
// --------------------------------MAP SETUP METHODS-------------------------------- //
	
	public void initialState() throws IOException{
		el = new EntityList();
		Map testm = new Map("src/Map1.txt");
		testm.initializeMap(this);
		
//		player = new Player(loadImage("Standing.png"), 100, 50, 20, 20, 100, null);
//		//player.giveCurrentWeapon(new Pistol());
//		el = new EntityList();
//		ArrayList<Entity> es = new ArrayList<Entity>();
//		es.add(new Platform(null, 50, 200, 200, 20));
//		es.add(new Platform(null, 30, 180, 20, 40));
//		es.add(new Platform(null, 250, 180, 20, 40));
//		es.add(new Platform(null, 80, 140, 140 + 350, 20));
//		es.add(new Platform(null, 80 + 140 + 350, 140, 350, 20));
//		Baddie baddie1 = new Baddie(null, 150, 100, 20, 20, true, -3, 0, 1000000);
//		Baddie baddie2 = new Baddie(null, 200, 100, 20, 20, true, 0, 0, 1000000);
//		Baddie baddie3 = new Baddie(null, 600, 100, 20, 20, true, 0, 0, 1000000);
//		es.add(baddie1);
//		es.add(baddie2);
//		es.add(baddie3);
//		el.addEntity(es);

		
//		Testing backgrounds
		background = loadImage("Background3.png");
		background2 = loadImage("Background2.png");
		
		final RocketLauncher rl = new RocketLauncher((int) Math.pow(w/2 * w/2 + h/2 * h/2, .5));
		final Pistol p = new Pistol();
		player.giveCurrentWeapon(rl);
		
		addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				int keyCode = e.getKeyCode();
				if((keyCode == KeyEvent.VK_1)){
					player.giveCurrentWeapon(p);
				}
				if((keyCode == KeyEvent.VK_2)){
					player.giveCurrentWeapon(rl);
				}
			}
		});
		
		ch = new CollisionHandler();
		this.addKeyListener(new EntityListener(player));
		this.addMouseListener(new EntityListener(player));
		this.addMouseMotionListener(new EntityListener(player));
	}
	
// --------------------------------GAME LOOP METHODS-------------------------------- //
	
	public void addNotify(){
		super.addNotify();
		startTime = System.currentTimeMillis();
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
	
	private long updateTime = 0;
	private long renderTime = 0;
	private long paintTime = 0;
	
	public void run(){
		long beforeTime, timeDiff, sleepTime;
		beforeTime = System.currentTimeMillis();
		running = true;
		while(running){
			long currTime = System.currentTimeMillis();
			gameUpdate();
			updateTime += System.currentTimeMillis() - currTime;
			currTime = System.currentTimeMillis();
			gameRender();
			renderTime += System.currentTimeMillis() - currTime;
			currTime = System.currentTimeMillis();
			paintScreen();
			paintTime += System.currentTimeMillis() - currTime;
			
			timeDiff = System.currentTimeMillis() - beforeTime;
			sleepTime = period - timeDiff;
			
			if(sleepTime <= 0){
				sleepTime = 0;
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
		if(!gameOver && !isPaused){
			updateCycle++;
//			update game state
			el.update();
			ArrayList<Entity> es = el.getEntities(getCurrScreen());
			
			ch.playerCollision(player, getCurrScreen());
			ch.entityCollision(getCurrScreen());
			
			for(int i = 0; i < el.getEntities().size(); i++){
				el.getEntities().get(i).update();
			}
			player.update();
			setOffset();
		}
		
//		boolean troll = true;
//		if(troll){
//			if(player.getY() >= entireH){
//				player.setY(-player.getH());
//			}
//			if(player.getX() >= entireW){
//				player.setX(player.getW()/2);
//			}
//			if(player.getX() < 0){
//				player.setX(entireW - player.getW()/2);
//			}
//		}
	
	}
	
	
	public void gameRender(){
		if(dbImage == null){
//			dbImage = createImage(screenW, screenH);
			GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    GraphicsDevice device = env.getDefaultScreenDevice();
		    GraphicsConfiguration config = device.getDefaultConfiguration();
		    dbImage = config.createCompatibleImage(screenW, screenH, Transparency.TRANSLUCENT);
//			dbImage = createImage(w, h);
		}
		if(dbImage == null){
			System.out.println("dbImage is null");
			return;
		}
		else{
			dbg = dbImage.getGraphics();
		}
		dbg.drawImage(background,0, 0, screenW, screenH, null, null);
		dbg.drawImage(background2, 0, 0, screenW, screenH, 0 + getOffsetX(), 0, screenW + getOffsetX(), 506, null);
//		dbg.fillRect(0, 0, w, h);
		
		dbg.setColor(Color.black);
		dbg.drawString(updateCycle + "", 50, 50);
		dbg.drawString(updateCycle/((System.currentTimeMillis() - startTime)/1000 + 1) + "", 50, 75);
		dbg.drawString("updateTime: " + (int) ((double)updateTime/(((double)System.currentTimeMillis() - (double)startTime) + 1) * 100) + ",  renderTime: " + (int) ((double)renderTime/((double)(System.currentTimeMillis() - (double)startTime) + 1) * 100) + ", paintTime:  " + (int) ((double)paintTime/(((double)System.currentTimeMillis() - (double)startTime) + 1) * 100), 50, 100);

		if(player.getCurrentWeapon() != null){
			for(Projectile p: player.getCurrentWeapon().getProjectiles()){
//				p.draw(dbg, offsetX, offsetY);
				p.draw(dbg, offsetX, offsetY, scaleX, scaleY);
			}
		}
		for(Entity e: el.getEntities(getCurrScreen())){
//			e.draw(dbg, offsetX, offsetY);
			e.draw(dbg, offsetX, offsetY, scaleX, scaleY);
		}
		
//		player.draw(dbg, offsetX, offsetY);
		player.draw(dbg, offsetX, offsetY, scaleX, scaleY);
		
		
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
				g.drawImage(dbImage, 0, 0, this);
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
				if(keyCode == KeyEvent.VK_ESCAPE){
					running = false;
				}
				if((keyCode == KeyEvent.VK_R)){
					pauseGame();
				}
				if((keyCode == KeyEvent.VK_E)){
					resumeGame();
				}
				if((keyCode == KeyEvent.VK_M)){
					try {
						initialState();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
	}
	
	public void resumeGame(){ 
		isPaused = false; 
	}
	
	public void pauseGame(){
		isPaused = true; 
	}
	
// --------------------------------SCREEN TRANSFORMATION METHODS-------------------------------- //
	
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
	
	public void setScale(){
		scaleX = (double) (screenW/w);
		scaleY= (double) (screenH/h);
	}

// --------------------------------IMAGE LOADING METHODS-------------------------------- //
	
	public BufferedImage loadImage(String path){
		BufferedImage img = null;
		try{
			img = ImageIO.read(getClass().getResource(path));
		} catch(IOException e){}
		return img;
	}
	
// --------------------------------GET/SET METHODS-------------------------------- //
	
	public static Rectangle getCurrScreen(){
	//	return new Rectangle(offsetX - EXTRA_SCREEN, offsetY - EXTRA_SCREEN, w + EXTRA_SCREEN, h + EXTRA_SCREEN);
		return new Rectangle(offsetX, offsetY, w, h);
	}
	
	public static Rectangle getRealScreen(){
		return new Rectangle((int)(offsetX * scaleX), (int)(offsetY * scaleY), (int)(w * scaleX), (int)(h * scaleY));
	}
	
	public static Rectangle getCurrScreen(int extra){
		return new Rectangle(offsetX - extra, offsetY - extra, w + extra, h + extra);
	}

	public static EntityList getEL() {
		return el;
	}

	public static void setEL(EntityList el) {
		GamePanel.el = el;
	}
	
	public static void setPlayer(Player p){
		player = p;
	}
	
	public static Player getPlayer(){
		return player;
	}
	
	public static long getUpdateCycle(){
		return updateCycle;
	}
	
	public static double getScaleX(){
		return scaleX;
	}
	
	public static double getScaleY(){
		return scaleY;
	}
	
	public static int getOffsetX(){
		return offsetX;
	}
	
	public static int getOffsetY(){
		return offsetY;
	}
	
}
