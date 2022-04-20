package game;

import java.awt.image.BufferedImage;


public class Ram extends Enemy {

	public Ram(int x, int y, BufferedImage imageSheet, int sheetX, int sheetY, BufferedImage[] movementImages, 
			BufferedImage[] stillImages, BufferedImage[] damageImages, Map currentMap, Player player, soundHandler sound) {
		
		super(x, y, "RAM", imageSheet, sheetX, sheetY, movementImages, stillImages, damageImages, currentMap, player, sound);
		this.speed = 2;
		this.attackDamage = 15;
		this.attackTime = 60;
		this.frontVisionRange = 256;
		this.sideVisionRange = 256;
		this.rearVisionRange = 256;
		this.frontAttackRange = -8;
		this.sideAttackRange = -8;
		this.rearAttackRange = -8;
		this.health = 100;
		this.maxHealth = 100;
	}
	
	@Override
	public void update(int frame, Map currentMap, Player player) { 
		super.update(frame, currentMap, player);
		
		if (this.getVisionBox().intersects(player.getHitBox())) {
			this.playerDetected = true;
		} else if (this.health == this.maxHealth){
			this.playerDetected = false;
		} else {
			this.playerDetected = true;
		}
	}
}
