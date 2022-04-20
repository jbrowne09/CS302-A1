package game;

import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;


public class firstAidKit extends Consumable {

	
	public firstAidKit(int x, int y, BufferedImage imageSheet, int sheetX, int sheetY, ImageIcon inventoryImage) {
		super(x, y, imageSheet, sheetX, sheetY, inventoryImage);
		this.itemType = "CONSUMABLE";
		this.consumable = true;
		this.itemName = "FIRSTAID";
		this.healAmount = 50;
		this.maxStackSize = 4;
		this.delayTime = 240;
	}
}
