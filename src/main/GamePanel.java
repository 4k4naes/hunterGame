package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import dataBase.Counter;
import dataBase.historyOfMoves;
import entity.Player;
//import loginy.Sound;
import objects.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final int originalTileSize = 16;
	final int scale = 3;
	
	public final int tileSize = originalTileSize * scale;
	public final int maxScreenCol = 12;
	public final int maxScreenRow = 8;
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeight = tileSize * maxScreenRow;
	
	public final int maxWorldCol = 60;
	public final int maxWorldRow = 60;
	
	final int FPS = 60;
	
	public int gameState;
	public final int playState = 0;
	public final int pauseState = 1;
	
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler(this);
//	Sound sound = new Sound();
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	Thread gameThread;
	public Player player = new Player(this,keyH);
	public SuperObject obj[] = new SuperObject[10];
	
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}

	public void setupGame() {
		aSetter.setObject();
		gameState = playState;
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
		historyOfMoves.initializeDatabase();
		Counter.start();
	}
	
	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while(gameThread != null) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}
			
			if(timer >= 1000000000) {
				ui.showFPS(drawCount);
				System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}
		}
	}
	
	public void update() {
		
		if(gameState == playState) {
			player.update();
		} 
		if(gameState == pauseState){
			//pause
		}

	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;

		tileM.draw(g2);
		for(int i = 0; i < obj.length; i++) {
			if(obj[i] != null){
				obj[i].draw(g2, this);
			}
		}
		player.draw(g2);
		ui.draw(g2);
		
		g2.dispose();
	}
	
//	public void playMusic(int i) {
//		
//		sound.setFile(i);
//		sound.play();
//		sound.loop();
//	}
//	
//	public void stopMusic() {
//		sound.stop();
//	}
//	
//	public void playSE(int i) {
//		sound.setFile(i);
//		sound.play();
//	}
}
