package game;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;


public abstract class GameObject {
	
	protected String type;
	protected int xPos;
	protected int yPos;
	protected int width;
	protected int height;
	protected Rectangle HitBox;
	protected BufferedImage renderedImage;
	
	public GameObject(int x, int y, String type, BufferedImage imageSheet,  int sheetX, int sheetY, int width, int height) {
		this.xPos = x;
		this.yPos = y;
		this.width = width;
		this.height = height;
		this.type = type;
		this.renderedImage = imageSheet.getSubimage(sheetX*32, sheetY*32, width, height);
		this.HitBox = new Rectangle(xPos, yPos, width, height);
	}
	
	public void update(int frame, Map currentMap, Player player) {
		this.HitBox.x = this.xPos;
		this.HitBox.y = this.yPos;
	}
	
	public BufferedImage getRenderedImage() {
		return this.renderedImage;
	}
	
	public int getX() {
		return this.xPos;
	}
	
	public void setX(int x) {
		this.xPos = x;
	}
	
	public int getY() {
		return this.yPos;
	}
	
	public void setY(int y) {
		this.yPos = y;
	}
	
	public String getType() {
		return this.type;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public Rectangle getHitBox() {
		return this.HitBox;
	}
}
