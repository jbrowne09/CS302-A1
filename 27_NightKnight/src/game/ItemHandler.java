package game;

import java.util.List;

public class ItemHandler {

	private Map map;
	private Player player;
	
	public ItemHandler(Map map, Player player) {
		this.map = map;
		this.player = player;
	}
	
	public String update(int frame, Map currentMap, Player player) {
		
		this.map = currentMap;
		this.player = player;
		
		List<Item> droppedItems = map.getDroppedItems();
		Item interactItem = null;
		int droppedIndex = 0;
		String statusText = "NULL";
		
		for(int i = 0; i < droppedItems.size(); i++) {
			
			Item currentItem = droppedItems.get(i);
			
			//update dropped items.
			currentItem.update(frame, currentMap, player);
			
			if(currentItem.withinRange(player.getHitBox())) {
				interactItem = currentItem;
				droppedIndex = i;
				statusText = ("Pick Up "+interactItem.getItemName());
				break;
			}
		}
		
		
		if (interactItem != null) {
			
			if (player.getInteractionQueued()) {
				
				this.pickUpItem(interactItem, player.getSelectedSlot());
				map.deleteDroppedItem(droppedIndex);
				player.resetInteractionQueued();
			}
		}
		
		return statusText;
	}
	
	//will add the inputted item to the players inventory, checks if the item already exists in the players inventory
	//and is stackable and will add it to a stack if possible, otherwise it will check the players current selected
	//slot and drop any existing item.
	public void pickUpItem(Item item, int selectedSlot) {
		
		Item[] playerInventory = player.getInventory();
		
		for (int i = 0; i < playerInventory.length; i++) {
			if (playerInventory[i] == null) {
				
				player.setInventory(item,  i);
				player.addInventory(i);
				return;
			} else if ((item.getItemName() == playerInventory[i].getItemName()) & (player.getInventoryStackSize(i) < item.getMaxStackSize())) {
				
				player.addInventory(i);
				return;
			}
		}
		
		Item itemToDrop = player.getInventoryItem(selectedSlot);
		itemToDrop.setX(player.getX());
		itemToDrop.setY(player.getY());
		
		map.addDroppedItem(itemToDrop);
		player.setInventory(item, selectedSlot);
	} 
}
