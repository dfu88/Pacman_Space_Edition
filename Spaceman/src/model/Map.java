package model;

public class Map {
	//need to check pub/static
	public static int gridX;
	public static int gridY;
	public static int mapArray[][] = new int[gridY][gridX];
	
	
	public int GROUND 	= 0;
	public static int WALL 	= 1;
	public int PELLET 	= 2;
	public int POWERUP 	= 3;
	
	public Map(int rows, int cols) {
		gridX = cols;
		gridY = rows;
	}
	
	
	public static int getData(int row, int col) {
		return mapArray[row][col];
	}
	
//	public static void testSetWalls(int index) {
//		for (int i = 0; i < gridY; i++) {
//			mapArray[i][index] = WALL;
//		}
//
//	}
}
