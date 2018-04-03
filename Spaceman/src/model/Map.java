package model;

public class Map {
	//need to check pub/static
	//public static int gridX;
	//public static int gridY;
	//public static int mapArray[][] = new int[gridY][gridX];
	public static int mapArray[][];// = new int[gridY][gridX];
	
	/*NOTE: map looks like 		col1 | col2
							row1| X		X
							row2| X		X
	*/
	public int GROUND 	= 0;
	public static int WALL 	= 1;
	public int PELLET 	= 2;
	public int POWERUP 	= 3;
	public int TELE = 5;
	public int SPAWN = 7;//maybe add ghost spawn aswell
	public int GATE = 8;
	
	public Map(int type) {
		initMap(type);
	}
	
	private void initMap(int type) {
		if (type == 1) {
			//mapArray = new int [21][21];
			//					  0  1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 16 17 18 19 20
			int temp[][] =  { 	{ 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 },		//index:0
								{ 0, 1, 3, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 3, 1, 0 },		//		1
								{ 0, 1, 2, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 2, 1, 0 },		//		2
								{ 0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 0 },		//		3
								{ 0, 1, 2, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 2, 1, 0 },		//		4
								{ 0, 1, 2, 2, 2, 2, 1, 2, 2, 2, 1, 2, 2, 2, 1, 2, 2, 2, 2, 1, 0 },		//		5
								{ 0, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 1, 1, 0 },		//		6
								{ 0, 0, 0, 0, 1, 2, 1, 2, 2, 2, 2, 2, 2, 2, 1, 2, 1, 0, 0, 0, 0 },		//		7
								{ 0, 1, 1, 1, 1, 2, 1, 2, 1, 1, 8, 1, 1, 2, 1, 2, 1, 1, 1, 1, 0 },		//		8
								{ 5, 0, 0, 0, 0, 2, 2, 2, 1, 0, 0, 0, 1, 2, 2, 2, 0, 0, 0, 0, 5 },		//		9
								{ 0, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 0 },		//		10
								{ 0, 0, 0, 0, 0, 2, 1, 2, 2, 2, 7, 2, 2, 2, 1, 2, 1, 0, 0, 0, 0 },		//		11
								{ 0, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 0 },		//		12
								{ 0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 0 },		//		13
								{ 0, 1, 2, 1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1, 2, 1, 0 },		//		14
								{ 0, 1, 2, 2, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 1, 0 },		//		15
								{ 0, 1, 1, 2, 1, 2, 1, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1, 2, 1, 1, 0 },		//		16
								{ 0, 1, 2, 2, 2, 2, 1, 2, 2, 2, 1, 2, 2, 2, 1, 2, 2, 2, 2, 1, 0 },		//		17
								{ 0, 1, 2, 1, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 1, 2, 1, 0 },		//		18
								{ 0, 1, 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 1, 0 },		//		19
								{ 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 }	};  //		20
			mapArray = temp;
		}
	}
	
	public int getData(int row, int col) {
		return mapArray[row][col];
	}
	
//	public static void testSetWalls(int index) {
//		for (int i = 0; i < gridY; i++) {
//			mapArray[i][index] = WALL;
//		}
//
//	}
}
