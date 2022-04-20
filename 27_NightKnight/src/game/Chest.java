package game;


import java.awt.image.BufferedImage;


public class Chest extends InteractableTile {

	protected BufferedImage openImage;
	
	public Chest(int x, int y, String type, BufferedImage imageSheet, int sheetX, int sheetY) {
		super(x, y, type, imageSheet, sheetX, sheetY);
		this.collision = true;
		this.openImage = imageSheet.getSubimage(32, 0, 32, 32);
	}
	
	public Item openChest(ResourceHandler resource) {
			
		this.interactable = false;
		this.renderedImage = openImage;
		
		String chosenItem = resource.generateRandomItem(100,  true);
		
		return resource.createType(this.getX(), this.getY() +32, chosenItem);
	}
}