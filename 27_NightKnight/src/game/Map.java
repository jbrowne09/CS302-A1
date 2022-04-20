package game;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


public class Map {
	
	private int width;
	private int height;
	private int mapNumber;
	private List<Tile> allTiles;
	private List<Item> droppedItems;
	private List<Enemy> enemies;
	private int[][] collisionMap;
	private int playerX;
	private int playerY;
	
	private soundHandler sound;

	
	public Map(int width, int height, int mapNumber, soundHandler sound) {
		this.width = width;
		this.height = height;
		this.mapNumber = mapNumber;
		this.allTiles = new ArrayList<Tile>();
		this.droppedItems = new ArrayList<Item>();
		this.enemies = new ArrayList<Enemy>();
		this.collisionMap = new int[height/32][width/32];
		this.sound = sound;
	}
	
	//TODO: implement proper entity logic (enemy spawns, fix player spawning)
	//called to insert a map or entity tile into the map.
	public void insertTile(int x, int y, String type, BufferedImage imageSheet, int sheetX, int sheetY) {
		switch(type) {
		case "WALL":
			allTiles.add(new Wall(x, y, "WALL", imageSheet, sheetX, sheetY));
			collisionMap[y/32][x/32] = 1;
			break;
		case "FLOOR":
			allTiles.add(new Floor(x, y, "FLOOR", imageSheet, sheetX, sheetY));
			collisionMap[y/32][x/32] = 0;
			break;
		case "CHEST":
			allTiles.add(new Chest(x, y, "CHEST", imageSheet, sheetX, sheetY));
			collisionMap[y/32][x/32] = 1;
			break;
		case "TUTORIALCHEST":
			allTiles.add(new TutorialChest(x, y, "CHEST", imageSheet, sheetX, sheetY));
			collisionMap[y/32][x/32] = 1;
			break;
		case "PLAYER":
			this.playerX = x*32;
			this.playerY = y*32;
			break;
		}
	}
	
	public void insertDoorTile(int x, int y, String type, BufferedImage imageSheet, int sheetX, int sheetY, int entryMap, int exitMap) {
		this.allTiles.add(new Door(x, y, type, imageSheet, sheetX, sheetY, entryMap, exitMap));
		collisionMap[y/32][x/32] = 1;
	}
	
	public void insertEnemy(int x, int y, String type, BufferedImage imageSheet, int sheetX, int sheetY, 
			BufferedImage[] movementImages, BufferedImage[] stillImages, BufferedImage[] damageImages, Player player) {
		
		switch(type) {
		case "RAM":
			enemies.add(new Ram(x, y, imageSheet, sheetY, sheetY, movementImages, stillImages, damageImages, this, player, sound));
			break;
		case "BOSS":
			enemies.add(new Boss(x, y, imageSheet, sheetY, sheetY, movementImages, stillImages, damageImages, this, player, sound));
			break;
		}
	}
	
	public List<Enemy> getEnemies() {
		return this.enemies;
	}
	
	public void removeEnemy(int index) {
		this.enemies.remove(index);
	}
	
	public int[][] getCollisionMap() {
		return this.collisionMap;
	}
	
	public void setCollisionMap(int num, int y, int x) {
		this.collisionMap[y][x] = num;
	}
	
	public int getMapNumber() {
		return this.mapNumber;
	}
	
	public List<Tile> getType(String type) {
		
		List<Tile> foundTiles = new ArrayList<>();
		
		for (int i = 0; i < allTiles.size(); i++) {
			if (allTiles.get(i).getType() == type) {
				foundTiles.add(allTiles.get(i));
			}
		}
		
		return foundTiles;
	}
	
	public void addDroppedItem(Item item) {
		this.droppedItems.add(item);
	}
	
	public void deleteDroppedItem(int index) {
		this.droppedItems.remove(index);
	}
	
	public List<Item> getDroppedItems() {
		return this.droppedItems;
	}
	public List<Tile> getTiles() {
		return this.allTiles;
	}
	
	public int getPlayerX() {
		return this.playerX;
	}
	
	public void setPlayerX(int x) {
		this.playerX = x;
	}
	
	public int getPlayerY() {
		return this.playerY;
	}
	
	public void setPlayerY(int y) {
		this.playerY = y;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
}