package pt.iul.poo.firefight.starterpack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import pt.iul.ista.poo.gui.ImageTile;

public class Score {
	private int scoreValue = 0;
	private double multiplier = 5;
	public static final int PINE_VALUE = 25;
	public static final int EUCALIPTUS_VALUE = 50;
	public static final int GRASS_VALUE = 100;
	public GameEngine ge = GameEngine.getInstance();
	
	
	public Score() {
		this.scoreValue = 0;
	}
	
	private boolean checkArgs(int value) {
		if(value < 0) {
			return false;
		}else return true;
	}
	
	
	public void givePoints(ImageTile image) {
		if(image instanceof Pine) {
			scoreValue += multiplier * PINE_VALUE;
		}else if(image instanceof Eucaliptus) {
			scoreValue += multiplier * EUCALIPTUS_VALUE;	
		}else if(image instanceof Grass) {
			scoreValue += multiplier * GRASS_VALUE;
		}
	}
	
	
	public void setInitValue(ImageTile image) {
		if(image instanceof Pine) {
			scoreValue += PINE_VALUE;
		}else if(image instanceof Eucaliptus) {
			scoreValue += EUCALIPTUS_VALUE;	
		}else if(image instanceof Grass) {
			scoreValue += GRASS_VALUE;
		}
	}
	
	public void decreaseValue(ImageTile image) {
		if(image instanceof Pine) {
			int newScore = this.scoreValue - PINE_VALUE;
			if(checkArgs(newScore)) {
				scoreValue -= PINE_VALUE;
			}else this.scoreValue = 0;
		}else if(image instanceof Eucaliptus) {
			int newScore = this.scoreValue - EUCALIPTUS_VALUE;
			if(checkArgs(newScore)) {
				scoreValue -= EUCALIPTUS_VALUE;
			}else this.scoreValue = 0;
			
		}else if(image instanceof Grass) {
			int newScore = this.scoreValue - GRASS_VALUE;
			if(checkArgs(newScore)) {
				scoreValue -= GRASS_VALUE;
			}else this.scoreValue = 0;
		}
		
	}
	
	
	
	
	public void setScoreValue(int scoreValue) {
		if(this.scoreValue + scoreValue < 0)
			this.scoreValue = 0;
		this.scoreValue += scoreValue;
	}

	public int getScoreValue() {
		return scoreValue;
	}
	
	
	//TODO ver se já existir o ficheiro, ver se o score anterior é maior ou menor 
	public void saveToFile(String nomePlayer) {
		int levelNumber = ge.getLevelNumber();
		String levelName = "level" + levelNumber;
		File ficheiro = new File(levelName + "_" + nomePlayer + ".txt");
	
		try {
			System.out.println("ESTOU A CRIAR O FICHEIRO " + ficheiro);
			PrintWriter pw = new PrintWriter("score/" + ficheiro);
			pw.println("Level name: " + levelName);
			pw.println("Player name:" + nomePlayer);
			pw.println("Score: " + scoreValue);
		
			pw.close();
		} catch (FileNotFoundException e) {
			System.err.println("Erro a escrever");
		}
		
		
	}
	
	
	
	
	


}
