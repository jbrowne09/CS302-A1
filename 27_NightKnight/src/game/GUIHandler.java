package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.Border;


public class GUIHandler {
	
	private JPanel GUIPanel;
	private Player player;
	private ResourceHandler resource;
	
	private JPanel playerBox;
	private JPanel timerBox;
	private JProgressBar healthBar;
	private JPanel inventoryBar;
	private JLabel timer10mins;
	private JLabel timer1mins;
	private JLabel timer10secs;
	private JLabel timer1secs;
	private JLabel slot1;
	private JLabel slot2;
	private JLabel slot3;
	private JLabel slot4;
	private JLabel slot5;
	private JLabel slot6;
	
	private Border selectedBorder;
	private Border deselectedBorder;
	private ImageIcon emptySlot;
	private ImageIcon emptyTimer;
	
	public GUIHandler(JPanel GUIPanel, Player player, ResourceHandler resource) {
		
		this.player = player;
		this.GUIPanel = GUIPanel;
		this.resource = resource;
		this.initGUI();
	}
	
	//all GUI objects are created here and positioned here.
	public void initGUI() {
		
		playerBox = new JPanel(new GridLayout(2, 1));
		timerBox = new JPanel(new GridLayout(1, 4)); 
		
		timer10mins = new JLabel();
		timer1mins = new JLabel();
		timer10secs = new JLabel();
		timer1secs = new JLabel();
		timer10mins.setHorizontalTextPosition(JLabel.CENTER);
		timer10mins.setVerticalTextPosition(JLabel.CENTER);
		timer1mins.setHorizontalTextPosition(JLabel.CENTER);
		timer1mins.setVerticalTextPosition(JLabel.CENTER);
		timer10secs.setHorizontalTextPosition(JLabel.CENTER);
		timer10secs.setVerticalTextPosition(JLabel.CENTER);
		timer1secs.setHorizontalTextPosition(JLabel.CENTER);
		timer1secs.setVerticalTextPosition(JLabel.CENTER);
		//timer10mins.setHorizontalAlignment(JLabel.CENTER);
		//timer10mins.setVerticalAlignment(JLabel.CENTER);
		//timer1mins.setHorizontalAlignment(JLabel.CENTER);
		//timer1mins.setVerticalAlignment(JLabel.CENTER);
		//timer10secs.setHorizontalAlignment(JLabel.CENTER);
		//timer10secs.setVerticalAlignment(JLabel.CENTER);
		//timer1secs.setHorizontalAlignment(JLabel.CENTER);
		//timer1secs.setVerticalAlignment(JLabel.CENTER);
		timer10mins.setPreferredSize(new Dimension(180, 200));
		timer1mins.setPreferredSize(new Dimension(180, 200));
		timer10secs.setPreferredSize(new Dimension(180, 200));
		timer1secs.setPreferredSize(new Dimension(180, 200));
		
		healthBar = new JProgressBar(0, player.getMaxHealth());
		inventoryBar = new JPanel(new GridLayout(1, 6));
		slot1 = new invSlot();
		slot2 = new invSlot();
		slot3 = new invSlot();
		slot4 = new invSlot();
		slot5 = new invSlot();
		slot6 = new invSlot();	
		slot1.setOpaque(true);
		slot2.setOpaque(true);
		slot3.setOpaque(true);
		slot4.setOpaque(true);
		slot5.setOpaque(true);
		slot6.setOpaque(true);
		healthBar.setString(player.getHealth()+"/"+player.getMaxHealth());
		healthBar.setForeground(Color.GREEN);
		healthBar.setBackground(new Color(150, 0, 0));
		healthBar.setStringPainted(true);
		
		timerBox.add(timer10mins, 0);
		timerBox.add(timer1mins, 1);
		timerBox.add(timer10secs, 2);
		timerBox.add(timer1secs, 3);
		
		inventoryBar.add(slot1, 0);
		inventoryBar.add(slot2, 1);
		inventoryBar.add(slot3, 2);
		inventoryBar.add(slot4, 3);
		inventoryBar.add(slot5, 4);
		inventoryBar.add(slot6, 5);
		
		playerBox.add(healthBar, 0);
		playerBox.add(inventoryBar, 1);
		
		GUIPanel.add(playerBox, 0);
		GUIPanel.add(timerBox, 1);
		
		this.selectedBorder = BorderFactory.createLineBorder(Color.GREEN);
		this.deselectedBorder = BorderFactory.createLineBorder(Color.BLACK);
		this.emptySlot = new ImageIcon(resource.loadImage("resources/EmptyIcon.png"));
		this.emptyTimer = new ImageIcon(resource.loadImage("resources/TimerIcon.png"));

		timer10mins.setBorder(this.deselectedBorder);
		timer1mins.setBorder(this.deselectedBorder);
		timer10secs.setBorder(this.deselectedBorder);
		timer1secs.setBorder(this.deselectedBorder);
		
		slot1.setBorder(this.selectedBorder);
		slot2.setBorder(this.deselectedBorder);
		slot3.setBorder(this.deselectedBorder);
		slot4.setBorder(this.deselectedBorder);
		slot5.setBorder(this.deselectedBorder);
		slot6.setBorder(this.deselectedBorder);
		
		Font timerFont = new Font("arial", Font.BOLD, 44);
		Font invFont = new Font("arial", Font.BOLD, 30);
		timer10mins.setForeground(Color.WHITE);
		timer1mins.setForeground(Color.WHITE);
		timer10secs.setForeground(Color.WHITE);
		timer1secs.setForeground(Color.WHITE);
		timer10mins.setFont(timerFont);
		timer1mins.setFont(timerFont);
		timer10secs.setFont(timerFont);
		timer1secs.setFont(timerFont);
		
		slot1.setFont(invFont);
		slot2.setFont(invFont);
		slot3.setFont(invFont);
		slot4.setFont(invFont);
		slot5.setFont(invFont);
		slot6.setFont(invFont);
		
		healthBar.setFont(invFont);
		
		
		GUIPanel.setBackground(Color.BLACK);
		GUIPanel.revalidate();
	}
	
	public void update(int mins10, int mins1, int secs10, int secs1) {
		
		healthBar.setValue(player.getHealth());
		healthBar.setString(player.getHealth()+"/"+player.getMaxHealth());
		
		timer10mins.setIcon(this.emptyTimer);
		timer1mins.setIcon(this.emptyTimer);
		timer10secs.setIcon(this.emptyTimer);
		timer1secs.setIcon(this.emptyTimer);
		timer10mins.setText(Integer.toString(mins10));
		timer1mins.setText(Integer.toString(mins1));
		timer10secs.setText(Integer.toString(secs10));
		timer1secs.setText(Integer.toString(secs1));
		
		if (player.getHealth() < 25) {
			healthBar.setForeground(Color.RED);
		} else if (player.getHealth() < 75) {
			healthBar.setForeground(Color.YELLOW);
		} else {
			healthBar.setForeground(Color.GREEN);
		}
		
		
		//if a slot is selected set its border to green, otherwise set it to black.
		if (isSelected(0)) {
			slot1.setBorder(this.selectedBorder);
		} else {
			slot1.setBorder(this.deselectedBorder);
		}
		if (isSelected(1)) {
			slot2.setBorder(this.selectedBorder);
		} else {
			slot2.setBorder(this.deselectedBorder);
		}
		if (isSelected(2)) {
			slot3.setBorder(this.selectedBorder);
		} else {
			slot3.setBorder(this.deselectedBorder);
		}
		if (isSelected(3)) {
			slot4.setBorder(this.selectedBorder);
		} else {
			slot4.setBorder(this.deselectedBorder);
		}
		if (isSelected(4)) {
			slot5.setBorder(this.selectedBorder);
		} else {
			slot5.setBorder(this.deselectedBorder);
		}
		
		if (isSelected(5)) {
			slot6.setBorder(this.selectedBorder);
		} else {
			slot6.setBorder(this.deselectedBorder);
		}
		
		
		//update slot images based on the player inventory.
		Item[] playerInventory = player.getInventory();
		
		if (playerInventory[0] != null) {
			
			ImageIcon icon = playerInventory[0].getIcon();
			slot1.setIcon(icon);
			
			slot1.setText(getInvStackString(0));
		} else {
			
			slot1.setIcon(this.emptySlot);
		}
		if (playerInventory[1] != null) {
			
			ImageIcon icon = playerInventory[1].getIcon();
			slot2.setIcon(icon);
			
			slot2.setText(getInvStackString(1));
		} else {
			
			slot2.setIcon(this.emptySlot);
		}
		if (playerInventory[2] != null) {
			
			ImageIcon icon = playerInventory[2].getIcon();
			slot3.setIcon(icon);
			
			slot3.setText(getInvStackString(2));
		}  else {
			
			slot3.setIcon(this.emptySlot);
		}
		if (playerInventory[3] != null) {
			
			ImageIcon icon = playerInventory[3].getIcon();
			slot4.setIcon(icon);
			
			slot4.setText(getInvStackString(3));
		} else {
			slot4.setIcon(this.emptySlot);
		}
		if (playerInventory[4] != null) {
			
			ImageIcon icon = playerInventory[4].getIcon();
			slot5.setIcon(icon);
			
			slot5.setText(getInvStackString(4));
		} else {
			
			slot5.setIcon(this.emptySlot);
		}
		if (playerInventory[5] != null) {
			
			ImageIcon icon = playerInventory[5].getIcon();
			slot6.setIcon(icon);
			
			slot6.setText(getInvStackString(5));
		} else {
			
			slot6.setIcon(this.emptySlot);
		}
	}
	
	//modified JLabel that draws text over its image, for the item stack sizes.
	class invSlot extends JLabel {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void paintComponent(Graphics graphics) {
			super.paintComponent(graphics);
			graphics.setColor(new Color(180, 255, 255));
			graphics.drawString(this.getText(), this.getWidth()/2 - 10, 0+this.getHeight() -8);
		}
		
	}
	
	public boolean isSelected(int index) {
		
		if (player.getSelectedSlot() == index) {
			return true;
		}
		
		return false;
	}
	
	public String getInvStackString(int invLocation) {
		int slotStackSize = player.getInventoryStackSize(invLocation);
		
		if (slotStackSize > 1) {
			
			return Integer.toString(slotStackSize);
		} else {
			
			return "";
		}
	}
}
