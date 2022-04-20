package game;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MapHandler {
	
	private List<Map> mapList;
	private ResourceHandler resource;
	private soundHandler sound;
	private TileType[] mapTiles;
	private TileType[] entityTiles;
	private Map currentMap;
	private Player player;
	private gameWindow game;

	//variables used globally across functions.
	int numMapTiles = 93;
	int numEntityTiles = 7;
	
	
	public MapHandler(ResourceHandler resource, Player player, gameWindow game, soundHandler sound) {
		this.resource = resource;
		this.sound = sound;
		this.mapList = new ArrayList<Map>();
		this.mapTiles = new TileType[numMapTiles];
		this.entityTiles = new TileType[numEntityTiles];
		this.player = player;
		this.initTileTypes();
		this.initialiseMaps();
		this.game = game;
	}
	
	private void initTileTypes() {
		mapTiles[0] = new TileType(55, 112, 55, "WALL", 0, 0);  //TREE
		mapTiles[1] = new TileType(34, 95, 109, "WALL", 1, 2);  //WATER
		mapTiles[2] = new TileType(10, 104, 10, "FLOOR", 4, 2); //GRASS
		
		mapTiles[3] = new TileType(141, 102, 22, "FLOOR", 8, 2);//PATHV
		mapTiles[4] = new TileType(142, 103, 23, "FLOOR", 9, 3);//PATHH
		mapTiles[5] = new TileType(110, 78, 16, "FLOOR", 7, 1);//TOP LEFT
		mapTiles[6] = new TileType(111, 79, 17, "FLOOR", 8, 1);//TOP RIGHT
		mapTiles[7] = new TileType(112, 80, 18, "FLOOR", 8, 3);//BOT LEFT
		mapTiles[8] = new TileType(113, 81, 19, "FLOOR", 7, 2);//BOT RIGHT
		mapTiles[9] = new TileType(67, 47, 11, "FLOOR", 6, 3);//END TOP
		mapTiles[10] = new TileType(68, 48, 12, "FLOOR", 6, 9);//END BOT
		mapTiles[11] = new TileType(69, 49, 13, "FLOOR", 8, 9);//END LEFT
		mapTiles[12] = new TileType(70, 50, 14, "FLOOR", 9, 9);//END RIGHT
		
		mapTiles[13] = new TileType(73, 52, 14, "FLOOR", 5, 4);//BRIDGE TOP PATH LEFT
		mapTiles[14] = new TileType(74, 53, 15, "FLOOR", 6, 4);//BRIDGE TOP PATH MID
		mapTiles[15] = new TileType(75, 54, 16, "FLOOR", 7, 4);//BRIDGE TOP PATH RIGHT
		mapTiles[16] = new TileType(76, 55, 17, "FLOOR", 5, 8);//BRIDGE BOT PATH LEFT
		mapTiles[17] = new TileType(77, 56, 18, "FLOOR", 6, 8);//BRIDGE BOT PATH MID
		mapTiles[18] = new TileType(78, 57, 19, "FLOOR", 7, 8);//BRIDGE BOT PATH RIGHT
		
		mapTiles[19] = new TileType(19, 48, 55, "FLOOR", 6, 5);//BRIDGE MIDDLE TOP 
		mapTiles[20] = new TileType(20, 49, 56, "FLOOR", 6, 6);//BRIDGE MIDDLE MID
		mapTiles[21] = new TileType(21, 50, 57, "FLOOR", 6, 7);//BRIDGE MIDDLE BOT
		mapTiles[22] = new TileType(22, 51, 58, "FLOOR", 5, 5);//BRIDGE LEFT TOP
		mapTiles[23] = new TileType(23, 52, 59, "FLOOR", 5, 6);//BRIDGE LEFT MID
		mapTiles[24] = new TileType(24, 53, 60, "FLOOR", 5, 7);//BRIDGE LEFT BOT
		mapTiles[25] = new TileType(25, 54, 61, "FLOOR", 7, 5);//BRIDGE RIGHT TOP
		mapTiles[26] = new TileType(26, 55, 62, "FLOOR", 7, 6);//BRIDGE RIGHT MID
		mapTiles[27] = new TileType(27, 56, 63, "FLOOR", 7, 7);//BRIDGE RIGHT BOT
		
		mapTiles[28] = new TileType(79, 42, 37, "WALL", 3, 1);//WOODWALL TOP LEFT
		mapTiles[29] = new TileType(80, 43, 38, "WALL", 5, 1);//WOODWALL TOP RIGHT
		mapTiles[30] = new TileType(81, 44, 39, "WALL", 3, 3);//WOODWALL BOT LEFT
		mapTiles[31] = new TileType(82, 45, 40, "WALL", 5, 3);//WOODWALL BOT RIGHT
		mapTiles[32] = new TileType(127, 64, 56, "WALL", 4, 1);//WOODWALL TOP
		mapTiles[33] = new TileType(128, 65, 57, "WALL", 3, 2);//WOODWALL LEFT
		mapTiles[34] = new TileType(129, 66, 58, "WALL", 5, 2);//WOODWALL RIGHT
		mapTiles[35] = new TileType(130, 69, 59, "WALL", 4, 3);//WOODWALL DOWN
		
		mapTiles[36] = new TileType(15, 73, 15, "WALL", 4, 0);//WOODWALL TOP SPIKES
		mapTiles[37] = new TileType(1, 1, 1, "WALL", 6, 0);//TOP OPEN RIGHT
		mapTiles[38] = new TileType(196, 196, 196, "WALL", 7, 0);//TOP OPEN LEFT
		mapTiles[39] = new TileType(0, 0, 0, "WALL", 8, 0);//BOT OPEN RIGHT
		mapTiles[40] = new TileType(195, 195, 195, "WALL", 9, 0);//BOT OPEN LEFT
		
		mapTiles[41] = new TileType(155, 127, 54, "FLOOR", 0, 1);//SHORE INNER TOP LEFT
		mapTiles[42] = new TileType(156, 128, 55, "FLOOR", 2, 1);//SHORE INNER TOP RIGHT
		mapTiles[43] = new TileType(157, 129, 56, "FLOOR", 0, 3);//SHORE INNER BOT LEFT
		mapTiles[44] = new TileType(158, 130, 57, "FLOOR", 2, 3);//SHORE INNER BOT RIGHT
		mapTiles[45] = new TileType(139, 113, 48, "FLOOR", 1, 4);//SHORE TOP LEFT
		mapTiles[46] = new TileType(140, 114, 49, "FLOOR", 2, 4);//SHORE TOP RIGHT
		mapTiles[47] = new TileType(141, 115, 50, "FLOOR", 1, 5);//SHORE BOT LEFT
		mapTiles[48] = new TileType(142, 116, 51, "FLOOR", 2, 5);//SHORE BOT RIGHT
		mapTiles[49] = new TileType(191, 153, 61, "FLOOR", 1, 1);//SHORE DOWN
		mapTiles[50] = new TileType(192, 154, 62, "FLOOR", 0, 2);//SHORE RIGHT
		mapTiles[51] = new TileType(193, 155, 63, "FLOOR", 2, 2);//SHORE LEFT
		mapTiles[52] = new TileType(194, 156, 64, "FLOOR", 1, 3);//SHORE UP
		mapTiles[53] = new TileType(160, 147, 116, "WALL", 0, 4);//SHORE ROCKS UP
		mapTiles[54] = new TileType(161, 148, 117, "WALL", 0, 5);//SHORE ROCKS DOWN
		mapTiles[55] = new TileType(162, 149, 118, "WALL", 3, 4);//SHORE ROCKS LEFT
		mapTiles[56] = new TileType(163, 150, 119, "WALL", 4, 4);//SHORE ROCKS RIGHT
		
		mapTiles[57] = new TileType(255, 0, 216, "FLOOR", 0, 7);//MOVEMENT TUTORIAL
		mapTiles[58] = new TileType(255, 0, 217, "FLOOR", 1, 7);
		mapTiles[59] = new TileType(255, 0, 218, "FLOOR", 2, 7);
		mapTiles[60] = new TileType(255, 0, 219, "FLOOR", 0, 8);
		mapTiles[61] = new TileType(255, 0, 220, "FLOOR", 1, 8);
		mapTiles[62] = new TileType(255, 0, 221, "FLOOR", 2, 8);
		mapTiles[63] = new TileType(255, 0, 222, "FLOOR", 0, 9);
		mapTiles[64] = new TileType(255, 0, 223, "FLOOR", 1, 9);
		mapTiles[65] = new TileType(255, 0, 224, "FLOOR", 2, 9);
		
		mapTiles[66] = new TileType(0, 0, 255, "FLOOR", 0, 10);//INTERACT TUTORIAL
		mapTiles[67] = new TileType(1, 0, 255, "FLOOR", 1, 10);
		mapTiles[68] = new TileType(2, 0, 255, "FLOOR", 2, 10);
		mapTiles[69] = new TileType(3, 0, 255, "FLOOR", 0, 11);
		mapTiles[70] = new TileType(4, 0, 255, "FLOOR", 1, 11);
		mapTiles[71] = new TileType(5, 0, 255, "FLOOR", 2, 11);
		mapTiles[72] = new TileType(6, 0, 255, "FLOOR", 0, 12);
		mapTiles[73] = new TileType(7, 0, 255, "FLOOR", 1, 12);
		mapTiles[74] = new TileType(8, 0, 255, "FLOOR", 2, 12);
		
		mapTiles[75] = new TileType(255, 150, 0, "FLOOR", 3, 10); //ATTACKUSE TUTORIAL
		mapTiles[76] = new TileType(255, 150, 1, "FLOOR", 4, 10);
		mapTiles[77] = new TileType(255, 150, 2, "FLOOR", 5, 10);
		mapTiles[78] = new TileType(255, 150, 3, "FLOOR", 3, 11);
		mapTiles[79] = new TileType(255, 150, 4, "FLOOR", 4, 11);
		mapTiles[80] = new TileType(255, 150, 5, "FLOOR", 5, 11);
		mapTiles[81] = new TileType(255, 150, 6, "FLOOR", 3, 12);
		mapTiles[82] = new TileType(255, 150, 7, "FLOOR", 4, 12);
		mapTiles[83] = new TileType(255, 150, 8, "FLOOR", 5, 12);
		
		mapTiles[84] = new TileType(233, 181, 249, "FLOOR", 6, 10); //EQUIP TUTORIAL
		mapTiles[85] = new TileType(233, 182, 249, "FLOOR", 7, 10);
		mapTiles[86] = new TileType(233, 183, 249, "FLOOR", 8, 10);
		mapTiles[87] = new TileType(233, 184, 249, "FLOOR", 6, 11);
		mapTiles[88] = new TileType(233, 185, 249, "FLOOR", 7, 11);
		mapTiles[89] = new TileType(233, 186, 249, "FLOOR", 8, 11);
		mapTiles[90] = new TileType(233, 187, 249, "FLOOR", 6, 12);
		mapTiles[91] = new TileType(233, 188, 249, "FLOOR", 7, 12);
		mapTiles[92] = new TileType(233, 189, 249, "FLOOR", 8, 12);
		
		entityTiles[0] = new TileType(255, 0, 0, "RAM", 0, 0);
		entityTiles[1] = new TileType(0, 255, 0, "PLAYER", 0, 0); //this is for the inital player spawn when they first enter the level.
		entityTiles[2] = new TileType(255, 255, 0, "CHEST", 0, 0);
		entityTiles[3] = new TileType(255, 255, 255, "TUTORIALCHEST", 0, 0);
		entityTiles[4] = new TileType(0, 255, 255, "NEXTDOOR", 1, 0);
		entityTiles[5] = new TileType(255, 0, 255, "PREVDOOR", 1, 0);
		entityTiles[6] = new TileType(150, 0, 0, "BOSS", 0, 0);
	}
	
	public void initialiseMaps() {
		this.loadLevel(resource.loadImage("resources/TutorialMAP.png"), resource.loadImage("resources/TutorialS.png"), 0);
		this.loadLevel(resource.loadImage("resources/FieldMAP.png"), resource.loadImage("resources/FieldS.png"), 1);
		this.loadLevel(resource.loadImage("resources/ForestMAP.png"), resource.loadImage("resources/ForestS.png"), 2);
		this.loadLevel(resource.loadImage("resources/GatesMAP.png"), resource.loadImage("resources/GatesS.png"), 3);
		this.loadLevel(resource.loadImage("resources/CastleMAP.png"), resource.loadImage("resources/CastleS.png"), 4);
		this.setCurrentMap(this.getMap(0));
	}
	
	public String update(int frame, Map currentMap, Player player) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		
		//check to see if enemies have reached 0 health, remove them if they have.
		List<Enemy> enemies = currentMap.getEnemies();
		
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).update(frame, currentMap, player);
		}
		
		for (int i = 0; i < enemies.size(); i++) {
			
			Enemy currentEnemy = enemies.get(i);
			
			if (currentEnemy.health == 0) {
				currentMap.removeEnemy(i);
				
				game.incrementEnemiesKilled();
				
				if (currentEnemy.getEnemyType() == "BOSS") {
					this.game.setGameState("WIN");
				}
				
				String randItem = resource.generateRandomItem(75, false);
				
				if (randItem != "NODROP" & currentEnemy.getEnemyType() != "BOSS") {
					currentMap.addDroppedItem(resource.createType(currentEnemy.getX(), currentEnemy.getY(), randItem));
				}
			}
		}
		
		
		InteractableTile interactTile = this.checkTileInteractivity();
		String statusText = "NULL";
		
		//if an interactable tile was found in range and the player has queued an interaction interact with the tile.
		if (interactTile != null) {
			if (player.getInteractionQueued()) {
				
				this.interactWith(interactTile);
				player.resetInteractionQueued();
			}
			
			statusText = ("Use "+interactTile.getType());
		}
		
		return statusText;
	}
	
	//load a map from a png image file: uses a RGB color scheme defined in RGBmapScheme.txt to generate tiles based on pixel colors,
	//two png files are required one for generating the map tiles and another for entity spawns: both of these need to be the same
	//size, map number should be specified so doors can properly connect between maps.
	public void loadLevel(BufferedImage mapImage, BufferedImage spawnsImage, int mapNumber) {
		
		int w = mapImage.getWidth();
		int h = mapImage.getHeight();	
		
		BufferedImage mapSheet = resource.loadImage("resources/TileSheet-MAP.png");
		BufferedImage chestSheet = resource.loadImage("resources/chest2.png");
		BufferedImage enemySheet = resource.loadImage("resources/Ram-Still.png");
		BufferedImage[] enemyMove = resource.loadImageArray("resources/Ram-Movement.png");
		BufferedImage[] enemyStill = resource.loadImageArray("resources/Ram-Still.png");
		BufferedImage[] ramDamage = resource.loadImageArray("resources/Ram-Damage.png");
		BufferedImage[] bossMove = resource.loadImageArray("resources/Boss-Movement.png");
		BufferedImage[] bossStill = resource.loadImageArray("resources/Boss-Still.png");
		BufferedImage bossSheet = resource.loadImage("resources/Boss-Still.png");
		BufferedImage[] bossDamage = resource.loadImageArray("resources/Boss-Damage.png");
		
		Map map = new Map(w*32, h*32, mapNumber, sound);
		this.mapList.add(map);
		
		//First loop for the map png file, loops for every pixel.
		for(int x = 0; x < w; x++) {
			for(int y = 0; y < h; y++) {
				Color mColor = new Color(mapImage.getRGB(x, y));
				
				int mRed = mColor.getRed();
				int mGreen = mColor.getGreen();
				int mBlue = mColor.getBlue();
				
				for (int i = 0; i < mapTiles.length; i++) {
					if (mRed == mapTiles[i].getRed() && mGreen == mapTiles[i].getGreen() && mBlue == mapTiles[i].getBlue()) {
						this.mapList.get(mapNumber).insertTile(x*32, y*32, mapTiles[i].getType(), mapSheet, mapTiles[i].getXPos(), 
								mapTiles[i].getYPos());
					}
				}
			}
		}
		
		//loop for entity map, done separately so entities are rendered on top of map tiles.
		for(int x = 0; x < w; x++) {
			for(int y = 0; y < h; y++) {
				Color sColor = new Color(spawnsImage.getRGB(x, y));
				
				int sRed = sColor.getRed();
				int sGreen = sColor.getGreen();
				int sBlue = sColor.getBlue();
				
				for (int i = 0; i < entityTiles.length; i++) {
					if (sRed == entityTiles[i].getRed() && sGreen == entityTiles[i].getGreen() && sBlue == entityTiles[i].getBlue()) {
								
						switch(entityTiles[i].getType()) {
						case "PREVDOOR":
							this.mapList.get(mapNumber).insertDoorTile(x*32, y*32, "DOOR", mapSheet, entityTiles[i].getXPos(), 
									entityTiles[i].getYPos(), mapNumber, mapNumber -1);
							break;
						case "NEXTDOOR":
							this.mapList.get(mapNumber).insertDoorTile(x*32, y*32, "DOOR", mapSheet, entityTiles[i].getXPos(), 
									entityTiles[i].getYPos(), mapNumber, mapNumber +1);
							break;
						case "CHEST":
							this.mapList.get(mapNumber).insertTile(x*32, y*32, entityTiles[i].getType(), chestSheet, entityTiles[i].getXPos(), 
									entityTiles[i].getYPos());
							break;
						case "TUTORIALCHEST":
							this.mapList.get(mapNumber).insertTile(x*32, y*32, entityTiles[i].getType(), chestSheet, entityTiles[i].getXPos(), 
									entityTiles[i].getYPos());
							break;
						case "PLAYER":
							this.mapList.get(mapNumber).setPlayerX(x*32);
							this.mapList.get(mapNumber).setPlayerY(y*32);
							break;
						case "RAM":
							this.mapList.get(mapNumber).insertEnemy(x*32, y*32, "RAM", enemySheet, entityTiles[i].getXPos(), 
									entityTiles[i].getYPos(), enemyMove, enemyStill, ramDamage, player);
							break;
						case "BOSS":
							this.mapList.get(mapNumber).insertEnemy(x*32, y*32, "BOSS", bossSheet, entityTiles[i].getXPos(), 
									entityTiles[i].getYPos(), bossMove, bossStill, bossDamage, player);
						}
					}
				}
			}
		}
	}
	
	//interact with a door or chest, input tile is a pointer to the actual tile contained in the currentMap.
	public void interactWith(InteractableTile tile) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		switch(tile.getType()) {
		case "DOOR":
			Door door = (Door)tile;
			int nextMapIndex = door.useDoor();
			
			//handle adjusting player spawns to just below or above the door for when the player re-enters the map.
			//position is dependent on whether the door takes the player to the next map or the previous map.
			if (door.getDoorType() == "NEXTDOOR") {
				
				this.currentMap.setPlayerX(door.getX());
				this.currentMap.setPlayerY(door.getY() +32);
			} else {
				
				this.currentMap.setPlayerX(door.getX());
				this.currentMap.setPlayerY(door.getY() -32);
			}
			
			//load the next map into the current map, if the map doesn't exist return a message
			if (this.mapList.size() > nextMapIndex) {
				
				this.setCurrentMap(mapList.get(nextMapIndex));
			} else {
				System.out.println("ERROR: Map at position '"+nextMapIndex+"' does not exist");
			}
			break;
		case "CHEST":
			
			Chest chest = (Chest)tile;
			Item newItem = chest.openChest(this.resource);
			//sound.playChestOpen();
			
			if (newItem != null) {
				this.currentMap.addDroppedItem(newItem);
			}
			
			break;
		}
	}
	
	//check for all tiles the player can interact with, the first tile found is returned.
	public InteractableTile checkTileInteractivity() {
		
		List<Tile> Tiles = currentMap.getTiles();
		
		for(int i = 0; i < Tiles.size(); i++) {
			
			Tile currentTile = Tiles.get(i);
			
			if(currentTile.isInteractable())  {
				InteractableTile interactTile = (InteractableTile)currentTile;
				
				if(interactTile.canInteract(player.HitBox)) {
					return interactTile;
				}
			}
		}
		
		return null;
	}
	
	public Map getMap(int listIndex) {
		return this.mapList.get(listIndex);
	}
	
	public Map getCurrentMap() {
		return this.currentMap;
	}
	
	public Map getLastMap() {
		return this.mapList.get(this.mapList.size() -1);
	}
	
	public void setCurrentMap(Map map) {
		
		player.setX(map.getPlayerX());
		player.setY(map.getPlayerY());
		this.currentMap = map;
	}
}
