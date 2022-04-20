package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import game.gameWindow.gamePanel;


public class MenuHandler {
	
	private String scoreName1;
	private String scoreName2;
	private String scoreName3;
	
	private String score1;
	private String score2;
	private String score3;
	private int firstScore;
	private int secondScore;
	private int thirdScore;
	private String previousState;
	
	private int currentScore;
	private int playerScorePosition;
	
	private ResourceHandler resource;
	private soundHandler sound;
	private gameWindow mainGame;
	private JPanel mainPanel;
	private JPanel leftPanel;
	private JPanel rightPanel;
	
	private JLabel titleImage;
	private JLabel playButton;
	private JLabel scoreButton;
	private JLabel exitButton;
	private JLabel confirmText;
	private JLabel scoreLabel;
	private JTextField nameEntry;
	private JLabel invalidText;
	
	private ImageIcon play;
	private ImageIcon score;
	private ImageIcon exit;
	
	private ImageIcon playSelected;
	private ImageIcon scoreSelected;
	private ImageIcon exitSelected;
	
	public MenuHandler(gameWindow mainGame, ResourceHandler resource, soundHandler sound) {
		
		this.resource = resource;
		this.mainGame = mainGame;
		this.sound = sound;
		
		//basic components used as a template for all menus.
		this.mainPanel = new JPanel(new BorderLayout(0, 0));
		this.mainPanel.setPreferredSize(new Dimension(1000, 900));
		this.mainPanel.setBackground(new Color(31, 37, 53));
		this.mainPanel.setOpaque(true);
		this.leftPanel = new JPanel();
		this.rightPanel = new JPanel();
		this.leftPanel.setPreferredSize(new Dimension(220, 900));
		this.rightPanel.setPreferredSize(new Dimension(220, 900));
		this.leftPanel.setBackground(new Color(31, 37, 53));
		this.rightPanel.setBackground(new Color(31, 37, 53));
		this.leftPanel.setOpaque(true);
		this.rightPanel.setOpaque(true);
		
		this.titleImage = new JLabel();
		this.titleImage.setPreferredSize(new Dimension(1000, 450));
		this.playButton = new JLabel();
		this.scoreButton = new JLabel();
		this.exitButton = new JLabel();
		this.confirmText = new JLabel();
		this.scoreLabel = new JLabel();
		this.nameEntry = new JTextField();
		this.invalidText = new JLabel();
		this.titleImage.setBackground(new Color(31, 37, 53));
		this.playButton.setBackground(new Color(31, 37, 53));
		this.scoreButton.setBackground(new Color(31, 37, 53));
		this.exitButton.setBackground(new Color(31, 37, 53));
		this.confirmText.setBackground(new Color(31, 37, 53));
		this.invalidText.setBackground(new Color(31, 37, 53));
		this.scoreLabel.setBackground(new Color(31, 37, 53));
		this.titleImage.setOpaque(true);
		this.playButton.setOpaque(true);
		this.scoreButton.setOpaque(true);
		this.exitButton.setOpaque(true);
		this.confirmText.setOpaque(true);
		this.scoreLabel.setOpaque(true);
		this.nameEntry.setOpaque(true);
		this.scoreLabel.setOpaque(true);
		this.titleImage.setIcon(new ImageIcon(resource.loadImage("resources/mainTitle.png")));
		this.playButton.setIcon(new ImageIcon(resource.loadImage("resources/playButton.png")));
		this.scoreButton.setIcon(new ImageIcon(resource.loadImage("resources/scoreButton.png")));
		this.exitButton.setIcon(new ImageIcon(resource.loadImage("resources/exitButton.png")));
		this.confirmText.setText("PRESS ENTER TO CONFIRM");
		
		titleImage.setHorizontalAlignment(JLabel.CENTER);
		titleImage.setVerticalAlignment(JLabel.CENTER);
		playButton.setHorizontalAlignment(JLabel.CENTER);
		playButton.setVerticalAlignment(JLabel.CENTER);
		scoreButton.setHorizontalAlignment(JLabel.CENTER);
		scoreButton.setVerticalAlignment(JLabel.CENTER);
		exitButton.setHorizontalAlignment(JLabel.CENTER);
		exitButton.setVerticalAlignment(JLabel.CENTER);
		confirmText.setHorizontalAlignment(JLabel.CENTER);
		confirmText.setVerticalAlignment(JLabel.CENTER);
		scoreLabel.setVerticalAlignment(JLabel.CENTER);
		scoreLabel.setHorizontalAlignment(JLabel.CENTER);
		invalidText.setVerticalAlignment(JLabel.CENTER);
		invalidText.setHorizontalAlignment(JLabel.CENTER);	
		
		mainGame.add(this.mainPanel, BorderLayout.CENTER);
		mainGame.add(this.leftPanel, BorderLayout.WEST);
		mainGame.add(this.rightPanel, BorderLayout.EAST);
		mainGame.setBackground(new Color(31, 37, 53));
		
		mainGame.revalidate();
		mainGame.pack();
		mainGame.setLocationRelativeTo(null);
		
		playSelected = new ImageIcon(resource.loadImage("resources/playButtonSelected.png"));
		scoreSelected = new ImageIcon(resource.loadImage("resources/scoreButtonSelected.png"));
		exitSelected = new ImageIcon(resource.loadImage("resources/exitButtonSelected.png"));
		play = new ImageIcon(resource.loadImage("resources/playButton.png"));
		score = new ImageIcon(resource.loadImage("resources/scoreButton.png"));
		exit = new ImageIcon(resource.loadImage("resources/exitButton.png"));
		
		this.playerScorePosition = 4;
		this.currentScore = 0;
		this.previousState = mainGame.getGameState();
		nameEntry.addKeyListener(mainGame.getKeyListeners()[0]);
	}
	
	
	public void changeTo(String gameState, gamePanel GPanel, JPanel GUIPanel, String prevState) throws IOException {
		
		this.previousState = prevState;
		
		switch(gameState) {
		case "MAINMENU":
			
			JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 0, 25));
			buttonPanel.setPreferredSize(new Dimension(1000, 450));
			buttonPanel.setBackground(new Color(31, 37, 53));
			buttonPanel.setOpaque(true);
			
			playButton.setBackground(new Color(31, 37, 53));
			scoreButton.setBackground(new Color(31, 37, 53));
			exitButton.setBackground(new Color(31, 37, 53));
			
			buttonPanel.add(this.playButton, 0);
			buttonPanel.add(this.scoreButton, 1);
			buttonPanel.add(this.exitButton, 2);
			
			this.mainPanel.removeAll();
			this.mainGame.getContentPane().removeAll();
			
			this.titleImage.setIcon(new ImageIcon(resource.loadImage("resources/mainTitle.png")));
			
			this.mainPanel.add(titleImage, BorderLayout.CENTER);
			this.mainPanel.add(buttonPanel, BorderLayout.PAGE_END);
			
			
			mainGame.add(this.mainPanel, BorderLayout.CENTER);
			mainGame.add(this.leftPanel, BorderLayout.WEST);
			mainGame.add(this.rightPanel, BorderLayout.EAST);
			
			this.mainGame.resetNextGameState();
			this.mainGame.setNextGameState("GAME");
			this.mainGame.setNextGameState("HIGHSCORES");
			this.mainGame.setNextGameState("EXIT");
			
			break;
			
		case "HIGHSCORES":
			
			this.mainGame.getContentPane().removeAll();
			this.mainPanel.removeAll();
			
			this.mainGame.add(this.mainPanel, BorderLayout.CENTER);
			this.mainGame.add(this.leftPanel, BorderLayout.WEST);
			this.mainGame.add(this.rightPanel, BorderLayout.EAST);
			
			JPanel scoresPanel = new JPanel(new GridLayout(3, 2, 0, 25));
			scoresPanel.setPreferredSize(new Dimension(1000, 300));
			scoresPanel.setBackground(new Color(31, 37, 53));
			scoresPanel.setOpaque(true);
			
			//assign high scores to various labels.
			this.loadHighScores();
			
			Font font = new Font("arial", Font.BOLD, 40);
			
			JLabel labelName1 = new JLabel(this.scoreName1);
			JLabel labelName2 = new JLabel(this.scoreName2);
			JLabel labelName3 = new JLabel(this.scoreName3);
			
			String stringScore1 = ""+this.score1.charAt(0)+this.score1.charAt(1)+":"+this.score1.charAt(2)+this.score1.charAt(3);
			String stringScore2 = ""+this.score2.charAt(0)+this.score2.charAt(1)+":"+this.score2.charAt(2)+this.score2.charAt(3);
			String stringScore3 = ""+this.score3.charAt(0)+this.score3.charAt(1)+":"+this.score3.charAt(2)+this.score3.charAt(3);
			JLabel score1 = new JLabel(stringScore1);
			JLabel score2 = new JLabel(stringScore2);
			JLabel score3 = new JLabel(stringScore3);
			
			labelName1.setForeground(Color.WHITE);
			labelName2.setForeground(Color.WHITE);
			labelName3.setForeground(Color.WHITE);
			score1.setForeground(Color.WHITE);
			score2.setForeground(Color.WHITE);
			score3.setForeground(Color.WHITE);
			
			labelName1.setFont(font);
			labelName2.setFont(font);
			labelName3.setFont(font);
			score1.setFont(font);
			score2.setFont(font);
			score3.setFont(font);
			
			labelName1.setHorizontalAlignment(JLabel.CENTER);
			labelName1.setVerticalAlignment(JLabel.CENTER);
			labelName2.setHorizontalAlignment(JLabel.CENTER);
			labelName2.setVerticalAlignment(JLabel.CENTER);
			labelName3.setHorizontalAlignment(JLabel.CENTER);
			labelName3.setVerticalAlignment(JLabel.CENTER);
			score1.setHorizontalAlignment(JLabel.CENTER);
			score1.setVerticalAlignment(JLabel.CENTER);
			score2.setHorizontalAlignment(JLabel.CENTER);
			score2.setVerticalAlignment(JLabel.CENTER);
			score3.setHorizontalAlignment(JLabel.CENTER);
			score3.setVerticalAlignment(JLabel.CENTER);
			
			exitButton.setBackground(new Color(31, 37, 53));
			
			scoresPanel.add(labelName1);
			scoresPanel.add(score1);
			scoresPanel.add(labelName2);
			scoresPanel.add(score2);
			scoresPanel.add(labelName3);
			scoresPanel.add(score3);
			
			this.titleImage.setIcon(new ImageIcon(resource.loadImage("resources/scoresTitle.png")));
			this.mainPanel.add(this.titleImage, BorderLayout.PAGE_START);
			this.mainPanel.add(scoresPanel, BorderLayout.CENTER);
			this.mainPanel.add(exitButton, BorderLayout.PAGE_END);
			
			this.mainGame.add(mainPanel, BorderLayout.CENTER);
			this.mainGame.add(leftPanel, BorderLayout.WEST);
			this.mainGame.add(rightPanel, BorderLayout.EAST);
			
			this.mainGame.resetNextGameState();
			
			switch (prevState) {
			case "MAINMENU":
				this.mainGame.setNextGameState("MAINMENU");
				break;
			case "DIED":
				this.mainGame.setNextGameState("DIED");
				break;
			case "WIN":
				this.mainGame.setNextGameState("WIN");
				break;
			default:
				this.mainGame.setNextGameState("EXIT");
				break;
			}
			
			break;
		case "GAME":
			
			this.mainGame.getContentPane().removeAll();
			
			this.mainGame.add(GPanel, BorderLayout.CENTER);
			this.mainGame.add(GUIPanel, BorderLayout.PAGE_END);
			
			this.mainGame.resetNextGameState();
			
			break;
		case "DIED":
			
			try {
				sound.playDefeat();
			} catch (UnsupportedAudioFileException | LineUnavailableException e1) {
				e1.printStackTrace();
			}
			
			this.mainPanel.removeAll();
			this.mainGame.getContentPane().removeAll();
			
			this.titleImage.setIcon(new ImageIcon(resource.loadImage("resources/diedTitle.png")));
			
			JPanel buttonPanelDead = new JPanel(new GridLayout(4, 1, 0, 25));
			buttonPanelDead.setPreferredSize(new Dimension(1000, 450));
			buttonPanelDead.setBackground(new Color(31, 37, 53));
			buttonPanelDead.setOpaque(true);
			
			scoreButton.setBackground(new Color(31, 37, 53));
			exitButton.setBackground(new Color(31, 37, 53));

			buttonPanelDead.add(scoreLabel);
			buttonPanelDead.add(this.scoreButton);
			buttonPanelDead.add(this.exitButton);
			
			this.mainPanel.add(titleImage, BorderLayout.CENTER);
			this.mainPanel.add(buttonPanelDead, BorderLayout.PAGE_END);
			
			mainGame.add(this.mainPanel, BorderLayout.CENTER);
			mainGame.add(this.leftPanel, BorderLayout.WEST);
			mainGame.add(this.rightPanel, BorderLayout.EAST);
			
			this.mainGame.resetNextGameState();
			this.mainGame.setNextGameState("HIGHSCORES");
			this.mainGame.setNextGameState("EXIT");
			
			break;
		case "WIN":
			
			try {
				sound.playWin();
			} catch (UnsupportedAudioFileException | LineUnavailableException e) {
				e.printStackTrace();
			}
			
			this.mainGame.getContentPane().removeAll();
			this.mainPanel.removeAll();
			
			this.titleImage.setIcon(new ImageIcon(resource.loadImage("resources/winTitle.png")));
			
			JPanel buttonPanelWin = new JPanel(new GridLayout(4, 1, 0, 25));
			buttonPanelWin.setPreferredSize(new Dimension(1000, 450));
			buttonPanelWin.setBackground(new Color(31, 37, 53));
			buttonPanelWin.setOpaque(true);
			
			scoreButton.setBackground(new Color(31, 37, 53));
			exitButton.setBackground(new Color(31, 37, 53));
			
			currentScore = mainGame.getScore();
			String stringCurrentScore = Integer.toString(currentScore);
			
			if(this.currentScore < 10) {
				stringCurrentScore = "000"+stringCurrentScore;
			} else if (this.currentScore < 100) {
				stringCurrentScore = "00"+stringCurrentScore;
			} else if (this.currentScore < 1000) {
				stringCurrentScore = "0"+stringCurrentScore;
			}
			
			Font scoreFont = new Font("arial", Font.BOLD, 44);
			scoreLabel.setForeground(Color.WHITE);
			scoreLabel.setText("Your Score: "+""+stringCurrentScore.charAt(0)+stringCurrentScore.charAt(1)+":"+
					stringCurrentScore.charAt(2)+stringCurrentScore.charAt(3));
			scoreLabel.setFont(scoreFont);
			
			buttonPanelWin.add(scoreLabel);
			buttonPanelWin.add(this.scoreButton);
			buttonPanelWin.add(this.exitButton);
			
			this.mainPanel.add(titleImage, BorderLayout.CENTER);
			this.mainPanel.add(buttonPanelWin, BorderLayout.PAGE_END);
			
			mainGame.add(this.mainPanel, BorderLayout.CENTER);
			mainGame.add(this.leftPanel, BorderLayout.WEST);
			mainGame.add(this.rightPanel, BorderLayout.EAST);
			
			this.mainGame.resetNextGameState();
			this.mainGame.setNextGameState("HIGHSCORES");
			this.mainGame.setNextGameState("EXIT");
			
			this.loadHighScores();
			
			
			firstScore = Integer.parseInt(this.score1);
			secondScore = Integer.parseInt(this.score2);
			thirdScore = Integer.parseInt(this.score3);
			
			
			if (prevState != "NEWSCORE" & prevState != "WIN" & prevState != "HIGHSCORES") {
				if (currentScore < firstScore) {
					this.playerScorePosition = 1;
					mainGame.setGameState("NEWSCORE");
				} else if (currentScore < secondScore) {
					this.playerScorePosition = 2;
					mainGame.setGameState("NEWSCORE");
				} else if (currentScore < thirdScore) {
					this.playerScorePosition = 3;
					mainGame.setGameState("NEWSCORE");
				} else {
					this.playerScorePosition = 4;
				}
			}
			
			break;
		case "NEWSCORE":
			
			this.mainGame.getContentPane().removeAll();
			this.mainPanel.removeAll();
			
			this.titleImage.setIcon(new ImageIcon(resource.loadImage("resources/newScoreTitle.png")));
			
			JPanel buttonPanelNew = new JPanel(new GridLayout(4, 1, 0, 25));
			buttonPanelNew.setPreferredSize(new Dimension(1000, 450));
			buttonPanelNew.setBackground(new Color(31, 37, 53));
			buttonPanelNew.setOpaque(true);
			
			Font confirmFont = new Font("arial", Font.BOLD, 44);
			scoreLabel.setForeground(Color.WHITE);
			confirmText.setFont(confirmFont);
			confirmText.setForeground(Color.WHITE);
			
			Font invalidFont = new Font("arial", Font.BOLD, 20);
			invalidText.setFont(invalidFont);
			invalidText.setForeground(Color.RED);
			
			buttonPanelNew.add(this.nameEntry);
			buttonPanelNew.add(this.confirmText);
			buttonPanelNew.add(this.invalidText);
			
			this.mainPanel.add(titleImage, BorderLayout.CENTER);
			this.mainPanel.add(buttonPanelNew, BorderLayout.PAGE_END);
			
			mainGame.add(this.mainPanel, BorderLayout.CENTER);
			mainGame.add(this.leftPanel, BorderLayout.WEST);
			mainGame.add(this.rightPanel, BorderLayout.EAST);
			
			this.mainGame.resetNextGameState();
			this.mainGame.setNextGameState("WIN");
			
			break;
		}
		
		mainGame.revalidate();
	}
	
	public void update(String gameState, int selectedButton) {
		
		this.playButton.setIcon(this.play);
		this.scoreButton.setIcon(this.score);
		this.exitButton.setIcon(this.exit);
		
		switch(gameState) {
		case "MAINMENU":
			
			switch (selectedButton) {
			case 0:
				this.playButton.setIcon(this.playSelected);
				break;
			case 1:
				this.scoreButton.setIcon(this.scoreSelected);
				break;
			case 2:
				this.exitButton.setIcon(this.exitSelected);
				break;
			}
			
			break;
		case "HIGHSCORES":
			
			if (selectedButton == 0) {
				this.exitButton.setIcon(this.exitSelected);
			}
			break;
		case "DIED":
			
			switch(selectedButton) {
			case 0:
				this.scoreButton.setIcon(this.scoreSelected);
				break;
			case 1:
				this.exitButton.setIcon(this.exitSelected);
			}
			
			break;
		case "WIN":
			
			switch(selectedButton) {
			case 0:
				this.scoreButton.setIcon(this.scoreSelected);
				break;
			case 1:
				this.exitButton.setIcon(this.exitSelected);
			}
			
			break;
		case "EXIT":
			System.exit(0);
		}
	}
	
	//load in the current 3 high scores from the HighScores.txt folder.
	public void loadHighScores() throws IOException {
		
		String[] Scores = new String[3];
		Scores[0] = resource.getHighScore(1)[1];
		Scores[1] = resource.getHighScore(2)[1];
		Scores[2] = resource.getHighScore(3)[1];
		
		String name1 = resource.getHighScore(1)[0];
		String name2 = resource.getHighScore(2)[0];
		String name3 = resource.getHighScore(3)[0];
		
		this.scoreName1 = name1;
		this.scoreName2 = name2;
		this.scoreName3 = name3;
		this.score1 = Scores[0];
		this.score2 = Scores[1];
		this.score3 = Scores[2];
	}
	
	public void keyPress() throws IOException {
		
		String stringScore = Integer.toString(this.currentScore);
		
		if(this.currentScore < 10) {
			stringScore = "000"+stringScore;
		} else if (this.currentScore < 100) {
			stringScore = "00"+stringScore;
		} else if (this.currentScore < 1000) {
			stringScore = "0"+stringScore;
		}
		
		if (nameEntry.getText().length() > 0 & nameEntry.getText().length() < 11) {
			mainGame.setGameState(this.previousState);
			mainGame.requestFocus();
			
			resource.setHighScore(this.playerScorePosition, stringScore, nameEntry.getText());
		} else {
			nameEntry.setText("");
			this.invalidText.setText("Invalid name, Name must be between 1 and 10 charaters");
		}
	}
}
