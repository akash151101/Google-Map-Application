/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
	//TODO: Add your member variables here in WEEK 3
	private int numVertices;
	private int numEdges;
	private HashMap<GeographicPoint, MapNode> adjListMap;
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		// TODO: Implement in this constructor in WEEK 3
		numVertices = 0;
		numEdges = 0;
		adjListMap = new HashMap<GeographicPoint, MapNode>();
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		//TODO: Implement this method in WEEK 3
		return numVertices;
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		//TODO: Implement this method in WEEK 3
        Set<GeographicPoint> ListVertices = new HashSet<GeographicPoint>();
        for(GeographicPoint key: adjListMap.keySet()) {
        	ListVertices.add(key);
        }

		return ListVertices;
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		//TODO: Implement this method in WEEK 3
		return numEdges;
	}

	
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		// TODO: Implement this method in WEEK 3
		
		if(location == null)
			return false;
		
		if(!adjListMap.containsKey(location)) {
			MapNode Vertex = new MapNode(location);
			adjListMap.put(location, Vertex);
			numVertices++;
			return true;
		}
		return false;
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {

		//TODO: Implement this method in WEEK 3
        if(errorFound(from, to, length))
        	throw new IllegalArgumentException("Argument Exception Occured");
		
		
		MapNode start = adjListMap.get(from);
		MapNode goal = adjListMap.get(to);
		
		MapEdge Edge = new MapEdge(start, goal, roadName, roadType, length);
		if(adjListMap.containsKey(from)) {
			MapNode node = adjListMap.get(from);
			List<MapEdge> edges = node.listOfEdges();
			if(!edges.contains(Edge)) {
				node.addEdge(Edge);
				numEdges++;
			}
		}
		
	}
	
	public boolean errorFound(GeographicPoint from, GeographicPoint to, double length) {
		boolean foundError = false;
		
		if(!adjListMap.containsKey(from) || !adjListMap.containsKey(to)) {
			foundError = true;
			return foundError;
		}
		MapNode start = adjListMap.get(from);
		MapNode goal = adjListMap.get(to);
		
		if(start == null || goal == null || length  < 0) {
			foundError = true;
			return foundError;
		}
		
		return foundError;
	}

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3
		
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		if(start == null || goal == null)
			throw new NullPointerException("Cannot find path from start to goal");
		
		MapNode startNode = adjListMap.get(start);
		MapNode goalNode = adjListMap.get(goal);
		if(startNode == null || goalNode == null) {
			System.out.println("Start or Goal Node is null, therefore no path exists");
			return new LinkedList<GeographicPoint>();
		}

		HashMap<MapNode, MapNode> parentMap = new HashMap<MapNode, MapNode>();
		boolean found = bfsSearch(startNode, goalNode, parentMap, nodeSearched);
		
		if(!found) {
			System.out.println("No path exists");
			return null;
		}
		
		List<GeographicPoint> path = constructPath(startNode, goalNode, parentMap);
		
		return path;
	}
	
	public boolean bfsSearch(MapNode startNode, MapNode goalNode, HashMap<MapNode, MapNode> parentMap, Consumer<GeographicPoint> nodeSearched) {
		
		HashSet<MapNode> visitedNode = new HashSet<MapNode>();
		Queue<MapNode> toExplore = new LinkedList<MapNode>();
	    boolean found = false;
	   
	    toExplore.add(startNode);
	    while(!toExplore.isEmpty()) {
	    	MapNode curr = toExplore.remove();
	    	
			nodeSearched.accept(curr.getLocation());

	    	if(curr.equals(goalNode)) {
	    		found = true;
	    		break;
	    	}
	    	
	    	visitedNode.add(curr);
	    	Set<MapNode> Neighbors = curr.getNeighbors();
	    	for(MapNode node: Neighbors) {
	    		if(!visitedNode.contains(node)) {
	    			visitedNode.add(node);
	    			toExplore.add(node);
	    			parentMap.put(node, curr);
	    		}
	    	}
	    }
	   
	    return found;
	}
	
	public List<GeographicPoint> constructPath(MapNode startNode, MapNode goalNode, HashMap<MapNode, MapNode> parentMap){
		
		LinkedList<GeographicPoint> path = new LinkedList<GeographicPoint>();
		MapNode curr = goalNode;
		
		while(!curr.equals(startNode)) {
			path.addFirst(curr.getLocation());
			curr = parentMap.get(curr);
		}
		path.addFirst(startNode.getLocation());
		return path;
	}
	

	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4

		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		if(start == null || goal == null)
			throw new NullPointerException("Cannot find path from start to goal");
		
		MapNode startNode = adjListMap.get(start);
		MapNode goalNode = adjListMap.get(goal);
		if(startNode == null || goalNode == null) {
			System.out.println("Start or Goal Node is null, therefore no path exists");
			return null;
		}
		
		HashMap<MapNode, MapNode> parentMap = new HashMap<MapNode, MapNode>();
		boolean found = dijkstraSearch(startNode, goalNode, parentMap, nodeSearched);
		
		if(!found) {
			System.out.println("No Path found from start node to goal node");
			return null;
		}
		
		List<GeographicPoint> path = constructPath(startNode, goalNode, parentMap);
		
		return path;
	}
	
	
	public boolean dijkstraSearch(MapNode startNode, MapNode goalNode, HashMap<MapNode, MapNode> parentMap, Consumer<GeographicPoint> nodeSearched) {
		
		boolean found = false;
		int count = 0;
		PriorityQueue<MapNode> calDistance = new PriorityQueue<MapNode>();
		HashSet<MapNode> visitedTree = new HashSet<MapNode>();
		
		for(MapNode nodes: adjListMap.values())
			nodes.setDistance(Double.POSITIVE_INFINITY);
		
		startNode.setDistance(0);
		calDistance.add(startNode);
		
		while(!calDistance.isEmpty()) {
			MapNode curr = calDistance.remove();
			count++;
			// Hook for visualization.  See writeup.
            nodeSearched.accept(curr.getLocation());
			
			if(curr.equals(goalNode)) {
				found = true;
				System.out.println("Number of visited nodes: " + count);
				break;
			}
			
			if(!visitedTree.contains(curr)) {
				visitedTree.add(curr);
				for(MapEdge e: curr.listOfEdges()) {
					MapNode other = e.otherNode(curr);
					if(!visitedTree.contains(other)) {
						if(other.getDistance() > curr.getDistance() + e.getDistance()) {
							 double relaxDist = curr.getDistance() + e.getDistance();
							 other.setDistance(relaxDist);
							 parentMap.put(other, curr);
							 calDistance.add(other);
						}	
					}
				}
			}
		}
		
		
		return found;
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4
		
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());
		
		if(start == null || goal == null)
			throw new NullPointerException("Cannot find path from start to goal");
		
		MapNode startNode = adjListMap.get(start);
		MapNode goalNode = adjListMap.get(goal);
		if(startNode == null || goalNode == null) {
			System.out.println("Start or Goal Node is null, therefore no path exists");
			return null;
		}
		
		HashMap<MapNode, MapNode> parentMap = new HashMap<MapNode, MapNode>();
		boolean found = AstarSearch(startNode, goalNode, parentMap, nodeSearched);
		
		if(!found) {
			System.out.println("No Path found from start node to goal node");
			return null;
		}
		
		List<GeographicPoint> path = constructPath(startNode, goalNode, parentMap);
		
		return path;
		
	}

   public boolean AstarSearch(MapNode startNode, MapNode goalNode, HashMap<MapNode, MapNode> parentMap, Consumer<GeographicPoint> nodeSearched) {
		
		boolean found = false;
		int count = 0;
		PriorityQueue<MapNode> calDistance = new PriorityQueue<MapNode>();
		HashSet<MapNode> visitedTree = new HashSet<MapNode>();
		
		for(MapNode nodes: adjListMap.values()) {
			nodes.setDistance(Double.POSITIVE_INFINITY);
			nodes.setPredDistance(Double.POSITIVE_INFINITY);
		}
		
		startNode.setDistance(0);
		startNode.setPredDistance(0);
		calDistance.add(startNode);
		
		while(!calDistance.isEmpty()) {
			MapNode curr = calDistance.remove();
			count++;
			// Hook for visualization.  See writeup.
            nodeSearched.accept(curr.getLocation());
			
			if(curr.equals(goalNode)) {
				found = true;
				System.out.println("Number of visited nodes: " + count);
				break;
			}
			
			if(!visitedTree.contains(curr)) {
				visitedTree.add(curr);
				for(MapEdge e: curr.listOfEdges()) {
					MapNode other = e.otherNode(curr);
					if(!visitedTree.contains(other)) {
						
						double actualDist = e.getDistance() + curr.getPredDistance();
						
						double predictDist = actualDist + other.getLocation().distance(goalNode.getLocation());
						
						if(other.getDistance() > predictDist) {
//							 double relaxDist = curr.getDistance() + e.getDistance();
							 other.setDistance(predictDist);
							 other.setPredDistance(actualDist);
							 parentMap.put(other, curr);
							 calDistance.add(other);
						}	
					}
				}
			}
		}
		
		
		return found;
	}

	
	
	public static void main(String[] args)
	{
		System.out.print("Making a new map...");
		MapGraph firstMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
		System.out.println("DONE.");
		
		
		
		MapGraph simpleTestMap = new MapGraph();
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
		
		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
		
		System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
		List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart,testEnd);
		List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart,testEnd);
		
		MapGraph testMap = new MapGraph();
		GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
		
		// A very simple test using real data
		testStart = new GeographicPoint(32.869423, -117.220917);
		testEnd = new GeographicPoint(32.869255, -117.216927);
		System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		
		
		testStart = new GeographicPoint(32.8674388, -117.2190213);
		testEnd = new GeographicPoint(32.8697828, -117.2244506);
		System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		
		
		
		/* Use this code in Week 4 End of Week Quiz */
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);
		// You can use this method for testing.  
		
		
		/* Here are some test cases you should try before you attempt 
		 * the Week 3 End of Week Quiz, EVEN IF you score 100% on the 
		 * programming assignment.
		 */
		/*
		MapGraph simpleTestMap = new MapGraph();
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
		
		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
		
		System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
		List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart,testEnd);
		List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart,testEnd);
		
		
		MapGraph testMap = new MapGraph();
		GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
		
		// A very simple test using real data
		testStart = new GeographicPoint(32.869423, -117.220917);
		testEnd = new GeographicPoint(32.869255, -117.216927);
		System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		
		
		// A slightly more complex test using real data
		testStart = new GeographicPoint(32.8674388, -117.2190213);
		testEnd = new GeographicPoint(32.8697828, -117.2244506);
		System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		*/
		
		
		/* Use this code in Week 3 End of Week Quiz */
		/*MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);

		*/
		
	}
	
}
