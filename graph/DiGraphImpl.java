package graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
 
//test comment another and another
public class DiGraphImpl implements DiGraph{

	private List<GraphNode> nodeList = new ArrayList<>();
	

	@Override
	public Boolean addNode(GraphNode node) {
		if(nodeList.add(node)) {
			return true;
		}
		return false;
	}

	@Override
	public Boolean removeNode(GraphNode node) {
		if(nodeList.remove(node)) {
			return true;
		}
		return false;
	}

	@Override
	public Boolean setNodeValue(GraphNode node, String newNodeValue) {
		node.setValue(newNodeValue);
		return true;
	}

	@Override
	public String getNodeValue(GraphNode node) {
		return node.getValue();
	}

	@Override
	public Boolean addEdge(GraphNode fromNode, GraphNode toNode, Integer weight) {
		return fromNode.addNeighbor(toNode, weight);
	}

	@Override
	public Boolean removeEdge(GraphNode fromNode, GraphNode toNode) {
		return fromNode.removeNeighbor(toNode);
	}

	@Override
	public Boolean setEdgeValue(GraphNode fromNode, GraphNode toNode, Integer newWeight) {
		return fromNode.addNeighbor(toNode, newWeight);
	}

	@Override
	public Integer getEdgeValue(GraphNode fromNode, GraphNode toNode) {
		return fromNode.getDistanceToNeighbor(toNode);
	}

	@Override
	public List<GraphNode> getAdjacentNodes(GraphNode node) {
		return node.getNeighbors();
	}

	@Override
	public Boolean nodesAreAdjacent(GraphNode fromNode, GraphNode toNode) {
		if(fromNode.getNeighbors().contains(toNode) && toNode.getNeighbors().contains(fromNode)) {
			return true;
		}
		return false;
	}

	@Override
	public Boolean nodeIsReachable(GraphNode fromNode, GraphNode toNode) {
		if(fromNode.getNeighbors().contains(toNode)) {
			return true;
		}
		for(GraphNode neighbor: fromNode.getNeighbors()) {
			if(nodeIsReachable(neighbor, toNode)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Boolean hasCycles() {
		for(GraphNode node : nodeList) {
			if(nodeIsReachable(node,node)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<GraphNode> getNodes() {
		return nodeList;
	}

	@Override
	public GraphNode getNode(String nodeValue) {
	    for (GraphNode node : nodeList) {
	        if (node.getValue().equals(nodeValue)) {
	            return node;
	        }
	    }
	    return null;
	}

	@Override
	public int fewestHops(GraphNode fromNode, GraphNode toNode) {
		Queue<GraphNode> queue = new LinkedList<>();
        queue.add(fromNode);
        fromNode.visited = true;
        int level = 0; // Initialize the level counter
        int nodesAtCurrentLevel = 1; // Counter for nodes at the current level

        while (!queue.isEmpty()) {
            int nodesAtNextLevel = 0; // Counter for nodes at the next level

            while (nodesAtCurrentLevel > 0) {
                GraphNode element = queue.remove();

                List<GraphNode> neighbours = element.getNeighbors();
                for (int i = 0; i < neighbours.size(); i++) {
                    GraphNode n = neighbours.get(i);
                    if (n != null && !n.visited) {
                        queue.add(n);
                        n.visited = true;
                        nodesAtNextLevel++; // Increment the counter for the next level
                    }
                    if (n.equals(toNode)) {
                        return level + 1; // Add 1 to include the current node
                    }
                }
                nodesAtCurrentLevel--;
            }
            // Update the counters for the next level
            nodesAtCurrentLevel = nodesAtNextLevel;
            level++;
        }
        // If no path found
        return -1;
	}

	@Override
	public int shortestPath(GraphNode fromNode, GraphNode toNode) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
}
