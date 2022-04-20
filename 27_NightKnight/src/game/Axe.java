package game;

import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;


public class Axe extends Weapon {

	public Axe(int x, int y, BufferedImage imageSheet, int sheetX, int sheetY, ImageIcon inventoryImage) {
		super(x, y, imageSheet, sheetX, sheetY, inventoryImage);
		this.itemName = "AXE";
		this.frontRange = 32;
		this.sideRange = 32;
		this.rearRange = 32;
		this.damage = 40;
		this.attackTime = 60;
	}
}
