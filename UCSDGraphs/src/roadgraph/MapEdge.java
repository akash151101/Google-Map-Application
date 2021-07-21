package roadgraph;

import geography.GeographicPoint;

//import geography.GeographicPoint;
//import java.util.List;
//import java.util.Set;

public class MapEdge {
     private MapNode start;
     private MapNode goal;
     private String RoadName;
     private String RoadType;
     private double Dist;
     
//     private void CalDistance() {
//    	 GeographicPoint source = start.getLocation();
//    	 GeographicPoint target = goal.getLocation();
//    	 
//    	 Dist = source.distance(target);
//     }
     
     public MapEdge(MapNode start, MapNode goal, String RoadName, String RoadType, double Dist) {
    	 this.start = start;
    	 this.goal = goal;
    	 this.RoadName = RoadName;
    	 this.RoadType = RoadType;
    	 this.Dist = Dist;
     }
     
    public MapNode thisNode() {
    	return start;
    }
    
    public MapNode otherNode(MapNode node) {
    	if(node.equals(start))
    		return goal;
    	
    	return start;
    }
    
    public String getRoadName() {
    	return RoadName;
    }
    
    public double getDistance() {
    	return Dist;
    }
    
    
}
