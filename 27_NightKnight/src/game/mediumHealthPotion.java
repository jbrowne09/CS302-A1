package game;

import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;


public class mediumHealthPotion extends Consumable {

	
	public mediumHealthPotion(int x, int y, BufferedImage imageSheet, int sheetX, int sheetY, ImageIcon inventoryImage) {
		super(x, y, imageSheet, sheetX, sheetY, inventoryImage);
		this.itemType = "CONSUMABLE";
		this.consumable = true;
		this.itemName = "MEDHEALTH";
		this.healAmount = 20;
		this.maxStackSize = 8;
		this.delayTime = 180;
	}
}
