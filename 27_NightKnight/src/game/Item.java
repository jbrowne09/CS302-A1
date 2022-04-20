package game;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;


public abstract class Item extends GameObject {
	
	protected String itemType;
	protected String itemName;
	protected boolean consumable;
	protected boolean dropped;
	protected ImageIcon inventoryImage;
	protected int maxStackSize;
	
	public Item(int x, int y, BufferedImage imageSheet, int sheetX, int sheetY, ImageIcon inventoryImage) {
		super(x, y, "ITEM", imageSheet, sheetX, sheetY, 32, 32);
		this.dropped = false;
		this.inventoryImage = inventoryImage;
	}
	
	//if specified playerHitBox intersects with the items HitBox it is within range to be picked up
	public boolean withinRange(Rectangle playerHitBox) {
		
		if (playerHitBox.intersects(this.HitBox)) {
			return true;
		}
		
		return false;
	}
	
	public boolean isDropped() {
		return this.dropped;
	}
	
	public String getItemType() {
		return this.itemType;
	}
	
	public boolean isConsumable() {
		return this.consumable;
	}
	
	public ImageIcon getIcon() {
		return this.inventoryImage;
	}
	
	public int getMaxStackSize() {
		return this.maxStackSize;
	}
	
	public String getItemName() {
		return this.itemName;
	}
}
