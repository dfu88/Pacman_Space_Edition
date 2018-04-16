package model;

public class Person {
	private String name;
	private String gameMode;
	private int score;
	
	public Person(String name, String gameMode, int score) {
		this.name = name;
		this.gameMode = gameMode;
		this.score = score;
	}
	
	public String getName() {
		return name;
	}
	
	public String getGameMode() {
		return gameMode;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setGameMode(String gameMode) {
		this.gameMode = gameMode;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
}
