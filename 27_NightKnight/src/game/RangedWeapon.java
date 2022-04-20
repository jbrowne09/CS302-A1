package game;

import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;


public class RangedWeapon extends Item {
	
	protected int damage;
	protected int attackTime;
	protected BufferedImage projectileSheet;
	protected int projectileHeight;
	protected int projectileWidth;

	public RangedWeapon(int x, int y, BufferedImage imageSheet, int sheetX, int sheetY, ImageIcon inventoryImage, BufferedImage projectileSheet) {
		super(x, y, imageSheet, sheetX, sheetY, inventoryImage);
		this.itemType = "RANGEDWEAPON";
		this.consumable = false;
		this.projectileSheet = projectileSheet;
	}
	
	public int getDamage() {
		return this.damage;
	}
	
	public BufferedImage getProjectileSheet() {
		return this.projectileSheet;
	}
	
	public int getAttackTime() {
		return this.attackTime;
	}
	
	public int getProjectileWidth() {
		return this.projectileWidth;
	}
	
	public int getProjectileHeight() {
		return this.projectileHeight;
	}
}
