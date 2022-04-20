package game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class Player extends Entity {

	private boolean heldW;
	private boolean heldS;
	private boolean heldA;
	private boolean heldD;
	private boolean interactionQueued;
	private boolean actionQueued;
	private Item[] inventory;
	private int[] inventoryStackSizes;
	private int actionCycles;
	private int selectedSlot;
	private List<Projectile> projectiles;
	private gameWindow game;
	private int countDamageIndicatorCycles;
	private int countHealIndicatorCycles;
	private boolean healed;
	private soundHandler sound;
	
	
	public Player(int x, int y, BufferedImage imageSheet, int sheetX, int sheetY, BufferedImage[] movementImages, 
			BufferedImage[] stillImages, BufferedImage[] damageImages, gameWindow game, soundHandler sound) {
		
		super(x, y, "PLAYER", imageSheet, sheetX, sheetY, movementImages, stillImages, damageImages);
		this.interactionQueued = false;
		this.actionQueued = false;
		this.inventory = new Item[6];
		this.inventoryStackSizes = new int[6];
		this.speed = 4;
		this.selectedSlot = 0;
		this.health = 100;
		this.maxHealth = 100;
		this.projectiles = new ArrayList<Projectile>();
		this.game = game;
		this.countDamageIndicatorCycles = 0;
		this.countHealIndicatorCycles = 0;
		this.healed = false;
		this.sound = sound;
	}
	
	@Override
	public void update(int frame, Map currentMap, Player player) {
		
		if (this.takenDamage & countDamageIndicatorCycles < 10) {
			this.countDamageIndicatorCycles++;
		} else {
			this.takenDamage = false;
			this.countDamageIndicatorCycles = 0;
		}
		
		if (this.healed & countHealIndicatorCycles < 20) {
			this.countHealIndicatorCycles++;
		} else {
			this.healed = false;
			this.countHealIndicatorCycles = 0;
		}
		
		Item selectedItem = this.inventory[this.selectedSlot];
		
		//update player action parameters based on the equipped item.
		if (selectedItem != null) {
			
			switch (selectedItem.getItemType()) {
			case "WEAPON":
				Weapon weapon = (Weapon)selectedItem;
				this.frontAttackRange = weapon.getFrontRange();
				this.sideAttackRange = weapon.getSideRange();
				this.rearAttackRange = weapon.getSideRange();
				this.attackDamage = weapon.getDamage();
				this.attackTime = weapon.getAttackTime();
				break;
			case "RANGEDWEAPON":
				RangedWeapon ranged = (RangedWeapon)selectedItem;
				this.attackDamage = ranged.getDamage();
				this.frontAttackRange = 0;
				this.sideAttackRange = 0;
				this.rearAttackRange = 0;
				this.attackTime = ranged.getAttackTime();
				break;
			case "CONSUMABLE":
				Consumable item = (Consumable)selectedItem;
				this.frontAttackRange = 0;
				this.sideAttackRange = 0;
				this.rearAttackRange = 0;
				this.attackDamage = 0;
				this.attackTime = item.getDelayTime();
				break;
			}
		} else {
			this.frontAttackRange = 0;
			this.sideAttackRange = 0;
			this.rearAttackRange = 0;
			this.attackDamage = 0;
			this.attackTime = 0;
		}
		
		//if the player is not on cooldown for performing an action, use the currently selected item or attack.
		//TODO: add player attack animation array, change it when weapon is equipped.
		if (this.actionQueued & actionCycles == 0 & selectedItem != null) {
			
			switch(selectedItem.getItemType()) {
			case "CONSUMABLE":
				Consumable healthItem = (Consumable)selectedItem;
				this.changeHealth(healthItem.getHealAmount());
				this.inventoryStackSizes[this.selectedSlot] -= 1;
				this.healed = true;
				
				/*try {
					sound.playHeal();
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
					e1.printStackTrace();
				}*/
				
				if (this.inventoryStackSizes[this.selectedSlot] < 1) {
					this.inventory[this.selectedSlot] = null;
					this.inventoryStackSizes[this.selectedSlot] = 0;
				}
				break;
			case "WEAPON":
				
				/*try {
					sound.playMeleeAttack();
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
					e.printStackTrace();
				}*/
				
				List<Enemy> enemies = currentMap.getEnemies();
					
				for (int i = 0; i < enemies.size(); i++) {
					
					Enemy currentEnemy = enemies.get(i);
					if (currentEnemy.getHitBox().intersects(player.getAttackBox())) {
							currentEnemy.changeHealth(-this.attackDamage);
							currentEnemy.setTakenDamage(true);
							
							/*switch (currentEnemy.getEnemyType()) {
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

				break;
			case "RANGEDWEAPON":
				
				RangedWeapon ranged = (RangedWeapon)selectedItem;
				
				this.projectiles.add(new Projectile(this.xPos, this.yPos, ranged.getProjectileSheet(),0 , 0, ranged.getProjectileWidth(), 
						ranged.getProjectileHeight(), this, sound));
				break;
			}
			
			this.actionCycles = this.attackTime;
		}
		
		//update all projectiles.
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).update(frame, currentMap, player);
		}
		
		super.update(frame, currentMap, player);
			
		//update timing variables based on their boundaries.
		if (this.actionCycles > 0) {
			this.actionCycles--;
		}
	}
	
	public void keyPress(String key, int invNum) {
		
		switch(key) {
		case "UP":
			this.dx = 0;
			this.dy = -1;
			
			if (!heldW & this.game.getGameState() == "GAME") {
				this.setImage(stillImages[3]);
			}
			
			heldW = true;
			this.direction = "UP";
			
			break;
		case "DOWN":
			this.dx = 0;
			this.dy = 1;
			
			if (!heldS & this.game.getGameState() == "GAME") {
				this.setImage(stillImages[0]);
			}
			
			heldS = true;
			this.direction = "DOWN";
			break;
		case "LEFT":
			this.dy = 0;
			this.dx = -1;
			
			if (!heldA & this.game.getGameState() == "GAME") {
				this.setImage(stillImages[1]);
			}
			
			heldA = true;
			this.direction = "LEFT";
			break;
		case "RIGHT":
			this.dy = 0;
			this.dx = 1;
			
			if (!heldD & this.game.getGameState() == "GAME") {
				this.setImage(stillImages[2]);
			}
			
			heldD = true;
			this.direction = "RIGHT";
			break;
		case "INTERACT":
			this.interactionQueued = true;
			break;
		case "ACTION":
			this.actionQueued = true;
			break;
		case "EQUIP":
			this.selectedSlot = invNum;
			break;
		}
	}
	
	public void keyRelease(String key) {
		
		switch(key) {
		case "UP":
			this.dy = 0;
			heldW = false;
			break;
		case "DOWN":
			this.dy = 0;
			heldS = false;
			break;
		case "LEFT":
			this.dx = 0;
			heldA = false;
			break;
		case "RIGHT":
			this.dx = 0;
			heldD = false;
			break;
		case "INTERACT":
			this.interactionQueued = false;
			break;
		case "ATTACK":
			this.actionQueued = false;
			break;
		}
		
		if (heldW & this.game.getGameState() == "GAME") {
			this.dx = 0;
			this.dy = -1;
			this.direction = "UP";
			this.setImage(stillImages[3]);
		} else if (heldS & this.game.getGameState() == "GAME") {
			this.dx = 0;
			this.dy = 1;
			this.direction = "DOWN";
			this.setImage(stillImages[0]);
		} else if (heldA & this.game.getGameState() == "GAME") {
			this.dy = 0;
			this.dx = -1;
			this.direction= "LEFT";
			this.setImage(stillImages[1]);
		} else if (heldD & this.game.getGameState() == "GAME") {
			this.dy = 0;
			this.dx = 1;
			this.direction = "RIGHT";
			this.setImage(stillImages[2]);
		}
	}
	
	public boolean getHealed() {
		return this.healed;
	}
	
	public void destroyProjectile(Projectile projectile) {
		this.projectiles.remove(projectile);
	}
	
	public List<Projectile> getProjectiles() {
		return this.projectiles;
	}
	
	public Item[] getInventory() {
		return this.inventory;
	}
	
	public Item getInventoryItem(int index) {
		return this.inventory[index];
	}
	
	public void setInventory(Item item, int index) {
		this.inventory[index] = item;
	}
	
	public void addInventory(int index) {
		this.inventoryStackSizes[index]++;
	}
	
	public int getInventoryStackSize(int index) {
		return this.inventoryStackSizes[index];
	}
	
	public void setInventoryStackSize(int index, int value) {
		this.inventoryStackSizes[index] = value;
	}
	
	public boolean getInteractionQueued() {
		return this.interactionQueued;
	}
	
	public void resetInteractionQueued() {
		this.interactionQueued = false;
	}
	
	public int getSelectedSlot() {
		return this.selectedSlot;
	}
	
	public void setSelectedSlot(int index) {
		this.selectedSlot = index;
	}
	
	public int getSelectedSlot(int index) {
		return this.selectedSlot;
	}
	
	public Item getEquippedItem() {
		return this.inventory[selectedSlot];
	}
}
