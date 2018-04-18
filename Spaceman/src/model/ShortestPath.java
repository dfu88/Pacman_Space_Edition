package model;

import java.util.Vector;

import controller.LevelController;

public class ShortestPath {
	public LevelController levelController;

	public ShortestPath(LevelController levelController) {
		this.levelController = levelController;
	}

	private int nodeIndex(Vector<Vector<Integer>> nodes, Vector<Integer> node) {
		int x = node.get(0);
		int y = node.get(1);
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

	/*
	 * computeShortest function returns the distance of the shortest path
	 * from source node to target node
	 */
	public int computeShortest(int sourceX, int sourceY, int targetX, int targetY){
		// Intialise set of nodes
		Vector<Vector<Integer>> nodes = new Vector<Vector<Integer>>();
		// Add all nodes
		for (int row=0; row<21; row++) {
			for (int col=0; col<21; col++) {
				int currentElement = levelController.getLevel().getCurrentMap().getData(row, col);
				if (currentElement != 1 && currentElement != 9) {
					Vector<Integer> node = new Vector<Integer>(); 
					node.add(col);
					node.add(row);
					nodes.add(node);
				}
			}
		}

		// Intialise weight vector (g)
		Vector<Integer> weight = new Vector<Integer>();
		// Intialise total cost (f) = weight(g) + heuristic - manhattan dist(h)
		Vector<Integer> totalCost = new Vector<Integer>();
		// Set all weights, heuristics, and totalCosts to infinity
		for (int i=0; i<nodes.size(); i++) {
			weight.add(Integer.MAX_VALUE);
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
			return Integer.MAX_VALUE;
		}
		// Calculates heuristic of source node to target node
		int sourceHeuristic = Math.abs(sourceX-targetX) + Math.abs(sourceY-targetY);
		// Adds source node to the openNodes list
		openNodes.add(sourceNode);
		// Updates the weight, heuristic, and totalCost vectors for source node
		weight.set(sourceIndex, 0);
		totalCost.set(sourceIndex, sourceHeuristic);

		while (!(openNodes.isEmpty())) {
			Vector<Integer> currentNode = nodes.get(minIndex(openNodes,nodes,totalCost));
			int currentIndex = nodeIndex(nodes,currentNode);
			if (compareNodes(currentNode,targetNode)) {
				return totalCost.get(currentIndex);
			}
			openNodes.remove(currentNode);
			closedNodes.add(currentNode);
			Vector<Vector<Integer>> neighbourNodes = generateNeighbours(nodes, currentNode);
			for (int i=0; i<neighbourNodes.size(); i++) {
				Vector<Integer> neighbourNode = neighbourNodes.get(i); 
				int neighbourIndex = nodeIndex(nodes,neighbourNode);
				if (isValid(closedNodes,neighbourNode)) {
					continue;
				}
				if (!(isValid(openNodes, neighbourNode))) {
					openNodes.add(neighbourNode);
				}
				int tempWeight = weight.get(currentIndex) + 1;
				if (tempWeight >= weight.get(neighbourIndex)) {
					continue;
				}
				int neighbourHeuristic = Math.abs(neighbourNode.get(0) - targetX) + Math.abs(neighbourNode.get(1) - targetY);
				weight.set(neighbourIndex, tempWeight);
				totalCost.set(neighbourIndex, tempWeight+neighbourHeuristic);
			}
		}
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
		return mIndex;
	}

	/*
	 * generateNeighbours function returns a vector of all the valid neighbouring nodes
	 * of the current node
	 */
	private Vector<Vector<Integer>> generateNeighbours(Vector<Vector<Integer>> nodes, Vector<Integer> node){
		int nodeX = node.get(0);
		int nodeY = node.get(1);

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
		if (isValid(nodes,leftNeighbour)) {
			neighbourNodes.add(leftNeighbour);
		}
		if (isValid(nodes,rightNeighbour)) {
			neighbourNodes.add(rightNeighbour);
		}
		if (isValid(nodes,upNeighbour)) {	
			neighbourNodes.add(upNeighbour);
		}	
		if (isValid(nodes,downNeighbour)) {
			neighbourNodes.add(downNeighbour);
		}
		return neighbourNodes;
	}

	private boolean isValid(Vector<Vector<Integer>> setNodes, Vector<Integer> node) {
		for (int i=0; i<setNodes.size(); i++) {
			if (compareNodes(node, setNodes.get(i))) {
				return true;
			}
		}
		return false;
	}

}
