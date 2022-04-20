package game;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class soundHandler {

	Clip sound;
	
	private File chestOpen;
	private File playerDamaged;
	private File meleeAttack;
	private File ramHit;
	private File bossHit;
	private File heal;
	private File win;
	private File defeat;
	
	public soundHandler() {
		this.chestOpen = new File("src/resources/Sounds/ChestSound.wav");
		this.playerDamaged = new File("src/resources/Sounds/PlayerDamage.wav");
		this.meleeAttack = new File("src/resources/Sounds/MeleeSound.wav");
		this.ramHit = new File("src/resources/Sounds/RamSound.wav");
		this.bossHit = new File("src/resources/Sounds/BossSound.wav");
		this.heal = new File("src/resources/Sounds/HealSound.wav");
		this.win = new File("src/resources/Sounds/WinSound.wav");
		this.defeat = new File("src/resources/Sounds/LossSound.wav");
		
	}
	
	public void setSound(File file)  throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		File loadFile = file;
		AudioInputStream soundEffect = AudioSystem.getAudioInputStream(loadFile);
		
		this.sound = AudioSystem.getClip();
		this.sound.open(soundEffect);
	}
	
	public void playSound() {
		this.sound.setFramePosition(0);
		this.sound.start();
	}
	
	//public void playChestOpen() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
	//	setSound(chestOpen);
	//	playSound();
	//}
	
	//public void playPlayerDamage() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
	//	setSound(playerDamaged);
	//	playSound();
	//}
	
	//public void playMeleeAttack() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
	//	setSound(meleeAttack);
	//	playSound();
	//}
	
	//public void playRamHit() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
	//	setSound(ramHit);
	//	playSound();
	//}
	
	//public void playBossHit() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
	//	setSound(bossHit);
	//	playSound();
	//}
	
	//public void playHeal() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
	//	setSound(heal);
	//	playSound();
	//}
	
	public void playWin() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		setSound(win);
		playSound();
	}
	
	public void playDefeat() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		setSound(defeat);
		playSound();
	}
	
}
