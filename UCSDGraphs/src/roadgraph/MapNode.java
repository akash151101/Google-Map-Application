package roadgraph;


import geography.GeographicPoint;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MapNode implements Comparable {
     private GeographicPoint location;
     private List<MapEdge> Neighbors;
     // Actual distance from source to this node
     private double distance;
     
     // Predicted distance from this node to target
     private double predDistance;
     
     public MapNode(GeographicPoint location) {
    	 this.location = location;
    	 Neighbors = new ArrayList<MapEdge>();
     }
     
     public GeographicPoint getLocation() {
    	 return this.location;
     }
     
     public Set<MapNode> getNeighbors(){
    	 Set<MapNode> adjacentNode = new HashSet<MapNode>();
    	 
    	 for(MapEdge edge: Neighbors) {
    		  MapNode other = edge.otherNode(this);
    		  adjacentNode.add(other);
    	 }
    	 return adjacentNode;
     }
     
     public List<MapEdge> listOfEdges(){
    	  return new ArrayList<MapEdge>(Neighbors);
     }
     
     public void addEdge(MapEdge edge) {
    	 Neighbors.add(edge);
     }
     
     public double getDistance() {
    	 return this.distance;
     }
     
     public void setDistance(double distance) {
    	 this.distance = distance;
     }
     
     public double getPredDistance() {
    	 return this.predDistance;
     }
     
     public void setPredDistance(double distance) {
    	 this.predDistance = distance;
     }
     
     public int compareTo(Object obj) {
    	 MapNode node = (MapNode) obj;
    	 return ((Double)this.getDistance()).compareTo((Double) node.getDistance());
     }
}
