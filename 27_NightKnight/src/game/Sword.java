package game;

import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;


public class Sword extends Weapon {

	public Sword(int x, int y, BufferedImage imageSheet, int sheetX, int sheetY, ImageIcon inventoryImage) {
		super(x, y, imageSheet, sheetX, sheetY, inventoryImage);
		this.itemName = "SWORD";
		this.frontRange = 32;
		this.sideRange = 0;
		this.rearRange = 0;
		this.damage = 25;
		this.attackTime = 40;
	}
}
