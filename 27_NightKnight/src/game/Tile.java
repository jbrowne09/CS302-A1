package game;

import java.awt.image.BufferedImage;


public abstract class Tile extends GameObject{

	protected boolean collision;
	protected boolean interactable;
	
	public Tile(int x, int y, String type, BufferedImage imageSheet, int sheetX, int sheetY) {
		super(x, y, type, imageSheet, sheetX, sheetY, 32, 32);
	}
	
	public boolean hasCollision() {
		return this.collision;
	}
	
	public boolean isInteractable() {
		return this.interactable;
	}
}
