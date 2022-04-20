package game;

import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;


public abstract class Consumable extends Item {

	protected int healAmount;
	protected int delayTime;
	
	public Consumable(int x, int y, BufferedImage imageSheet, int sheetX, int sheetY, ImageIcon inventoryImage) {
		super(x, y, imageSheet, sheetX, sheetY, inventoryImage);
		this.itemType = "CONSUMABLE";
		this.consumable = true;
	}
	
	public int getHealAmount() {
		return this.healAmount;
	}
	
	public int getDelayTime() {
		return this.delayTime;
	}
}
