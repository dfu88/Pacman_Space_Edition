package model;

import model.Map;
import view.Spaceman;

import java.util.ArrayList;

public class Level {
	public int lives;
	public int timeRemaining;
	public int score;
	public int pelletsRemaining;

	public Map currentMap;
	public Spaceman spaceman;

	public ArrayList <Map> mapList;

	public Level() {
		mapList = new ArrayList<Map>();
	}

	public void makeMaps( ) {
		Map classic = new Map();
		classic.initMap(1);
		//check map data is initilised
		//		for (int i = 0; i < 21; i++) {
		//			for (int j = 0;j<21;j++) {
		//				int a = classic.getData(i, j);
		//				System.out.print(a);
		//			}
		//			System.out.println("\n");
		//		}
		mapList.add(classic);
		//..add other maps
	}

	public void initLevel(Map model) {
		lives = 3;
		timeRemaining = 120; //180?
		score = 0;
		currentMap = model;
		spaceman = new Spaceman(800, 700);
		//command to generate visuals for map goes here
	}

	public void setMap(int type) {
		if (type == 1) {
			initLevel(mapList.get(0));
		} else if (type == 2) { //prob dont need these, just pass type straight into initlevel func
			//for testing 
//			System.out.println("bbbb");
//			System.out.println(type);

			initLevel(mapList.get(type  - 1));
		}
	}
}
