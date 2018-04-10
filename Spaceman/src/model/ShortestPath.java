package model;

public class ShortestPath {
	private int[][] mapArray;
	private int[] nextDXDY; //{dx,dy}
	
	
	public ShortestPath(int[][] mapArray) {
		this.mapArray = mapArray;
	}

	public int[] getDXDY(int sourceX, int sourceY, int targetX, int targetY) {
		return nextDXDY;
	}
	

}
