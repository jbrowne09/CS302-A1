package game;

import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;


public class Bow extends RangedWeapon {

	public Bow(int x, int y, BufferedImage imageSheet, int sheetX, int sheetY, ImageIcon inventoryImage, BufferedImage projectileSheet) {
		super(x, y, imageSheet, sheetX, sheetY, inventoryImage, projectileSheet);
		
		this.itemName = "BOW";
		this.damage = 20;
		this.attackTime = 35;
		this.projectileWidth = 32;
		this.projectileHeight = 8;
	}
}
