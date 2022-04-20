package game;

import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;


public class smallHealthPotion extends Consumable {

	public smallHealthPotion(int x, int y, BufferedImage imageSheet, int sheetX, int sheetY, ImageIcon inventoryImage) {
		super(x, y, imageSheet, sheetX, sheetY, inventoryImage);
		this.itemType = "CONSUMABLE";
		this.consumable = true;
		this.itemName = "SMALLHEALTH";
		this.healAmount = 10;
		this.maxStackSize = 10;
		this.delayTime = 120;
	}
}
