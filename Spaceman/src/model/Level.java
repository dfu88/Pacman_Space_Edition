package model;

import model.Map;

public class Level {
	public int lives;
	public int timeRemaining;
	public int score;
	
	public Map currentMap;
	//public Character pac;
	
	public void initLevel(Map model) {
		lives = 3;
		timeRemaining = 120; //180?
		score = 0;
		currentMap = model;
		//command to generate visuals for map goes here
	}
	
}
