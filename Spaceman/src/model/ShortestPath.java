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

	private int nodeIndex(Vector<Vector<Integer>> nodes, int x, int y) {
		for(int i=0; i<nodes.size(); i++) {
			Vector<Integer> node = nodes.get(i);
			int thisX = node.get(0);
			int thisY = node.get(1);
			if (thisX == x && thisY == y) {
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
					node.add(row);
					nodes.add(node);
				}
			}
		}

		// Intialise empty set of previous nodes
		Vector<Vector<Integer>> previousNodes = new Vector<Vector<Integer>>(nodes.size());

		// Intialise weight vector (g)
		Vector<Integer> weight = new Vector<Integer>(nodes.size());
		// Intialise heuristic vector (h) - using manhattan distance for heuristic
		Vector<Integer> heuristic = new Vector<Integer>(nodes.size());
		// Intialise total cost (f) = weight(g) + heuristic vector(h)
		Vector<Integer> totalCost = new Vector<Integer>(nodes.size());
		// Set all weights, heuristics, and totalCosts to infinity
		for (int i=0; i<weight.size(); i++) {
			weight.add(i, Integer.MAX_VALUE);
			heuristic.add(i, Integer.MAX_VALUE);
			totalCost.add(i, Integer.MAX_VALUE);
		}

		// Intialise empty set of open nodes
		Vector<Vector<Integer>> openNodes = new Vector<Vector<Integer>>();
		// Intialise empty set of closed nodes (essentially visited nodes)
		Vector<Vector<Integer>> closedNodes = new Vector<Vector<Integer>>();

		// Get source and target nodes
		Vector<Integer> sourceNode = nodes.get(nodeIndex(nodes, sourceX, sourceY));
		Vector<Integer> targetNode = nodes.get(nodeIndex(nodes, targetX, targetY));
		// Calculates heuristic of source node to target node
		int sourceHeuristic = Math.abs(sourceX-targetX) + Math.abs(sourceY-targetY);
		// Adds source node to the openNodes list
		openNodes.add(sourceNode);
		// Updates the weight, heuristic, and totalCost vectors for source node
		weight.add(nodeIndex(nodes, sourceX, sourceY), 0);
		heuristic.add(nodeIndex(nodes, sourceX, sourceY), sourceHeuristic);
		totalCost.add(nodeIndex(nodes, sourceX, sourceY), sourceHeuristic);

		while (openNodes.isEmpty()) {
			Vector<Integer> currentNode = nodes.get(minIndex(openNodes,nodes,totalCost));
			int currentIndex = nodes.indexOf(currentNode);
			if (currentNode == targetNode) {
				return totalCost.get(currentIndex);
			}
			openNodes.remove(currentNode);
			closedNodes.add(currentNode);
			Vector<Vector<Integer>> neighbourNodes = generateNeighbours(nodes, currentNode);
			for (int i=0; i<neighbourNodes.size(); i++) {
				Vector<Integer> neighbourNode = neighbourNodes.get(i); 
				int neighbourIndex = nodes.indexOf(neighbourNode);
				if (closedNodes.contains(neighbourNode)) {
					continue;
				}
				if (!(openNodes.contains(neighbourNode))) {
					openNodes.add(neighbourNode);
				}
				int tempWeight = weight.get(currentIndex) + 1;
				if (tempWeight >= weight.get(neighbourIndex)) {
					continue;
				}
				int neighbourHeuristic = Math.abs(neighbourNode.get(0) - targetX) + Math.abs(neighbourNode.get(1) - targetY);
				previousNodes.add(neighbourIndex, currentNode);
				weight.add(neighbourIndex, tempWeight);
				heuristic.add(neighbourIndex, neighbourHeuristic);
				totalCost.add(neighbourIndex, tempWeight+neighbourHeuristic);
			}
		}
		return Integer.MAX_VALUE;
	}

	private boolean compareNodes(Vector<Integer> node1, Vector<Integer> node2) {
		if (node1 == node2) {
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
		return mIndex;
	}

	private Vector<Vector<Integer>> generateNeighbours(Vector<Vector<Integer>> nodes, Vector<Integer> node){
		int nodeX = node.get(0);
		int nodeY = node.get(1);

		// Generate neighbours
		Vector<Integer> leftNeighbour = new Vector<Integer>();
		leftNeighbour.add(0, nodeX-1);
		leftNeighbour.add(1, nodeY);
		Vector<Integer> rightNeighbour = new Vector<Integer>();
		leftNeighbour.add(0, nodeX+1);
		leftNeighbour.add(1, nodeY);
		Vector<Integer> upNeighbour = new Vector<Integer>();
		leftNeighbour.add(0, nodeX);
		leftNeighbour.add(1, nodeX-1);
		Vector<Integer> downNeighbour = new Vector<Integer>();
		leftNeighbour.add(0, nodeX);
		leftNeighbour.add(1, nodeX+1);

		// Add valid neighbours to vector
		Vector<Vector<Integer>> neighbourNodes = new Vector<Vector<Integer>>();
		if (nodes.contains(leftNeighbour)) {
			neighbourNodes.add(leftNeighbour);
		}
		if (nodes.contains(rightNeighbour)) {
			neighbourNodes.add(rightNeighbour);
		}
		if (nodes.contains(upNeighbour)) {	
			neighbourNodes.add(upNeighbour);
		}	
		if (nodes.contains(downNeighbour)) {
			neighbourNodes.add(downNeighbour);
		}

		return neighbourNodes;
	}

	//	private boolean isValidNode(Vector<Vector<Integer>> nodes, Vector<Integer> node) {
	//		
	//	}


}
