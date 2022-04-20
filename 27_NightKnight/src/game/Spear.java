package game;

import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;


public class Spear extends Weapon {

	public Spear(int x, int y, BufferedImage imageSheet, int sheetX, int sheetY, ImageIcon inventoryImage) {
		super(x, y, imageSheet, sheetX, sheetY, inventoryImage);
		this.itemName = "SPEAR";
		this.frontRange = 64;
		this.sideRange = 0;
		this.rearRange = 0;
		this.damage = 45;
		this.attackTime = 60;
	}
}
