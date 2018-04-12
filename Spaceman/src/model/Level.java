package model;

import java.util.ArrayList;

import view.Spaceman;



public class Level {
	//make these private maybe?
	public int lives;
	public int timeLimit;
	public int score;
	
	private int pelletsRemaining;

	private Map currentMap;
	//public Spaceman spaceman;

	private ArrayList <Map> mapList;

	
	public Level() {
		mapList = new ArrayList<Map>();
	}
	
	public Map getCurrentMap() {
		return currentMap;
	}
	
	public void makeMaps( ) {
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
	}

	public void initLevel(int mode) {
		score = 0;
		lives = 3;
		if (mode == 3) {
			timeLimit = -1;
		} else {
			timeLimit = 120; //180?
		}
		
		makeMaps();
		currentMap = mapList.get(mode);
		//spaceman = new Spaceman(10, 15); //SHOULDNT BE HERE, SPACEMAN IS A CLASS THAT RELATES TO VIEW/GRAPHICS
		//command to generate visuals for map goes here
	}
	
	public int getTimeLimit() {
		return timeLimit;
	}
//
//	public void setMap(int type) {
//		
//		initLevel());
//	}
	
	public void addPoints(int pointsToAdd) {
		score += pointsToAdd;
	}
	
	public int getScore() {
		return score;
	}
}
