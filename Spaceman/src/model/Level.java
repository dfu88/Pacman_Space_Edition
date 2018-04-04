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
	
	public Level() {
		mapList = new ArrayList<Map>();
	}
	
	public void makeMaps( ) {
		Map classic = new Map();
		classic.initMap(1);
	
		//check map data is initilised
		for (int i = 0; i < 21; i++) {
			for (int j = 0;j<21;j++) {
				int a = classic.getData(i, j);
				System.out.print(a);
			}
			System.out.println("\n");
		}
		
		mapList.add(classic);
		
		
		Map testMap = new Map();
		testMap.initMap(2);
		mapList.add(testMap);
		
		//checking the maps are correctly saved
//		for (int i = 0; i < 21; i++) {
//			for (int j = 0;j<21;j++) {
//				int a = mapList.get(0).getData(i, j);
//				System.out.print(a);
//			}
//			System.out.println("\n");
//		}
//		System.out.println(mapList.size());
//		for (int i = 0; i < 21; i++) {
//			for (int j = 0;j<21;j++) {
//				int a = mapList.get(1).getData(i, j);
//				System.out.print(a);
//			}
//			System.out.println("\n");
//		}
//		
//		System.out.println(mapList.size());
//		..add other maps
	}
	
	public void initLevel(Map model) {
		lives = 3;
		timeRemaining = 120; //180?
		score = 0;
		currentMap = model;
		//command to generate visuals for map goes here
	}
	
	public void setMap(int type) {
		if (type == 1) { //can possible get rid of these
			//for testing 
			System.out.println("aaaaa");
			System.out.println(type);
			
			initLevel(mapList.get(type - 1));
			
		} else if (type == 2) { //prob dont need these, just pass type straight into initlevel func
			//for testing 
			System.out.println("bbbb");
			System.out.println(type);
			
			initLevel(mapList.get(type  - 1));
		}
	}
}
