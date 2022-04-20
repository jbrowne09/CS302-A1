package game;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;


public abstract class Entity extends GameObject {

	protected String direction;
	protected int dx;
	protected int dy;
	protected int speed;
	protected int health;
	protected int maxHealth;
	
	protected int attackDamage;
	protected int attackTime;
	protected int frontAttackRange;
	protected int sideAttackRange;
	protected int rearAttackRange;
	
	protected boolean takenDamage;
	protected int damageCounter;
	
	protected BufferedImage[]damageImages;
	protected BufferedImage[] stillImages;
	protected BufferedImage[] movementImages;
	
	//variables used globally across functions.
	int animationImageNum = 0;
	
	public Entity(int x, int y, String type, BufferedImage imageSheet, int sheetX, int sheetY, 
			BufferedImage[] movementImages, BufferedImage[] stillImages, BufferedImage[] damageImages) {
		
		super(x, y, type, imageSheet, sheetX, sheetY, 32, 32);
		this.direction = "DOWN";
		this.dx = 0;
		this.dy = 0;
		this.movementImages = movementImages;
		this.stillImages = stillImages;
		this.damageImages = damageImages;
		this.takenDamage = false;
		this.damageCounter = 0;
	}
	
	//collisions with the solid tiles
	protected boolean collision(Map map) {
		
		List<Tile> Tiles = map.getTiles();
		
		for(int i = 0; i < Tiles.size(); i++) {
			
			Tile currentTile = Tiles.get(i);
			
			//check for collision with wall
			if(currentTile.hasCollision())  {
				
				if(this.HitBox.intersects(currentTile.getHitBox())) {
					
					switch(this.direction) {
					case "UP":
						if (this.dy == -1) {
							this.yPos = currentTile.getY() + currentTile.getHeight();
						}
						break;
					case "DOWN":
						if (this.dy == 1) {
							this.yPos = currentTile.getY() - this.height;
						}
						break;
					case "LEFT":
						if (this.dx == -1) {
							this.xPos = currentTile.getX() + currentTile.getWidth();
						}
						break;
					case "RIGHT":
						if (this.dx == 1) {
							this.xPos = currentTile.getX() - this.width;
						}
						break;
					}
					
					return true;
				}
			}
		}
		
		return false;
	}
	
	@Override
	public void update(int frame, Map currentMap, Player player) {
		
		this.xPos += this.dx * this.speed;
		this.yPos += this.dy * this.speed;
		super.update(frame, currentMap, player);
		
		//check for collision after the hitbox has been updated.
		collision(currentMap);
		
		//Alternate between images to create a walking animation whilst the entity is moving otherwise assign a static
		//image, the number of frames between image switching is dependent on the players speed via formula below:
		int switchTime = 0;
		
		if (this.speed > 0) {
			switchTime = 20/this.speed;
		
			switch(this.direction) {
			case "UP":
				if (dy != 0 || dx != 0) {
					animateEntity(movementImages, frame, switchTime, 3, 3);
				} else {
					this.setImage(stillImages[3]);
				}
				break;
			case "DOWN":
				if (dy != 0 || dx != 0) {
					animateEntity(movementImages, frame, switchTime, 0, 3);
				} else {
					this.setImage(stillImages[0]);
				}
				break;
			case "LEFT":
				if (dy != 0 || dx != 0) {
					animateEntity(movementImages, frame, switchTime, 1, 3);
				} else {
					this.setImage(stillImages[1]);
				}
				break;
			case "RIGHT":
				if (dy != 0 || dx != 0) {
					animateEntity(movementImages, frame, switchTime, 2, 3);
				} else {
					this.setImage(stillImages[2]);
				}
				break;
			}
		}
		
		if (this.takenDamage & this.damageCounter < 10) {
			this.dx = 0;
			this.dy = 0;
			switch(this.direction) {
			case "UP":

				this.setImage(damageImages[3]);
				break;
			case "DOWN":

				this.setImage(damageImages[0]);
				break;
			case "LEFT":
				
				this.setImage(damageImages[1]);
				break;
			case "RIGHT":

				this.setImage(damageImages[2]);
				break;
			}
			
			this.damageCounter++;
		} else {
			this.takenDamage = false;
			this.damageCounter = 0;
		}
	}
	
	//cycle between images in specified array: selected image is assigned as the entities image: frameCount is the frame 
	//the system is currently at, delay is the number of frames between image switching, sheetRow is the row on the imageSheet 
	//that images will be pulled from, numCols is the number of images per row in that imageSheet
	public void animateEntity(BufferedImage[] images, int frame, int delay, int sheetRow, int numCols) {
		
		if (frame % delay == 0) {
			this.setImage(images[animationImageNum + sheetRow*numCols]);
			animationImageNum++;
			
			if (animationImageNum > (numCols - 1)) {
				animationImageNum = 0;
			}
		}
	}
	
	//produces a rectangle extended from the entities position by given ranges.
	public Rectangle getRangeBox(int forwardRange, int leftRange, int rightRange, int backRange) {
		
		int boxX = this.xPos;
		int boxY = this.yPos;
		int boxWidth = this.width;
		int boxHeight = this.height;
		
		switch(this.direction) {
		case "UP":
			boxX = this.xPos - leftRange;
			boxY = this.yPos - forwardRange;
			boxWidth = this.width + leftRange + rightRange;
			boxHeight = this.height + forwardRange + backRange;
			break;
		case "DOWN":
			boxX = this.xPos - leftRange;
			boxY = this.yPos - backRange;
			boxWidth = this.width + leftRange + rightRange;
			boxHeight = this.height + forwardRange + backRange;
			break;
		case "LEFT":
			boxX = this.xPos - forwardRange;
			boxY = this.yPos - rightRange;
			boxWidth = this.width + forwardRange + backRange;
			boxHeight = this.height + rightRange + leftRange;
			break;
		case "RIGHT":
			boxX = this.xPos - backRange;
			boxY = this.yPos - leftRange;
			boxWidth = this.width + backRange + forwardRange;
			boxHeight = this.height + leftRange + rightRange;
			break;
		}
		
		return new Rectangle(boxX, boxY, boxWidth, boxHeight);
	}
	
	public void setTakenDamage(boolean input) {
		this.takenDamage = input;
	}
	
	public boolean hasTakenDamage() {
		return this.takenDamage;
	}
	
	public Rectangle getAttackBox() {
		return getRangeBox(this.frontAttackRange, this.sideAttackRange, this.sideAttackRange, this.rearAttackRange);
	}
	
	public String getDirection() {
		return this.direction;
	}
	
	public void setMovementImages(BufferedImage[] images) {
		this.movementImages = images;
	}
	
	public void setImage(BufferedImage image) {
		this.renderedImage = image;
	}
	
	public BufferedImage getImage() {
		return this.renderedImage;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public int getSpeed() {
		return this.speed;
	}
	
	public void setAttackDamage(int damage) {
		this.attackDamage = damage;
	}
	
	public int getAttackDamage() {
		return this.attackDamage;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public int getHealth() {
		return this.health;
	}
	
	public int getMaxHealth() {
		return this.maxHealth;
	}
	
	public void changeHealth(int change) {
		this.health += change;

		if (this.health < 0) {
			this.health = 0;
		}
		if (this.health > this.maxHealth) {
			this.health = this.maxHealth;
		}
	}
}
