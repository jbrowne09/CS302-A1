package game;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;


public class InteractableTile extends Tile {
	
	protected Rectangle interactionBox;
	
	public InteractableTile(int x, int y, String type, BufferedImage imageSheet, int sheetX, int sheetY) {
		super(x, y, type, imageSheet, sheetX, sheetY);
		
		this.interactionBox = new Rectangle(x -4, y -4, 40, 40);
		this.interactable = true;
	}
	
	public boolean canInteract(Rectangle playerHitBox) {
		if (playerHitBox.intersects(this.interactionBox) & this.interactable == true) {
			return true;
		}
		
		return false;
	}
	
	public Rectangle getInteractionBox() {
		return this.interactionBox;
	}
}
