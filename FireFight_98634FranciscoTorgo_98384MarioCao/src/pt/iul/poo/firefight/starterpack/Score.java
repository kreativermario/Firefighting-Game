package pt.iul.poo.firefight.starterpack;

import java.io.*;
import java.util.*;

import pt.iul.ista.poo.gui.ImageTile;

public class Score{
	
	public static final int ABIES_VALUE = Scoreboard.ABIES.getValue();
	public static final int PINE_VALUE = Scoreboard.PINE.getValue();
	public static final int EUCALIPTUS_VALUE = Scoreboard.EUCALIPTUS.getValue();
	public static final int GRASS_VALUE = Scoreboard.GRASS.getValue();
	
	public static final String SCORE_DIR = "score/";
	public static final String SCORE_FILE_NAME = "scores.txt";
	public static final String TOP_5_SCORE_FILE_NAME = "top5_level";
	
	public static GameEngine ge = GameEngine.getInstance();
	
	private int scoreValue = 0;
	private int levelNumber;
	private String playerName;
	
	private double multiplier = 5;
	
	
	public static class ScoreComparator implements Comparator<Score>{
		@Override
		public int compare(Score s1, Score s2) {
			
			int score = s2.getScoreValue() - s1.getScoreValue();
			if(score != 0)
				return score;
			else{
				return s1.getPlayerName().compareToIgnoreCase(s2.getPlayerName());
			}	
		
		}
	
	}
	

	public Score() {
		this.scoreValue = 0;
	}
	
	
	public Score(int levelNumber, String playerName, int scoreValue) {
		this.setLevelNumber(levelNumber);
		this.setPlayerName(playerName);
		this.scoreValue = scoreValue;
	}
	
	
	private boolean checkArgs(int value) {
		if(value < 0) {
			return false;
		}else return true;
	}
	
	
	public static Score getPlayerHighscore(String name, int level) {
		
		List<Score> allScores = new ArrayList<>();
		File ficheiro = new File(SCORE_DIR + SCORE_FILE_NAME);
		try {
			Scanner sc = new Scanner(ficheiro);
			while(sc.hasNext()) {
				String[] line = sc.nextLine().split(";");
				int levelNumber = Integer.parseInt(line[0]);
				String playerName = line[1];
				int scoreValue = Integer.parseInt(line[2]);
				Score score = new Score(levelNumber, playerName, scoreValue);
				if(!highScoreExists(allScores, score)) {
					allScores.removeIf(e -> e.getPlayerName().equals(score.getPlayerName()) 
							&& e.getLevelNumber() == score.getLevelNumber() &&  e.getScoreValue() < score.getScoreValue());
					allScores.add(score);
				}
			}
			sc.close();
			
		} catch (FileNotFoundException e) {
			System.err.println("Ficheiro " + ficheiro + " não encontrado");
		}
		allScores.removeIf(e -> !e.getPlayerName().equals(name) || e.getLevelNumber() != level);
		Score score = null;
		if(!allScores.isEmpty()) {
			score = Collections.max(allScores, new ScoreComparator());
		}
		return score;

		
	}
	
	
	
	//Retorna o TOP5 de um nível dado
	public static List<Score> getTop5Score(int level){
		
		List<Score> allScores = new ArrayList<>();
		List<Score> top5 = new ArrayList<>();
		File ficheiro = new File(SCORE_DIR + SCORE_FILE_NAME);
		try {
			Scanner sc = new Scanner(ficheiro);
			while(sc.hasNext()) {
				String[] line = sc.nextLine().split(";");
				int levelNumber = Integer.parseInt(line[0]);
				String playerName = line[1];
				int scoreValue = Integer.parseInt(line[2]);
				Score score = new Score(levelNumber, playerName, scoreValue);
				if(!highScoreExists(allScores, score)) {
					allScores.removeIf(e -> e.getPlayerName().equals(score.getPlayerName()) 
							&& e.getLevelNumber() == score.getLevelNumber() &&  e.getScoreValue() < score.getScoreValue());
					allScores.add(score);
				}
			}
			sc.close();
			
		} catch (FileNotFoundException e) {
			System.err.println("Ficheiro " + ficheiro + " não encontrado");
		}
		
		//TODO Se tiver score igual, sort por alfabeto IMPLEMENTAR COMPARATOR
		allScores.sort(new ScoreComparator());
		
		allScores.removeIf(e -> e.getLevelNumber() != level);
		
		Iterator<Score> it = allScores.iterator();
		
		//Devolve só os TOP5, independemente se só existir 1 score nesse nível
		//Ou seja, não vai dar erro
		int count = 0;
		while(it.hasNext()) {
			if(count == 5)
				break;
			top5.add(it.next());
			count++;
		}
		
		
		return top5;
		
		
		
	}
	

	
	//TODO implementar comparator. 
	private static boolean highScoreExists(List<Score> list, Score compare) {
		for(Score score : list) {
			if(score.getPlayerName().equals(compare.getPlayerName()) && score.getScoreValue() >= compare.getScoreValue()
					&& score.getLevelNumber() == compare.getLevelNumber()) {
				return true;
			}		
		}
		return false;	
	}
	
	
	//Dá pontos de acordo com o tipo de burnable apagada
	public void givePoints(ImageTile image) {
		if(image instanceof Pine) {
			scoreValue += multiplier * PINE_VALUE;
		}else if(image instanceof Eucaliptus) {
			scoreValue += multiplier * EUCALIPTUS_VALUE;	
		}else if(image instanceof Grass) {
			scoreValue += multiplier * GRASS_VALUE;
		}else if(image instanceof Abies) {
			scoreValue += multiplier * ABIES_VALUE;
		}
	}
	
	
	//Inicializa os pontos por cada load do mapa
	public void setInitValue(ImageTile image) {
		if(image instanceof Pine) {
			scoreValue += PINE_VALUE;
		}else if(image instanceof Eucaliptus) {
			scoreValue += EUCALIPTUS_VALUE;	
		}else if(image instanceof Grass) {
			scoreValue += GRASS_VALUE;
		}else if(image instanceof Abies) {
			scoreValue += ABIES_VALUE;
		}
	}
	
	
	//Decresce os pontos de acordo com o tipo de burnable
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
			
		}else if(image instanceof Abies) {
			
			int newScore = this.scoreValue - ABIES_VALUE;
			if(checkArgs(newScore)) {
				scoreValue -= ABIES_VALUE;
			}else this.scoreValue = 0;
			
		}
		
	}
	
	
	//TODO ver se já existir o ficheiro, ver se o score anterior é maior ou menor 
	public void saveToFile(String nomePlayer) {
		int levelNumber = ge.getLevelNumber();
		File ficheiro = new File(SCORE_FILE_NAME);
	
		try {
			System.out.println("ESTOU A CRIAR O FICHEIRO " + ficheiro);
			PrintWriter pw = new PrintWriter(new FileWriter(SCORE_DIR + ficheiro, true));
		
			pw.println(levelNumber + ";" + nomePlayer + ";" + scoreValue);

			pw.close();
		} catch (FileNotFoundException e) {
			System.err.println("Erro a escrever");
		} catch (IOException e) {
			System.err.println("Não consegui escrever!");		}
	}
	
	
	public static void saveTop5File(List<Score> top5) {
		int levelNumber = ge.getLevelNumber();
		File ficheiro = new File(TOP_5_SCORE_FILE_NAME + levelNumber + ".txt");
		
		try {
			System.out.println("ESTOU A CRIAR O FICHEIRO " + ficheiro);
			PrintWriter pw = new PrintWriter(new FileWriter(SCORE_DIR + ficheiro));
			String str = "TOP 5 Scores for Level " + levelNumber + System.lineSeparator();
			for(int i = 1; i <= top5.size(); i++) {
				Score score = top5.get(i-1);
				str += i + ". " + score.getPlayerName() + " - " + score.getScoreValue() + System.lineSeparator();
			}
			
			
			pw.println(str);

			pw.close();
		} catch (FileNotFoundException e) {
			System.err.println("Erro a escrever");
		} catch (IOException e) {
			System.err.println("Não consegui escrever!");		}
	}
	
	
	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getLevelNumber() {
		return levelNumber;
	}

	public void setLevelNumber(int levelNumber) {
		this.levelNumber = levelNumber;
	}
	
	public void setScoreValue(int scoreValue) {
		if(this.scoreValue + scoreValue < 0)
			this.scoreValue = 0;
		this.scoreValue += scoreValue;
	}

	public int getScoreValue() {
		return scoreValue;
	}

	//TODO DEBUG
	@Override
	public String toString() {
		return "Score -> Level " + levelNumber + " | ScoreValue " + scoreValue + " | PlayerName " + playerName + System.lineSeparator();
	}

	

	
	
	
	
	
	
	
	


}
