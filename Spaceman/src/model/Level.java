package model;

import model.Map;

import java.util.ArrayList;

public class Level {
	public int lives;
	public int timeRemaining;
	public int score;
	
	public Map currentMap;
	//public Character pac;
	
	public ArrayList <Map> mapList;
	
	public void makeMaps( ) {
		Map classic = new Map();
		classic.initMap(1);
		//mapList.add(classic);
		//..add other maps
	}
	
	public void initLevel(Map model) {
		lives = 3;
		timeRemaining = 120; //180?
		score = 0;
		currentMap = model;
		//command to generate visuals for map goes here
	}
	
	public void setMap(int type) {
		if (type == 1) {
			//initLevel(mapList.get(0));
		}
	}
}
