package model;

import java.util.Vector;

import controller.LevelController;

public class ShortestPath {
	//private int[][] mapArray;
	public LevelController levelController;
	//	private ArrayList<Integer> nextDXDY; //{dx,dy}


	public ShortestPath(LevelController levelController) {
		//this.mapArray = mapArray;
		this.levelController = levelController;
	}

	//	public int[] getDXDY(int sourceX, int sourceY, int targetX, int targetY) {
	//		return nextDXDY;
	//	}

	private int nodeIndex(Vector<Vector<Integer>> nodes, Vector<Integer> node) {
		int x = node.get(0);
		int y = node.get(1);
		System.out.println("nodeIndexX "+x);
		System.out.println("nodeIndexY "+y);
		for(int i=0; i<nodes.size(); i++) {
			Vector<Integer> currentNode = nodes.get(i);
			int currentX = currentNode.get(0);
			int currentY = currentNode.get(1);
			if (currentX == x && currentY == y) {
				return i;
			}
		}
		return -1;
	}

	public int computeShortest(int sourceX, int sourceY, int targetX, int targetY){
		// Intialise set of nodes
		Vector<Vector<Integer>> nodes = new Vector<Vector<Integer>>();
		// Add all nodes
		for (int row=0; row<21; row++) {
			for (int col=0; col<21; col++) {
				int currentElement = levelController.getLevel().getCurrentMap().getData(row, col);
				if (currentElement != 1) {
					Vector<Integer> node = new Vector<Integer>(); 
					node.add(col);
					//System.out.println(col);
					node.add(row);
					//System.out.println(row);
					nodes.add(node);
				}
			}
		}

		// Intialise empty set of previous nodes
		Vector<Vector<Integer>> previousNodes = new Vector<Vector<Integer>>(nodes.size());

		// Intialise weight vector (g)
		Vector<Integer> weight = new Vector<Integer>();
		// Intialise heuristic vector (h) - using manhattan distance for heuristic
		Vector<Integer> heuristic = new Vector<Integer>();
		// Intialise total cost (f) = weight(g) + heuristic vector(h)
		Vector<Integer> totalCost = new Vector<Integer>();
		// Set all weights, heuristics, and totalCosts to infinity
		for (int i=0; i<nodes.size(); i++) {
			weight.add(Integer.MAX_VALUE);
			heuristic.add(Integer.MAX_VALUE);
			totalCost.add(Integer.MAX_VALUE);
		}

		// Intialise empty set of open nodes
		Vector<Vector<Integer>> openNodes = new Vector<Vector<Integer>>();
		// Intialise empty set of closed nodes (essentially visited nodes)
		Vector<Vector<Integer>> closedNodes = new Vector<Vector<Integer>>();

		
		Vector<Integer> sourceNode = new Vector<Integer>();
		sourceNode.add(sourceX);
		sourceNode.add(sourceY);
		Vector<Integer> targetNode = new Vector<Integer>();
		targetNode.add(targetX);
		targetNode.add(targetY);
		// Get source and target nodes
		int sourceIndex = nodeIndex(nodes,sourceNode);
		int targetIndex = nodeIndex(nodes,targetNode);
		if (sourceIndex<0 || targetIndex<0) {
			System.out.println("rip");
			return Integer.MAX_VALUE;
		}
//		Vector<Integer> sourceNode = nodes.get(sourceIndex);
//		Vector<Integer> targetNode = nodes.get(targetIndex);
		// Calculates heuristic of source node to target node
		int sourceHeuristic = Math.abs(sourceX-targetX) + Math.abs(sourceY-targetY);
		// Adds source node to the openNodes list
		openNodes.add(sourceNode);
		// Updates the weight, heuristic, and totalCost vectors for source node
		weight.set(sourceIndex, 0);
		heuristic.set(sourceIndex, sourceHeuristic);
		totalCost.set(sourceIndex, sourceHeuristic);
		System.out.println("riperinno");

		while (!(openNodes.isEmpty())) {
			Vector<Integer> currentNode = nodes.get(minIndex(openNodes,nodes,totalCost));
			int currentIndex = nodeIndex(nodes,currentNode);
			System.out.println("while "+currentIndex);
			if (currentNode == targetNode) {
				System.out.println("FFFUUUUUUU");
				return totalCost.get(currentIndex);
			}
			openNodes.remove(currentNode);
			closedNodes.add(currentNode);
			Vector<Vector<Integer>> neighbourNodes = generateNeighbours(nodes, currentNode);
			for (int i=0; i<neighbourNodes.size(); i++) {
				Vector<Integer> neighbourNode = neighbourNodes.get(i); 
				System.out.println("innn");
				int neighbourIndex = nodeIndex(nodes,neighbourNode);
				if (closedNodes.contains(neighbourNode)) {
					System.out.println("closssed");
					continue;
				}
				if (!(openNodes.contains(neighbourNode))) {
					openNodes.add(neighbourNode);
					System.out.println("addd");
				}
				int tempWeight = weight.get(currentIndex) + 1;
				if (tempWeight >= weight.get(neighbourIndex)) {
					System.out.println("ghjhjck");
					continue;
				}
				int neighbourHeuristic = Math.abs(neighbourNode.get(0) - targetX) + Math.abs(neighbourNode.get(1) - targetY);
				//previousNodes.set(neighbourIndex, currentNode);
				weight.set(neighbourIndex, tempWeight);
				heuristic.set(neighbourIndex, neighbourHeuristic);
				totalCost.set(neighbourIndex, tempWeight+neighbourHeuristic);
				
				System.out.println(totalCost);
				System.out.println("SUPERgood");
			}
			return Integer.MAX_VALUE;
		}
		System.out.println("fckkkkk");
		return Integer.MAX_VALUE;
	}

	private boolean compareNodes(Vector<Integer> node1, Vector<Integer> node2) {
		int node1X = node1.get(0);
		int node1Y = node1.get(1);
		int node2X = node2.get(0);
		int node2Y = node2.get(1);
		
		if (node1X == node2X && node1Y == node2Y) {
			return true;
		}
		
		return false;
	}

	private int minIndex(Vector<Vector<Integer>> openNodes, Vector<Vector<Integer>> nodes, Vector<Integer> weight) {
		int mWeight = Integer.MAX_VALUE;
		int mIndex = 0;
		for (int i=0; i<openNodes.size(); i++) {
			Vector<Integer> openNode = openNodes.get(i);
			for (int j=0; j<nodes.size(); j++) {
				Vector<Integer> node = nodes.get(j);
				if (compareNodes(openNode,node)) {
					int currentWeight = weight.get(j);
					if (currentWeight < mWeight) {
						mWeight = currentWeight;
						mIndex = j;
					}
				}
			}
		}
		System.out.println("------");
		System.out.println("minInd "+mIndex);
		return mIndex;
		
	}

	private Vector<Vector<Integer>> generateNeighbours(Vector<Vector<Integer>> nodes, Vector<Integer> node){
		int nodeX = node.get(0);
		int nodeY = node.get(1);
		System.out.println("genNX "+nodeX);
		System.out.println("genNY "+nodeY);

		// Generate neighbours
		Vector<Integer> leftNeighbour = new Vector<Integer>();
		leftNeighbour.add(nodeX-1);
		leftNeighbour.add(nodeY);
		Vector<Integer> rightNeighbour = new Vector<Integer>();
		rightNeighbour.add(nodeX+1);
		rightNeighbour.add(nodeY);
		Vector<Integer> upNeighbour = new Vector<Integer>();
		upNeighbour.add(nodeX);
		upNeighbour.add(nodeY-1);
		Vector<Integer> downNeighbour = new Vector<Integer>();
		downNeighbour.add(nodeX);
		downNeighbour.add(nodeY+1);

		// Add valid neighbours to vector
		Vector<Vector<Integer>> neighbourNodes = new Vector<Vector<Integer>>();
		if (nodes.contains(leftNeighbour)) {
			neighbourNodes.add(leftNeighbour);
			System.out.println("goodLeft");
		}
		if (nodes.contains(rightNeighbour)) {
			neighbourNodes.add(rightNeighbour);
			System.out.println("goodRight");
		}
		if (nodes.contains(upNeighbour)) {	
			neighbourNodes.add(upNeighbour);
			System.out.println("goodUp");
		}	
		if (nodes.contains(downNeighbour)) {
			neighbourNodes.add(downNeighbour);
			System.out.println("goodDown");
		}
		
		System.out.println("okay");
		return neighbourNodes;
	}

	//	private boolean isValidNode(Vector<Vector<Integer>> nodes, Vector<Integer> node) {
	//		
	//	}


}
