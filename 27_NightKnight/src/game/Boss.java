package game;


import java.awt.image.BufferedImage;


public class Boss extends Enemy {

	protected String currentPhase;
	
	public Boss(int x, int y, BufferedImage imageSheet, int sheetX, int sheetY, BufferedImage[] movementImages, 
			BufferedImage[] stillImages, BufferedImage[] damageImages, Map currentMap, Player player, soundHandler sound) {
		
		super(x, y, "BOSS", imageSheet, sheetX, sheetY, movementImages, stillImages, damageImages, currentMap, player, sound);
		this.speed = 4;
		this.attackDamage = 20;
		this.attackTime = 240;
		this.frontVisionRange = 640;
		this.sideVisionRange = 640;
		this.rearVisionRange = 640;
		this.frontAttackRange = -8;
		this.sideAttackRange = -8;
		this.rearAttackRange = -8;
		this.currentPhase = "MELEE";
		this.health = 500;
		this.maxHealth = 500;
	}
	
	@Override
	public void update(int frame, Map currentMap, Player player) { 
		
		if (this.currentPhase == "MELEE") {
			this.playerDetected = true;
			super.update(frame, currentMap, player);
		} else {
			
		}
		
		if (this.health < this.maxHealth/2) {
			this.currentPhase = "MELEE";
		}
	}
	
	public String getCurrentPhase() {
		return this.currentPhase;
	}
}