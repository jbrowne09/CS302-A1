package game;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.List;


public class gameWindow extends JFrame implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel GUIPanel;
	private gamePanel GPanel;
	
	private ResourceHandler resource;
	private MenuHandler menu;
	private MapHandler maps;
	private CameraHandler camera;
	private ItemHandler items;
	private GUIHandler GUI;
	private soundHandler sound;
	private String gameState;
	private Player player;
	private String useText;
	private int frame;
	private double xZoom;
	private int selectedButton;
	private List<String> nextGameStates;
	private int score;
	private int numEnemiesKilled;
	private BufferedImage pauseIcon;
	private BufferedImage damageIndicator;
	private BufferedImage healIndicator;
	
	private int timer10mins;
	private int timer1mins;
	private int timer10secs;
	private int timer1secs;
	
	
	public gameWindow() throws IOException {
		
		this.gameState = "MAINMENU";
		resource = new ResourceHandler();
		sound = new soundHandler();
			
		player = new Player(32, 32, resource.loadImage("resources/PlayerPink-Still.png"), 0, 0, 
				resource.loadImageArray("resources/PlayerPink-Movement.png"), 
					resource.loadImageArray("resources/PlayerPink-Still.png"), 
						resource.loadImageArray("resources/PlayerPink-Damage.png"), this, sound);
		
		this.addKeyListener(new InputHandler(player, this));
		menu = new MenuHandler(this, resource, sound);
		
		//Setting up JFrame layout with game window at the top and GUI at the bottom.
		this.setLayout(new BorderLayout(0, 0));
		this.setMinimumSize(new Dimension (1024, 768));
		
		GUIPanel = new JPanel();
		GPanel = new gamePanel(this);
		GUIPanel.setLayout(new GridLayout(1, 2));
		GUIPanel.setPreferredSize(new Dimension(1440, 200));
		
		this.setTitle("NightKnight");
		ImageIcon image = new ImageIcon("src/resources/Logo.png");
		this.setIconImage(image.getImage());
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		this.frame = 0;
		this.selectedButton = 0;
		this.xZoom = 1;
		this.nextGameStates = new ArrayList<String>();
		this.useText = "NULL";
		this.timer10mins = 0;
		this.timer1mins = 0;
		this.timer10secs = 0;
		this.timer1secs = 0;
		this.score = 0;
		this.numEnemiesKilled = 0;
		this.pauseIcon = resource.loadImage("resources/pauseIcon.png");
		this.damageIndicator = resource.loadImage("resources/DamageIndicator.png");
		this.healIndicator = resource.loadImage("resources/HealIndicator.png");
		
		maps = new MapHandler(resource, player, this, this.sound);
		camera = new CameraHandler(this.GPanel, maps.getCurrentMap().getPlayerX(), maps.getCurrentMap().getPlayerY());
		items = new ItemHandler(maps.getCurrentMap(), player);
		GUI = new GUIHandler(this.GUIPanel, player, resource);
		
		menu.changeTo(this.gameState, this.GPanel, this.GUIPanel, this.gameState);
	}
	
	private void update() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		
		
		this.score = (this.timer10mins*1000 + this.timer1mins*100 + this.timer10secs*10 + this.timer1secs);
		menu.update(this.gameState, this.selectedButton);
		
		if (this.gameState == "GAME") {
			String tileText = "NULL";
			String itemText = "NULL";
			
			if (this.player.getHealth() < 1) {
				this.setGameState("DIED");
			}
			
			GUI.update(this.timer10mins, this.timer1mins, this.timer10secs, this.timer1secs);
			tileText = maps.update(frame, maps.getCurrentMap(), player);
			itemText = items.update(frame, maps.getCurrentMap(), player);
			
			
			player.update(frame, maps.getCurrentMap(), player);
			camera.update(xZoom, player, maps.getCurrentMap());
			
			if (itemText != "NULL") {
				this.useText = itemText;
			} else {
				this.useText = tileText;
			}
			
			//determining which frame the game is currently at, used for playing animations in various classes.
			frame++;
			if (frame == 60) {
				this.countDownTimer();
				frame = 0;
			}
		}
	}
	
	//Custom JPanel for drawing objects with graphics.
	class gamePanel extends JPanel {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		gameWindow game;
		
		gamePanel(gameWindow game) {
			this.setPreferredSize(new Dimension(1440, 700));
			this.game = game;
		}
		
		//Overrided paint method, all drawing is done here.
		@Override
		public void paintComponent(Graphics graphics) {
			super.paintComponent(graphics);
			
			Graphics2D graphics2D = (Graphics2D)graphics;
			
			//zoom is based off current panel width and height (which changes as the jFrame and jPanel are resized)
			//the fixed value is the standard window width.
			xZoom = ((double)GPanel.getWidth()/(double)1440);
			
			//translate camera based off translation axis in cameraHandler, scale is based off current panel size
			//this makes it so the rendered map scales appropriately with game window size(if the window is resized).
			if (xZoom >= 1) {
				graphics2D.scale(xZoom, xZoom);
			} else {
				xZoom = 1;
			}
			
			graphics2D.translate(-camera.getxAxis(), -camera.getyAxis());
			
			//map drawing logic.
			List<Tile> tiles = maps.getCurrentMap().getTiles();
			List<Item> items = maps.getCurrentMap().getDroppedItems();
			List<Enemy> enemies = maps.getCurrentMap().getEnemies();
			
			for (int i = 0; i < tiles.size(); i++) {
				Tile currentTile = tiles.get(i);
				graphics2D.drawImage(currentTile.getRenderedImage(), currentTile.getX(), currentTile.getY(), null);
			}
			
			for (int i = 0; i < items.size(); i++) {
				Item currentItem = items.get(i);
				graphics2D.drawImage(currentItem.getRenderedImage(), currentItem.getX(), currentItem.getY(), null);
			}
			
			List<Projectile> projectiles = player.getProjectiles();
			
			for (int i = 0; i < projectiles.size(); i++) {
				graphics2D.drawImage(projectiles.get(i).getRenderedImage(), projectiles.get(i).getX(), projectiles.get(i).getY(), null);
			}
			
			for (int i = 0; i < enemies.size(); i++) {
				Enemy currentEnemy = enemies.get(i);
				
				graphics2D.drawImage(currentEnemy.getRenderedImage(), currentEnemy.getX(), currentEnemy.getY(), null);
				
				if (currentEnemy.getPlayerDetected()) {
					graphics2D.setColor(Color.RED); 
					graphics2D.fillRect(currentEnemy.xPos +14, currentEnemy.yPos -22, 4, 10);
					graphics2D.fillRect(currentEnemy.xPos +14, currentEnemy.yPos -8, 4, 4);
					graphics2D.setColor(Color.BLACK); 
				}
			}
			
			graphics2D.drawImage(player.getImage(), player.getX(), player.getY(), null);
			
			if (player.getInventoryItem(player.getSelectedSlot()) != null) {
				if (player.getInventoryItem(player.getSelectedSlot()).getItemType() == "WEAPON") {
					graphics2D.setColor(Color.RED);
					graphics2D.draw(player.getAttackBox());
					graphics2D.setColor(Color.BLACK);
				}
			}
			
			//when the player is able to interact with a tile display text above their head.
			if (useText != "NULL") {
				graphics2D.setColor(Color.WHITE);
				graphics2D.drawString(useText, player.getX() -((useText.length()/2) *4), player.getY());
				graphics2D.setColor(Color.BLACK);
			}
			
			if (player.hasTakenDamage()) {
				graphics2D.drawImage(game.getDamageIndicator(), (int)game.getCamera().getxAxis(), (int)game.getCamera().getyAxis(), null);
			}
			
			if (player.getHealed()) {
				graphics2D.drawImage(game.getHealIndicator(), (int)game.getCamera().getxAxis(), (int)game.getCamera().getyAxis(),  null);
			}
			
			if (game.getGameState() == "PAUSE") {
				graphics2D.drawImage(game.getPauseIcon(), this.getWidth()/2 -192, (int)game.getCamera().getyAxis() + this.getHeight()/2 -32, null);
			}

			graphics2D.dispose();
			graphics.dispose();
		}
	}
	
	public void run() {
		
		long lastTime = System.nanoTime();
		double nanoSecondConversion = 1000000000.0/60;
		double changeInSeconds = 0;
		
		while(true) {
			long now = System.nanoTime();
			
			changeInSeconds += (now - lastTime) / nanoSecondConversion;
			while(changeInSeconds >= 1) {
				
				try {
					update();
				} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
					e.printStackTrace();
				}
				
				this.repaint();
				changeInSeconds = 0;
			}
			
			lastTime = now;
		}
	}
	
	public void keyPress(String key) throws IOException {
		
		switch(key) {
		case "UP":
			this.selectedButton--;
			
			if (this.selectedButton < 0) {
				this.selectedButton = 0;
			}
			
		break;
		case "DOWN":
			this.selectedButton++;
			
			if (this.nextGameStates.size() < 0) {
				this.selectedButton = -1;
			} else if (this.selectedButton > this.nextGameStates.size()-1) {
				this.selectedButton = nextGameStates.size() -1;
			}
		break;
		case "ENTER":
			
			if (this.nextGameStates.size() > 0 & this.selectedButton > -1) {
				
				String currentState = this.gameState;
				
				this.gameState = this.nextGameStates.get(this.selectedButton);
				menu.changeTo(this.gameState, this.GPanel, this.GUIPanel, currentState);
			}
			
		break;
		case "PAUSE":
			
			if (this.gameState == "GAME") {
				this.gameState = "PAUSE";
			} else if (this.gameState == "PAUSE") {
				this.gameState = "GAME";
			}
			
		break;
		case "ESCAPE":
			
			if (this.gameState == "PAUSE") {
				System.exit(0);
			} else if (this.gameState == "GAME"){
				this.gameState = "PAUSE";
			}
		break;
		case "PGDOWN":
			maps.setCurrentMap(maps.getLastMap());
			
			player.setInventory(resource.createType(player.getX(), player.getY(), "SPEAR"), 0);
			player.setInventory(resource.createType(player.getX(), player.getY(), "FIRESTAFF"),  1);
			player.setInventory(resource.createType(player.getX(), player.getY(), "MEDHEALTH"), 4);
			player.setInventory(resource.createType(player.getX(), player.getY(), "FIRSTAID"), 5);
			
			player.setInventoryStackSize(4, 8);
			player.setInventoryStackSize(5, 4);
		}
	}
	
	public void countDownTimer() {
		
		this.timer1secs++;
		
		if (this.timer1secs > 9) {
			this.timer1secs = 0;
			this.timer10secs++;
		}
		if (this.timer10secs > 5) {
			this.timer10secs = 0;
			this.timer1mins++;
		}
		if (this.timer1mins > 9) {
			this.timer1mins = 0;
			this.timer10mins++;
		}
	}
	
	public BufferedImage getPauseIcon() {
		return this.pauseIcon;
	}
	
	public BufferedImage getHealIndicator() {
		return this.healIndicator;
	}
	
	public BufferedImage getDamageIndicator() {
		return this.damageIndicator;
	}
	
	public CameraHandler getCamera() {
		return this.camera;
	}
	
	public MenuHandler getMenuHandler() {
		return this.menu;
	}
	
	public String getGameState() {
		return this.gameState;
	}
	
	public void incrementEnemiesKilled() {
		this.numEnemiesKilled++;
	}
	
	public int getEnemiesKilled() {
		return this.numEnemiesKilled;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public void setGameState(String state) throws IOException {
		
		String currentState = this.gameState;
		this.gameState = state;
		menu.changeTo(state, this.GPanel, this.GUIPanel, currentState);
	}
	
	public void setNextGameState(String state) {
		this.nextGameStates.add(state);
	}
	
	public void resetNextGameState() {
		this.nextGameStates.removeAll(nextGameStates);
	}
	
	public gamePanel getGamePanel() {
		return this.GPanel;
	}
	
	public JPanel getGUIPanel() {
		return this.GUIPanel;
	}
	
	public static void main(String[] args) throws IOException {
		
		gameWindow game = new gameWindow();
		Thread gameThread = new Thread(game);
		gameThread.start();
	}
}