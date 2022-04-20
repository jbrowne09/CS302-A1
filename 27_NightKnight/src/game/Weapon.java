package game;

import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;


public class Weapon extends Item {
	
	protected int damage;
	protected int frontRange;
	protected int sideRange;
	protected int rearRange;
	protected int attackTime;

	public Weapon(int x, int y, BufferedImage imageSheet, int sheetX, int sheetY, ImageIcon inventoryImage) {
		super(x, y, imageSheet, sheetX, sheetY, inventoryImage);
		this.itemType = "WEAPON";
		this.consumable = false;
	}
	
	public int getFrontRange() {
		return this.frontRange;
	}
	
	public int getSideRange() {
		return this.sideRange;
	}
	
	public int getRearRange() {
		return this.rearRange;
	}
	
	public int getDamage() {
		return this.damage;
	}
	
	public int getAttackTime() {
		return this.attackTime;
	}
}
