package game;

import game.gameWindow.gamePanel;


public class CameraHandler {

	private double xAxis;
	private double yAxis;
	private int windowWidth;
	private int windowHeight;
	private gamePanel GPanel;
	
	public CameraHandler(gamePanel GPanel, float xAxis, float yAxis) {
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		this.windowWidth = GPanel.getWidth();
		this.windowHeight = GPanel.getHeight();
		this.GPanel = GPanel;
	}
	
	//translate from 0 to the players current position minus half the window size, subtract off the previous 
	//translation so we don't constantly increase the Axis values, if the translation would result in tiles outside 
	//the map border being rendered do not translate any further.
	public void update(double xZoom, Player player, Map map) {
		this.windowWidth = GPanel.getWidth();
		this.windowHeight = GPanel.getHeight();
		
		xAxis += (player.getX() - windowWidth/2 - xAxis); 
		yAxis += (player.getY() - windowHeight/2 - yAxis);
		
		//Only adjust the axis if the camera is within the map bounds.
		if (xAxis < 0) {
			xAxis = 0;
		}
		if (xAxis > map.getWidth() - windowWidth/xZoom) {
			xAxis = map.getWidth() - windowWidth/xZoom; //Full width of map - game window width.
		}
		
		if (yAxis < 0) {
			yAxis = 0; 
		}
		if (yAxis > map.getHeight() - windowHeight/xZoom) {
			yAxis = map.getHeight() - windowHeight/xZoom; //Full height of map - game window height.
		}
	
	}
	
	public double getxAxis() {
		return xAxis;
	}

	public double getyAxis() {
		return yAxis;
	}
}