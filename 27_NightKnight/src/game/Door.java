package game;

import java.awt.image.BufferedImage;


public class Door extends InteractableTile {
	
	private int entryMap;
	private int exitMap;
	private String doorType;

	public Door(int x, int y, String type, BufferedImage imageSheet, int sheetX, int sheetY, int entryMap, int exitMap) {
		super(x, y, type, imageSheet, sheetX, sheetY);
		this.collision = true;
		this.entryMap = entryMap;
		this.exitMap = exitMap;
		
		if (entryMap < exitMap) {
			this.doorType = "NEXTDOOR";
		} else {
			this.doorType = "PREVDOOR";
		}
	}
	
	//logic for using a door, returns the index of its exit map.
	public int useDoor() {
		return this.exitMap;
	}
	
	public int getX() {
		return this.xPos;
	}
	
	public int getY() {
		return this.yPos;
	}
	
	public int getEntryMap() {
		return this.entryMap;
	}
	
	public String getDoorType() {
		return this.doorType;
	}
}