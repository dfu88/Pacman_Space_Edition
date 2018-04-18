package model;

import java.util.ArrayList;

public class Level {
	public int lives;
	public int timeLimit;
	public int score;

	private Map currentMap;
	private ArrayList <Map> mapList;

	
	public Level() {
		mapList = new ArrayList<Map>();
	}
	
	public Map getCurrentMap() {
		return currentMap;
	}
	
	//Makes the list of maps the controller can select from
	public void makeMaps() {
		mapList.clear();
		
		Map classic = new Map();
		classic.initMap(1);
		mapList.add(classic);
		
		Map testMap = new Map();
		testMap.initMap(2);
		mapList.add(testMap);
		
		Map testMap2 = new Map();
		testMap2.initMap(1);
		mapList.add(testMap2);
		
		Map testMap3 = new Map();
		testMap3.initMap(3);
		mapList.add(testMap3);
		
		Map testMap4 = new Map();
		testMap4.initMap(6);
		mapList.add(testMap4);
		
		Map testMap5 = new Map();
		testMap5.initMap(5);
		mapList.add(testMap5);
	}
	
	/* This function initialises a level based on the inputs..
	 * Input: mode, the integer representing the current mode being played
	 * 		  levelWins, how many wins from the current set of lives
	 */
	public void initLevel(int mode, int levelWins) {
		if (levelWins == 0) {
			score = 0;
			lives = 3;
		}
		if (mode == 3) {
			timeLimit = -1;
		} else {
			timeLimit = 120 - levelWins*10; //180?
			if (timeLimit < 60) {
				timeLimit = 60;
			}
		}
		
		makeMaps();
		currentMap = mapList.get(mode);
	}
	
	public int getTimeLimit() {
		return timeLimit;
	}
	
	public void addPoints(int pointsToAdd) {
		score += pointsToAdd;
	}
	
	public void addLives(int numLives) {
		lives += numLives;
	}
	
	public void minusLives(int numLives) {
		lives -= numLives;
	}
	
	public int getScore() {
		return score;
	}

	public int getLives() {
		return lives;
	}

	public void setScore(int newScore) {
		score = newScore;
		
	}

	public void setLives(int newLives) {
		lives = newLives;
		
	}
}
