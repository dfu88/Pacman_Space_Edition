package model;

import java.util.ArrayList;

import view.Spaceman;



public class Level {
	public int lives;
	public int timeRemaining;
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
		Map classic = new Map();
		classic.initMap(1);
		mapList.add(classic);
		
		Map testMap = new Map();
		testMap.initMap(2);
		mapList.add(testMap);
	}

	private void initLevel(Map model) {
		lives = 3;
		timeRemaining = 120; //180?
		score = 0;
		currentMap = model;
		//spaceman = new Spaceman(10, 15); //SHOULDNT BE HERE, SPACEMAN IS A CLASS THAT RELATES TO VIEW/GRAPHICS
		//command to generate visuals for map goes here
	}

	public void setMap(int type) {
		initLevel(mapList.get(type));
	}
}
