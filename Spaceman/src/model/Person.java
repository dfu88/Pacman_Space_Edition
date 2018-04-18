package model;

public class Person {
	private String name;
	private int score;
	
	public Person(String name, String score) {
		this.name = name;
		this.score = Integer.parseInt(score);
	}
	
	public String getName() {
		return name;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	public void setScore(String score) {
		this.score = Integer.parseInt(score);
	}
}
