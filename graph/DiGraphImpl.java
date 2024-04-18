package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;




 
//test comment another and another
public class DiGraphImpl implements DiGraph{

	private List<GraphNode> nodeList = new ArrayList<>();
	private int circuitCounter;

	@Override
	public Boolean addNode(GraphNode node) {
		if (getNode(node.getValue()) == null) { 
			nodeList.add(node);
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
		GraphNode targetFromNode = getNode(fromNode.getValue());
		GraphNode targetToNode = getNode(toNode.getValue());

		if (targetFromNode == null || targetToNode == null)
			return false;

		return targetFromNode.addNeighbor(targetToNode, weight);
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
		GraphNode targetFromNode = getNode(fromNode.getValue());
		GraphNode targetToNode = getNode(toNode.getValue());
		circuitCounter++;
		if(targetFromNode.getNeighbors().contains(targetToNode)) {
			return true;
		}
		else if(circuitCounter>nodeList.size()*100)
		{
			return false;
		}
		for(GraphNode neighbor: targetFromNode.getNeighbors()) {
			if(nodeIsReachable(neighbor, targetToNode)) {
				return true;
			}
		}
		
		
		return false;
	}

	@Override
	public Boolean hasCycles() {
		for(GraphNode node : nodeList) {
			circuitCounter = 0;
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
		GraphNode targetFromNode = getNode(fromNode.getValue());
		GraphNode targetToNode = getNode(toNode.getValue());
		if(targetFromNode == null || targetToNode == null) {
			return -1;
		}
		//TODO - your implementation here..
		
		Queue<GraphNode> queue = new LinkedList<>();
        queue.add(targetFromNode);
        int level = 0; // Initialize the level counter
        int nodesAtCurrentLevel = 1; // Counter for nodes at the current level
        while (!queue.isEmpty()) {
            int nodesAtNextLevel = 0; // Counter for nodes at the next level
            while (nodesAtCurrentLevel > 0) {
                GraphNode element = queue.remove();
                List<GraphNode> neighbours = element.getNeighbors();
                if(neighbours == null) {
                	return -1;
                }
                for (int i = 0; i < neighbours.size(); i++) {
                    GraphNode n = neighbours.get(i);
                    if (n != null && !queue.contains(n)) {
                        queue.add(n);
                        nodesAtNextLevel++; // Increment the counter for the next level
                    }
                    if (n.equals(targetToNode)) {
                        return level + 1; // Add 1 to include the current node
                    }
                }
                nodesAtCurrentLevel--;
            }
            nodesAtCurrentLevel = nodesAtNextLevel;
            level++;
        }
        // If no path found
        if(level <= 0) {
        	return -1;
        }
        return level;
	}

	@Override
	public int shortestPath(GraphNode fromNode, GraphNode toNode) {
		
		GraphNode targetFromNode = getNode(fromNode.getValue());
		GraphNode targetToNode = getNode(toNode.getValue());
		
		//Lists for algorithm
		//Set<GraphNode> unSettled1 = new HashSet<>();
		Map<String,Integer> distance = new HashMap<>();
		Map<String,GraphNode> prev = new HashMap<>();
		
		//Temp weight value
		Integer temp;
		GraphNode currentNode;
		
		//Create List of Nodes to be processed from graph
		Queue<GraphNode> queue = new LinkedList<>();
        queue.add(targetFromNode);
        Set<GraphNode> alreadyVisited = new HashSet<>();
        while (!queue.isEmpty()) {
            currentNode = queue.remove();
            if(nodeIsReachable(currentNode,targetToNode)||currentNode.equals(targetToNode))
            {
	            System.out.print(currentNode.getValue() + " | ");
            
	            if(currentNode.equals(targetFromNode))
	            	distance.put(currentNode.getValue(),0);
	            else
	            	distance.put(currentNode.getValue(),Integer.MAX_VALUE);
	            prev.put(currentNode.getValue(),null);
            }	
	        alreadyVisited.add(currentNode);
	        if(!currentNode.equals(targetToNode))
	        {
	        	queue.addAll(getAdjacentNodes(currentNode));
	           	queue.removeAll(alreadyVisited);
	        }
        }
        
        List<GraphNode> unSettled = new ArrayList<>();
        unSettled.add(targetFromNode);
        while(!unSettled.isEmpty())
        {
        	/*
        	if(unSettled.contains(targetFromNode))
        		currentNode = targetFromNode;
        	else
        		currentNode = unSettled.get(0);
        	*/
        	currentNode = unSettled.get(0);
        	unSettled.remove(currentNode);
        	
	        for(GraphNode neighbors: currentNode.getNeighbors())
	        {
	        	if((nodeIsReachable(neighbors,targetToNode)||neighbors.equals(targetToNode)))
	        	{
	        		temp = distance.get(currentNode.getValue())+getEdgeValue(currentNode,neighbors);
	        		if(temp<distance.get(neighbors.getValue()))
	        		{
	        			distance.replace(neighbors.getValue(), temp);
	        			prev.replace(neighbors.getValue(), currentNode);
	        		}
	        		if(!neighbors.equals(targetToNode))
	        				unSettled.add(neighbors);
        		}
        	}
			
        }
        
		System.out.println();
		return distance.get(targetToNode.getValue());
		
		
		
		/*GraphNode targetFromNode = getNode(fromNode.getValue());
		GraphNode targetToNode = getNode(toNode.getValue());
		int weight = 0;
		int count = 0;
		int total = 0;
		if(targetFromNode.getNeighbors().contains(targetToNode)) {
			weight += getEdgeValue(targetFromNode,targetToNode);
			if(count == 0) {
				total = weight;
				count ++;
			}else if(weight < total){
				total = weight;
			}
			weight = 0;
		}
		for(GraphNode neighbor: targetFromNode.getNeighbors()) {
			weight += getEdgeValue(neighbor,targetToNode);
			if(nodeIsReachable(neighbor, targetToNode)) {
				
			}
		}
		return total;*/
		
		/*
		GraphNode targetFromNode = getNode(fromNode.getValue());
		GraphNode targetToNode = getNode(toNode.getValue());
		Queue<GraphNode> queue = new LinkedList<>();		//linked list can implement queue
        queue.add(targetFromNode);
        int weight = 0;
        int total=0;
        GraphNode currentNode;
        //Set<GraphNode> alreadyVisited = new HashSet<>();
        //System.out.print("Visited nodes: ");
   
        while (!queue.isEmpty()) {
            currentNode = queue.remove();
            //System.out.print(currentNode.data + " | ");
            
            for(GraphNode c : currentNode.getNeighbors())
            {	
            	//weight += getEdgeValue(currentNode,c);
            	if(nodeIsReachable(getNode(c.getValue()),targetToNode)||getNode(c.getValue()).equals(targetToNode))
            	{
            		weight+=getEdgeValue(currentNode,c);
            		
            		
            			if((getNode(c.getValue()).equals(targetToNode)&&total<weight + getEdgeValue(currentNode,c))||total==0)
            				total = weight;
            		queue.add(c);
            	}
            	
            }
            //queue.addAll(currentNode.getNeighbors());
            //alreadyVisited.add(currentNode);
            
           //queue.removeAll(alreadyVisited);
        }
		
		return total;
		*/
		
		
	}
	
	
	
}
