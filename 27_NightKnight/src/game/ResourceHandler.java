package game;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.io.FileWriter;
import java.io.PrintWriter;


public class ResourceHandler {
	
	private String[] listOfItems;
	private int[] enemyDropChance;
	private int[] chestDropChance;
	
	public ResourceHandler() {
		
		//these lists are for all items in the game and their chances of dropping for enemies and from chests, random item generation should 
		//automatically update based on these, the numbers should represent an integer percentage value i.e. 10 = 10%, total percentage across 
		//the array should add to 100%, values can be left as 0% chance as long as the remaining numbers add to 100%.
		this.listOfItems = new String[] {"SWORD", "SPEAR", "AXE", "BOW", "MAGICSTAFF", "FIRESTAFF", "SMALLHEALTH", "MEDHEALTH", "FIRSTAID"};
		this.enemyDropChance = new int[] {10, 0, 0, 10, 0, 0, 40, 20, 20};
		this.chestDropChance = new int[] {0, 20, 20, 25, 25, 10, 0, 0, 0};
	}
	
	public Item createType(int x, int y, String type) {
		switch (type) {
		case "SWORD":
			return new Sword(x, y, loadImage("resources/SwordDropped.png"), 0, 0, new ImageIcon("src/resources/SwordIcon.png"));
		case "SPEAR":
			return new Spear(x, y, loadImage("resources/SpearDropped.png"), 0, 0, new ImageIcon("src/resources/SpearIcon.png"));
		case "AXE":
			return new Axe(x, y, loadImage("resources/AxeDropped.png"), 0, 0, new ImageIcon("src/resources/AxeIcon.png"));
		case "BOW":
			return new Bow(x, y, loadImage("resources/BowDropped.png"), 0, 0, new ImageIcon("src/resources/BowIcon.png"), loadImage("resources/Arrow.png"));
		case "MAGICSTAFF":
			return new MagicStaff(x, y, loadImage("resources/MagicStaffDropped.png"), 0, 0, new ImageIcon("src/resources/MagicStaffIcon.png"), loadImage("resources/MagicBolt.png"));
		case "FIRESTAFF":
			return new FireStaff(x, y, loadImage("resources/FireStaffDropped.png"), 0, 0, new ImageIcon("src/resources/FireStaffIcon.png"), loadImage("resources/FireBall.png"));
		case "SMALLHEALTH":
			return new smallHealthPotion(x, y, loadImage("resources/SmallHealthDropped.png"), 0, 0, new ImageIcon("src/resources/SmallHealthIcon.png"));
		case "MEDHEALTH":
			return new mediumHealthPotion(x, y, loadImage("resources/MedHealthDropped.png"), 0, 0, new ImageIcon("src/resources/MedHealthIcon.png"));
		case "FIRSTAID":
			return new firstAidKit(x, y, loadImage("resources/FirstAidDropped.png"), 0, 0, new ImageIcon("src/resources/FirstAidIcon.png"));
		}
		
		return null;
	}
	
	//load an imageSheet into an array, images are stored in their row, col order i.e: position 0, 0 will be at 0 in the array
	//position 1, 0 will be at 1 in the array...
	public BufferedImage[] loadImageArray(String sheetPath) {
		
		BufferedImage loadSheet = loadImage(sheetPath);
		
		int cols = loadSheet.getWidth()/32;
		int rows = loadSheet.getHeight()/32;
		int size = cols * rows;
		
		BufferedImage[] loadArray = new BufferedImage[size];
		int x = 0;
		int y = 0;
		
		for (int i = 0; i < size; i++) {
			loadArray[i] = loadSheet.getSubimage(x, y, 32, 32);
			
			x += 32;
			
			if ((x / 32) == cols) {
				x = 0;
				y += 32;
			}
		}
		
		return loadArray;
	}
	
	public BufferedImage loadImage(String path) {
		try {
			BufferedImage loadedImage = ImageIO.read(gameWindow.class.getClassLoader().getResource(path));
			return loadedImage;
			
		} catch(IOException exception) {
			exception.printStackTrace();
			return null;
		}
	}
	
	//returns a string representing a randomly selected item, chanceNoDrop is the chance that a drop should not occur in which
	//case the function returns "NODROP", also input a boolean: true represents a chest drop, false represents an
	//enemy drop.
	public String generateRandomItem(int chanceNoDrop, boolean chest) {
		
		Random randGen = new Random();
		
		int noDrop = randGen.nextInt(100);
		
		if (noDrop <= chanceNoDrop) {
			
			int chosen = randGen.nextInt(100);
			int currentRange = 0;
			
			for (int i = 0; i < listOfItems.length; i++) {
				
				if (chest) {
					currentRange += chestDropChance[i];
					
					if (chosen < currentRange & chestDropChance[i] != 0) {
						return listOfItems[i];
					}
				} else {
					currentRange += enemyDropChance[i];
					
					if (chosen < currentRange & enemyDropChance[i] != 0) {
						return listOfItems[i];
					}
				}
			}
		}
		
		return "NODROP";
	}
	
	//returns a string array where pos 0 is the players name and pos 1 is their score, the score is stored as
	//four character numbers with the first two representing mins and the second secs.
	public String[] getHighScore(int scoreNum) throws IOException {
		
		int foundNum = 0;
		String name = "";
		char score1000 = '0';
		char score100 = '0';
		char score10 = '0';
		char score1 = '0';
		String totalScore = "";
		String[] toReturn = new String[2];
		
		File highScores = new File("src/resources/HighScores.txt");
		
		BufferedReader br = new BufferedReader(new FileReader(highScores));
		
		String line = br.readLine();
		while (line != null & foundNum != scoreNum) {
			
			name = "";
			if (line != null) {
				 for (int i = 0; i < line.length(); i++) {
					 if (line.charAt(i) == '#') {
						 foundNum = line.charAt(i+1) - '0';
					 }
					 
					 if (line.charAt(i) == '$') {
						 
						 int j = i +1;
						 
						 while (line.charAt(j) != ';') {
							 name += line.charAt(j);
							 j++;
						 }
					 }
					 
					 if (line.charAt(i) == '&') {
						 
						 score1000 = line.charAt(i +1);
						 score100 = line.charAt(i +2);
						 score10 = line.charAt(i +3);
						 score1 = line.charAt(i +4);
					 }
				 }
			}
			line = br.readLine();
		} 
		
		br.close();
		
		if (foundNum == 0 || foundNum != scoreNum) {
			toReturn[0] = "NOSCORE";
			toReturn[1] = "0000";
			return toReturn;
		}
		
		totalScore += score1000;
		totalScore += score100;
		totalScore += score10;
		totalScore += score1;
		 
		toReturn[0] = name;
		toReturn[1] = totalScore;
		return toReturn;
	}
	
	public void setHighScore(int position, String value, String name) throws IOException {
		
		String currentName1 = getHighScore(1)[0];
		String currentName2 = getHighScore(2)[0];
		String currentName3 = getHighScore(3)[0];
		
		String currentValue1 = getHighScore(1)[1];
		String currentValue2 = getHighScore(2)[1];
		String currentValue3 = getHighScore(3)[1];
		
		
		FileWriter writer = new FileWriter("src/resources/HighScores.txt" , false);
		PrintWriter print = new PrintWriter(writer);
		
		switch (position) {
		case 1:
			print.printf("#1$%s;&%s%n", name, value);
			print.printf("#2$%s;&%s%n", currentName2, currentValue2);
			print.printf("#3$%s;&%s%n", currentName3, currentValue3);
			break;
		case 2:
			print.printf("#1$%s;&%s%n", currentName1, currentValue1);
			print.printf("#2$%s;&%s%n", name, value);
			print.printf("#3$%s;&%s%n", currentName3, currentValue3);
			break;
		case 3:
			print.printf("#1$%s;&%s%n", currentName1, currentValue1);
			print.printf("#2$%s;&%s%n", currentName2, currentValue2);
			print.printf("#3$%s;&%s%n", name, value);
			break;
		}
		
		print.close();
	}
	
	public String[] getItemList() {
		return this.listOfItems;
	}
}
