package game;

public class TileType {
	
	private int Red;
	private int Green;
	private int Blue;
	private String Type;
	private int xPos;
	private int yPos;
	
	public TileType(int Red, int Green, int Blue, String type, int x, int y) {
		this.Red = Red;
		this.Green = Green;
		this.Blue = Blue;
		this.Type = type;
		this.xPos = x;
		this.yPos = y;
	}
	
	public int getXPos() {
		return this.xPos;
	}
	
	public int getYPos() {
		return this.yPos;
	}
	
	public int getRed() {
		return this.Red;
	}
	
	public int getGreen() {
		return this.Green;
	}
	
	public int getBlue() {
		return this.Blue;
	}
	
	public String getType() {
		return this.Type;
	}
}