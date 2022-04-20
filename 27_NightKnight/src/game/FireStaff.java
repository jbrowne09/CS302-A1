package game;

import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;


public class FireStaff extends RangedWeapon {

	public FireStaff(int x, int y, BufferedImage imageSheet, int sheetX, int sheetY, ImageIcon inventoryImage, BufferedImage projectileSheet) {
		super(x, y, imageSheet, sheetX, sheetY, inventoryImage, projectileSheet);
		this.itemName = "FIRESTAFF";
		this.damage = 50;
		this.attackTime = 60;
		this.projectileWidth = 32;
		this.projectileHeight = 26;
	}
}
