package game;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class Projectile extends GameObject {
	
	private int dy;
	private int dx;
	private int speed;
	private soundHandler sound;

	public Projectile(int x, int y, BufferedImage imageSheet, int sheetX, int sheetY, int width, int height, Player player, soundHandler sound) {
		
		super(x, y, "PROJECTILE", imageSheet, sheetX, sheetY, width, height);
		this.dx = 0;
		this.dy = 0;
		this.speed = 6;
		this.sound = sound;
		
		this.xPos = x;
		this.yPos = y;
		
		int projectileWidth = this.width;
		int projectileHeight = this.height;
		
		switch(player.direction) {
		case "UP":
			
			this.xPos = x + (32 - projectileHeight)/2;
			this.yPos = y;
			
			this.dy = -1;
			this.dx = 0;
			this.renderedImage = imageSheet.getSubimage(32, 0, projectileHeight, projectileWidth);
			this.HitBox = new Rectangle(this.xPos, this.yPos, projectileHeight, projectileWidth);
			break;
		case "DOWN":
			
			this.xPos = x + (32 - projectileHeight)/2;
			this.yPos = y + 32;
			
			this.dy = 1;
			this.dx = 0;

			this.renderedImage = imageSheet.getSubimage(64, 0, projectileHeight, projectileWidth);
			this.HitBox = new Rectangle(this.xPos, this.yPos, projectileHeight, projectileWidth);
			break;
		case "LEFT":
			
			this.xPos = x;
			this.yPos = y + (32 - projectileHeight)/2;
			
			this.dy = 0;
			this.dx = -1;
			
			this.renderedImage = imageSheet.getSubimage(0 , 0, projectileWidth, projectileHeight);
			this.HitBox = new Rectangle(this.xPos, this.yPos, projectileWidth, projectileHeight);
			break;
		case "RIGHT":
			
			this.xPos = x + 32;
			this.yPos = y + (32 - projectileHeight)/2;
			
			this.dy = 0;
			this.dx = 1;
			this.renderedImage = imageSheet.getSubimage(96, 0, projectileWidth, projectileHeight);
			this.HitBox = new Rectangle(this.xPos, this.yPos, projectileWidth, projectileHeight);
			break;
		}
	}
	
	@Override
	public void update(int frame, Map currentMap, Player player) {
		this.xPos += this.dx * this.speed;
		this.yPos += this.dy * this.speed;
		super.update(frame,  currentMap, player);
		
		
		List<Enemy> enemies = currentMap.getEnemies();
		
		for (int i = 0; i < enemies.size(); i++) {
			if (this.HitBox.intersects(enemies.get(i).getHitBox())) {
				enemies.get(i).changeHealth(-player.getAttackDamage());
				enemies.get(i).setTakenDamage(true);
				player.destroyProjectile(this);
				
				/*switch (enemies.get(i).getEnemyType()) {
				case "RAM":
					
					try {
						sound.playRamHit();
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
						e.printStackTrace();
					}
					
					break;
				case "BOSS":
					
					try {
						sound.playBossHit();
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
						e.printStackTrace();
					}
					
					break;
				}*/
			}
		}
		
		List<Tile> mapTiles = currentMap.getTiles();
		
		for (int i = 0; i < mapTiles.size(); i++) {
			if (this.HitBox.intersects(mapTiles.get(i).getHitBox())) {
				if (mapTiles.get(i).hasCollision()) {
					player.destroyProjectile(this);
				}
			}
		}
	}
}
