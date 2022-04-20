package game;

import java.awt.image.BufferedImage;


public class Wall extends Tile {

	public Wall(int x, int y, String type, BufferedImage imageSheet, int sheetX, int sheetY) {
		super(x, y, type, imageSheet, sheetX, sheetY);
		this.collision = true;
		this.interactable = false;
	}
}
