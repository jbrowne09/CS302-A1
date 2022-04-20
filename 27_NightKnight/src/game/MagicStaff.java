package game;

import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;


public class MagicStaff extends RangedWeapon {

	public MagicStaff(int x, int y, BufferedImage imageSheet, int sheetX, int sheetY, ImageIcon inventoryImage, BufferedImage projectileSheet) {
		super(x, y, imageSheet, sheetX, sheetY, inventoryImage, projectileSheet);
		this.itemName = "MAGICSTAFF";
		this.damage = 15;
		this.attackTime = 25;
		this.projectileWidth = 24;
		this.projectileHeight = 12;
	}
}
