# Google-Map-Application
A simple Google Map App. This app helps us to find the shortest path from one location to another location. It also compares the performance of three different shortest finding path algorithm which are used in a variety of real world application.

## Purpose of this Project
* It gives the shortest path from one location to another location using Google Map API 

* It compares the time complexity and number of nodes visited in each algorithms.
## Algorithms used in this Project
### Dijkstra’s shortest path algorithm
Dijkstra's algorithm (or Dijkstra's Shortest Path First algorithm, SPF algorithm) is an algorithm for finding the shortest paths between nodes in a graph, which may represent, for example, road networks. It was conceived by computer scientist Edsger W. Dijkstra in 1956 and published three years later. The algorithm exists in many variants. Dijkstra's original algorithm found the shortest path between two given nodes,but a more common variant fixes a single node as the "source" node and finds shortest paths from the source to all other nodes in the graph, producing a shortest-path tree...[read more](https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-greedy-algo-7/)
### Breadth First Search algorithm
Breadth First Traversal (or Search) for a graph is similar to Breadth First Traversal of a tree. The only catch here is, unlike trees, graphs may contain cycles, so we may come to the same node again. To avoid processing a node more than once, we use a boolean visited array. For simplicity, it is assumed that all vertices are reachable from the starting vertex...[read more](https://www.geeksforgeeks.org/breadth-first-search-or-bfs-for-a-graph/)
### A* Search Algorithm
A* is the most popular choice for pathfinding, because it’s fairly flexible and can be used in a wide range of contexts. A* is like Dijkstra’s Algorithm in that it can be used to find a shortest path. A* is like Greedy Best-First-Search in that it can use a heuristic to guide itself. In the simple case, it is as fast as Greedy Best-First-Search. It often used in many fields of computer science due to its completeness, optimality, and optimal efficiency...[read more](https://www.geeksforgeeks.org/a-search-algorithm/).
## Screenshots
![Image1](https://github.com/akash151101/Google-Map-Application/blob/master/Images/image2.JPG)

![Image2](https://github.com/akash151101/Google-Map-Application/blob/master/Images/image3.JPG)

![Image3](https://github.com/akash151101/Google-Map-Application/blob/master/Images/image4.JPG)

![Image4](https://github.com/akash151101/Google-Map-Application/blob/master/Images/image5.JPG)

![Image5](https://github.com/akash151101/Google-Map-Application/blob/master/Images/image6.JPG)
## Run Locally
### 1. Clone repo

```
$ gh repo clone akash151101/Google-Map-Application
$ cd Google-Map-Application
```

### 2. Get Google Maps API Key 

```
$ Go to https://developers.google.com/maps/documentation/javascript/
$ Click on Get a Key at the top of the page
$ In the project folder, find the file src/html/index.html in the Package Explorer. Right-click on the file and select Open With->Text Editor.
$ Then replace the text <script src="https://maps.googleapis.com/maps/api/js?key=[APIKeyHere]&callback=initMap"></script> --> with the API Key you copied
```

### 3. Run Backend

```
$ open MapApp.java : UCSDGraph->src->application->MapApp.java
$ Run MapApp.java(Alt+Shift+X, J)
```
