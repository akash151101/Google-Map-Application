package roadgraph;

import java.util.List;

import geography.GeographicPoint;
import util.GraphLoader;

public class MapTester {

	public static void main(String[] args) {
		
		System.out.print("Making a new map...");
		MapGraph firstMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
		System.out.println("DONE.");
		
		System.out.println("Number of nodes: " + firstMap.getNumVertices());
		System.out.println("Number of edges: " + firstMap.getNumEdges()); 
		
		List<GeographicPoint> route = firstMap.bfs(new GeographicPoint(7.0, 3.0), new GeographicPoint(4.0, -1.0));
		System.out.println(route); 
	}

}
