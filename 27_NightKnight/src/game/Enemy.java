package game;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public abstract class Enemy extends Entity {
	
	protected String enemyType;
	protected int frontVisionRange;
	protected int sideVisionRange;
	protected int rearVisionRange;
	protected boolean playerDetected;
	protected List<int[]>pathToPlayer;
	
	protected Random randNum;
	protected int timeToTravel;
	protected int frameCycles;
	protected int attackCycles;
	
	protected soundHandler sound;
		
	public Enemy(int x, int y, String enemyType, BufferedImage imageSheet, 
			int sheetX, int sheetY, BufferedImage[] movementImages, BufferedImage[] stillImages, 
			BufferedImage[] damageImages, Map currentMap, Player player, soundHandler sound) {
			
		super(x, y, "ENEMY", imageSheet, sheetX, sheetY, movementImages, stillImages, damageImages);
		this.enemyType = enemyType;
		this.speed = 2;
		
		this.randNum = new Random();
		this.timeToTravel = 0;
		this.frameCycles = 0;
		this.attackCycles = this.attackTime;
		this.playerDetected = false;
		this.pathToPlayer = new ArrayList<int[]>();
		this.pathToPlayer = findShortestPath(currentMap, player);
		this.sound = sound;
	}
		
	//implement base functionality of enemy.
	@Override
	public void update(int frame, Map currentMap, Player player) {
		super.update(frame, currentMap, player);
		
		if (!this.playerDetected) {
			if (this.frameCycles == this.timeToTravel) {
				
				this.undetectedAI(currentMap);
				this.frameCycles = 0;
			}
			
			if (this.collision(currentMap)) {
				switch(this.direction) {
				case "UP":
					this.direction = "DOWN";
					dy = 1;
					break;
				case "DOWN":
					this.direction = "UP";
					dy = -1;
					break;
				case "LEFT":
					this.direction = "RIGHT";
					dx = 1;
					break;
				case "RIGHT":
					this.direction = "LEFT";
					dx = -1;
					break;
				}
			}
		} else {
			//prevent movement after a recent attack.
			if (this.attackCycles == 0) {
				this.detectedAI(currentMap, player);
			} else {
				this.dy = 0;
				this.dx = 0;
			}
		}
		
		//update every 3 frames, 60 fps so update 20 times a second.
		if (frame % 3 == 0) {
			this.pathToPlayer = findShortestPath(currentMap, player);
		}
		
		//if the player is inside attack range perform attack.
		if (this.getAttackBox().intersects(player.getHitBox()) & attackCycles == 0) {
			player.changeHealth(-this.attackDamage);
			
			/*try {
				sound.playPlayerDamage();
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}*/
			
			player.setTakenDamage(true);
			attackCycles = this.attackTime;
		}
		
		//update timing variables based on their boundaries.
		if (this.attackCycles > 0) {
			this.attackCycles--;
		}
		if (this.frameCycles < this.timeToTravel) {
			this.frameCycles++;
		}
	}
		
	//base AI movements for when a player is undetected
	public void undetectedAI(Map currentMap) {
		
		int randDirection = generateRandom(0, 3); 	//25% chance for each direction
		int move = generateRandom(0, 1);			//50% chance to move or not.
		this.timeToTravel = generateRandom(60, 240);//random amount of frames to travel for (min 1 sec, max 4 sec).
		
		switch(randDirection) {
		case 0:
			this.direction = "UP";
			this.dy = -1;
			this.dx = 0;
			break;
		case 1:
			this.direction = "DOWN";
			this.dy = 1;
			this.dx = 0;
			break;
		case 2:
			this.direction = "LEFT";
			this.dy = 0;
			this.dx = -1;
			break;
		case 3:
			this.direction = "RIGHT";
			this.dy = 0;
			this.dx = 1;
			break;
		}
		
		if (move != 1) {
			this.dy = 0;
			this.dx = 0;
		}
	}
	
	//enemy tracks player whilst the player remains detected using the calculated
	//shortestpath between the enemy and the player.
	public void detectedAI(Map currentMap, Player player) {
		
		int[] currentPos = new int[] {this.yPos+16, this.xPos+16};
		int[] playerPos = new int[] {player.yPos+16, player.xPos+16};
		int[] nextPathPos = new int[2];
		
		nextPathPos[0] = this.pathToPlayer.get(pathToPlayer.size()-1)[0]*32 +16;
		nextPathPos[1] = this.pathToPlayer.get(pathToPlayer.size()-1)[1]*32 +16;
		
		//if the enemy is not close to the player follow the path to the player.
		if (!(currentPos[0] < playerPos[0]+16 & currentPos[0] > playerPos[0]-16) || !(currentPos[1] < playerPos[1]+16 & currentPos[1] > playerPos[1]-16)) {
			if (nextPathPos[0] > currentPos[0]) {
				this.dy = 1;
				this.dx = 0;
				this.direction = "DOWN";
			} else if (nextPathPos[0] < currentPos[0]) {
				this.dy = -1;
				this.dx = 0;
				this.direction = "UP";
			} else if (nextPathPos[1] > currentPos[1]) {
				this.dy = 0;
				this.dx = 1;
				this.direction = "RIGHT";
			} else if (nextPathPos[1] < currentPos[1]) {
				this.dy = 0;
				this.dx = -1;
				this.direction = "LEFT";
			}
		} else {
			this.dy = 0;
			this.dx = 0;
		}
		
		//if enemy is colliding with a solid tile adjust the path.
		if (this.collision(currentMap)) {
			switch(this.direction) {
			case "UP":
				if (nextPathPos[1] > currentPos[1]) {
					this.dx = 1;
				} else {
					this.dx = -1;
				}
				break;
			case "DOWN":
				if (nextPathPos[1] > currentPos[1]) {
					this.dx = 1;
				} else {
					this.dx = -1;
				}
				break;
			case "RIGHT":
				if (nextPathPos[0] > currentPos[0]) {
					this.dy = 1;
				} else {
					this.dy = -1;
				}
				break;
			case "LEFT":
				if (nextPathPos[0] > currentPos[0]) {
					this.dy = 1;
				} else {
					this.dy = -1;
				}
				break;
			}
		}
	}
	
	//find the shortest path from the enemy to the player, implements BFS.
	public List<int[]> findShortestPath(Map currentMap, Player player) {
		
		int[][] collisionMap = currentMap.getCollisionMap();
		boolean[][] visited = new boolean[collisionMap.length][collisionMap[0].length];
		
		//for storing distances to all points.
		int[][] distanceMap = new int[collisionMap.length][collisionMap[0].length];
		int currentDistance = 0;
		
		//predecessor stores the previous position used to reach the current position, this is
		//used to trace back the shortest path.
		int[][][] predecessor = new int[collisionMap.length][collisionMap[0].length][2];
		
		//the queue to store positions that need to be checked.
		List<int[]> toVisit = new ArrayList<int[]>();
		
		//this enemies current position, takes its x, y and adds 16 so the position is taken relative to the middle of a tile.
		int[]pos = new int[2];
		double posY = (this.yPos+16)/32;
		double posX = (this.xPos+16)/32;
		pos[0] = (int)Math.round(posY);
		pos[1] = (int)Math.round(posX);
		
		//initialise algorithm.
		visited[pos[0]][pos[1]] = true;
		distanceMap[pos[0]][pos[1]] = 0;
		toVisit.add(pos);
		int[] currentPos = new int[2];
		
		//the players current position.
		int[]playerPos = new int[2];
		double playerPosY = (player.yPos+16)/32;
		double playerPosX = (player.xPos+16)/32;
		playerPos[0] = (int)Math.round(playerPosY);
		playerPos[1] = (int)Math.round(playerPosX);
		
		while (toVisit.size() != 0) {
			
			currentPos = toVisit.get(0);
			toVisit.remove(0);
			currentDistance = distanceMap[currentPos[0]][currentPos[1]] +1;
			
			if (currentPos[0] == playerPos[0] & currentPos[1] == playerPos[1]) {
				break;
			}

			List<int[]> adjacent = getAdjacentPositions(currentPos, collisionMap[0].length, collisionMap.length, collisionMap, visited);
			
			for (int i = 0; i < adjacent.size(); i++) {
				toVisit.add(adjacent.get(i));
				visited[adjacent.get(i)[0]][adjacent.get(i)[1]] = true;
				distanceMap[adjacent.get(i)[0]][adjacent.get(i)[1]] = currentDistance;
				predecessor[adjacent.get(i)[0]][adjacent.get(i)[1]] = currentPos;
			}
		}
		
		List<int[]> shortestPath = new ArrayList<int[]>();
		
		//trace back the shortest path using the store predecessor locations.
		currentPos = playerPos;
		shortestPath.add(currentPos);
		
		while (distanceMap[currentPos[0]][currentPos[1]] > 1) {
			shortestPath.add(predecessor[currentPos[0]][currentPos[1]]);
			currentPos = predecessor[currentPos[0]][currentPos[1]];
		}
		
		return shortestPath;
	}
	
	//find valid adjacent positions above, below, left and right of the enemy, if the position is collidable or has been checked ignore it.
	public List<int[]> getAdjacentPositions(int[] currentPos, int arrayWidth, int arrayHeight, int[][] collisionMap, boolean[][] visited) {
		
		List<int[]> adjacent = new ArrayList<int[]>();		
		
		if (currentPos[0] != 0) {
			
			int[] above = new int[] {currentPos[0]-1, currentPos[1]};
			
			if ((visited[above[0]][above[1]] == false) & (collisionMap[above[0]][above[1]] != 1)) {
				adjacent.add(above);
			}
		}
		if (currentPos[0] != arrayHeight-1) {
			
			int[] below = new int[] {currentPos[0]+1, currentPos[1]};
			
			if ((visited[below[0]][below[1]] == false) & (collisionMap[below[0]][below[1]] != 1)) {
				adjacent.add(below);
			}
		}
		if (currentPos[1] != 0) {
			
			int[] left = new int[] {currentPos[0], currentPos[1]-1};
			
			if ((visited[left[0]][left[1]] == false) & (collisionMap[left[0]][left[1]] != 1)) {
				adjacent.add(left);
			}
		}
		if (currentPos[1] != arrayWidth-1) {
			
			int[] right = new int[] {currentPos[0], currentPos[1]+1};
			
			if ((visited[right[0]][right[1]] == false) & (collisionMap[right[0]][right[1]] != 1)) {
				adjacent.add(right);
			}
		}
		
		return adjacent;
	}
	
	public String getEnemyType() {
		return this.enemyType;
	}
	
	public boolean getPlayerDetected() {
		return this.playerDetected;
	}
	
	public Rectangle getVisionBox() {
		return getRangeBox(this.frontVisionRange, this.sideVisionRange, this.sideVisionRange, this.rearVisionRange);
	}
	
	public int generateRandom(int min, int max) {
		return randNum.nextInt(max + 1 - min) + min;
	}
	
	public List<int[]> getPathToPlayer() {
		return this.pathToPlayer;
	}
}
