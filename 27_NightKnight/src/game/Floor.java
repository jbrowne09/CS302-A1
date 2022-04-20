package game;

import java.awt.image.BufferedImage;


public class Floor extends Tile {

	public Floor(int x, int y, String type, BufferedImage image, int sheetX, int sheetY) {
		super(x, y, type, image, sheetX, sheetY);
		this.collision = false;
		this.interactable = false;
	}
}

