package game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;


public class InputHandler extends KeyAdapter{
	
	private Player player;
	private gameWindow game;
	
	public InputHandler(Player player, gameWindow game) {
		this.player = player;
		this.game = game;
	}
	
	public void keyPressed(KeyEvent event) {
		int key = event.getKeyCode();
		
		switch(key) {
		case KeyEvent.VK_W:
			player.keyPress("UP", 0);
			
			try {
				game.keyPress("UP");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case KeyEvent.VK_S:
			player.keyPress("DOWN", 0);
			
			try {
				game.keyPress("DOWN");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case KeyEvent.VK_A:
			player.keyPress("LEFT", 0);
			break;
		case KeyEvent.VK_D:
			player.keyPress("RIGHT", 0);
			break;
		case KeyEvent.VK_E:
			player.keyPress("INTERACT", 0);
			break;
		case KeyEvent.VK_SPACE:
			player.keyPress("ACTION", 0);
			break;
		case KeyEvent.VK_1:
			player.keyPress("EQUIP", 0);
			break;
		case KeyEvent.VK_2:
			player.keyPress("EQUIP", 1);
			break;
		case KeyEvent.VK_3:
			player.keyPress("EQUIP", 2);
			break;
		case KeyEvent.VK_4:
			player.keyPress("EQUIP", 3);
			break;
		case KeyEvent.VK_5:
			player.keyPress("EQUIP", 4);
			break;
		case KeyEvent.VK_6:
			player.keyPress("EQUIP", 5);
			break;
		case KeyEvent.VK_ENTER:
			
			if (game.getGameState() == "NEWSCORE") {
				try {
					game.getMenuHandler().keyPress();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				try {
					game.keyPress("ENTER");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			break;
		case KeyEvent.VK_P:
			try {
				game.keyPress("PAUSE");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case KeyEvent.VK_ESCAPE:
			try {
				game.keyPress("ESCAPE");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case KeyEvent.VK_PAGE_DOWN:
			try {
				game.keyPress("PGDOWN");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		}
	}
	
	public void keyReleased(KeyEvent event) {
		int key = event.getKeyCode();
		
		switch(key) {
		case KeyEvent.VK_W:
			player.keyRelease("UP");
			break;
		case KeyEvent.VK_S:
			player.keyRelease("DOWN");
			break;
		case KeyEvent.VK_A:
			player.keyRelease("LEFT");
			break;
		case KeyEvent.VK_D:
			player.keyRelease("RIGHT");
			break;
		case KeyEvent.VK_E:
			player.keyRelease("INTERACT");
			break;
		case KeyEvent.VK_SPACE:
			player.keyRelease("ATTACK");
			break;
		}
	}
}
