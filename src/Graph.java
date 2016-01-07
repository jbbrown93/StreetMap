/*
 * Graph.java
 * 
 * Version 7
 * 
 * Copyright Jordan Brown
 * 
 * Course: CSC 172 FALL 2015
 * 
 * Project 4
 * 
 * Last Revised: December 12, 2015
 */

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.PriorityQueue;

import javax.swing.JPanel;


/*
 * Graph class creates and provides methods for a graph with intersections representing vertices and roads
 * representing edges. Contains methods to draw itself, compute the shortest path from a start vertex to an
 * end vertex, and computes the minimum spanning tree.
 */
public class Graph {
	
	
	public HashMap< String, Intersection > graph; //our graph
	
	public LinkedList<Road> listOfRoads; //list of all the roads
	
	public static final double INFINITY = 10000000; //initial constant for unknown distances
	
	//list of a series of roads and intersections for shortest path
	public LinkedList<Road> shortestPathRoadList;
	public LinkedList<Intersection> shortestPathInterList;
	
	//list of roads for minimum spanning tree
	public LinkedList<Road> minimumSpanningTree;
	
	//constructor
	public Graph(){
		
		graph = new HashMap< String, Intersection >();
		
		listOfRoads = new LinkedList<Road>();
		
		shortestPathRoadList = new LinkedList<Road>();
		
		shortestPathInterList = new LinkedList<Intersection>();
		
		minimumSpanningTree = new LinkedList<Road>();
	}//end of constructor
	
	/*
	 * inserts an intersection in the graph
	 */
	public void insert( Intersection intersection ){
		
		graph.put( intersection.id ,  intersection );
	}
	
	
	/*
	 * returns true if an intersection is connected to another intersection, false otherwise
	 */
	public boolean connected( Intersection node1 , Intersection node2 ){
		
		ListIterator<Intersection> pathItems = node1.getAllPath();
		
		while( pathItems.hasNext() )
			
			if( pathItems.next().id.equals(node2.id) )
				return true;
		
		
		return false;
	}
	
	/*
	 * Returns specified intersection according to it's ID
	 */
	public Intersection getIntersection( String id ){
		
		
		return graph.get(id);
	}
	
	/*
	 * Adds a road connection to between intersections, and modifies the intersection path, add the road to list of
	 * roads, and calculates the weights of the roads. 
	 */
	public void addRoadConnection( Road road ){
		
		Intersection A = graph.get(road.a);
		
		Intersection B = graph.get(road.b);
		
		A.addToPath(B, road);
		B.addToPath(A, road);
				
		calculateEdgeWeights(road);
		
		
		listOfRoads.add(road);

	}
	

	
	/*
	 * Uses the distance formula to calculate distance between two intersections (the road distance)
	 */
	public double calculateDistance( double x1, double y1, double x2, double y2){
		
		return Math.sqrt( Math.pow(x2 - x1, 2) + Math.pow(y2 - y1 , 2) );
		
		
	}
	
	/*
	 * Calculates the edge distance for a given road. 
	 */
	public void calculateEdgeWeights(Road current){
		
		double x1, y1, x2, y2;
		Intersection node1, node2;
		
			
			node1 = getIntersection( current.a );
			
			node2 = getIntersection( current.b );
			
			x1 = node1.x;
			y1 = node1.y;
			
			x2 = node2.x;
			y2 =  node2.y;
			
			current.distance = calculateDistance(x1, y1, x2, y2);
			
	}
	
	
	/*
	 * Returns the JPanel for drawing the initial graph
	 */
	public JPanel drawGraph(){
		
		return new View();		
		
	}
	
	/*
	 * View class is a JPanel that constructs the initial graph according to the list of all roads
	 */
	public class View extends JPanel{
		
		
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			
			
			ListIterator<Road> list = listOfRoads.listIterator();
			
			Road current;
			int x1, y1, x2, y2;
			Intersection node1, node2;
			
			while( list.hasNext() ){
				
				current = list.next();
				
				node1 = getIntersection( current.a );
				
				node2 = getIntersection( current.b );
				
				x1 = (int) node1.x;
				y1 = (int) node1.y;
				
				x2 = (int) node2.x;
				y2 = (int) node2.y;
				
				
				
				g.drawLine(x1, y1, x2, y2);
			}
			
			
		}
	}
	
	

	/*
	 * Returns the JPanel for drawing the shortest path graph
	 */
	public JPanel drawShortestPath(){
		
		return new ShortestPathView();
	}
	
	/*
	 * ShortestPathView class is a JPanel that constructs the initial graph with black lines and colors the 
	 * shortest path as blue lines 
	 */
	public class ShortestPathView extends JPanel{
		
		
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			
			ListIterator<Road> initial = listOfRoads.listIterator();
			
			
			Road current;
			int x1, y1, x2, y2;
			Intersection node1, node2;
			
			while( initial.hasNext() ){
				
				current = initial.next();
				
				
				node1 = getIntersection( current.a );
				
				node2 = getIntersection( current.b );
				
				x1 = (int) node1.x;
				y1 = (int) node1.y;
				
				x2 = (int) node2.x;
				y2 = (int) node2.y;
				
				
				if(shortestPathRoadList.contains(current))
					g.setColor(Color.BLUE);
				else
					g.setColor(Color.BLACK);
				
				g.drawLine(x1, y1, x2, y2);
				
			}
			
			
		}
	}
	
	/*
	 * Returns the JPanel for drawing the minimum spanning tree graph
	 */
	public JPanel drawMinSpanTreeView(){
		
		return new MinimumSpanTreeView();
	}
	
	/*
	 * MinimumSpanTreeView class is a JPanel that constructs the initial graph with black lines and colors the 
	 * shortest path as red lines 
	 */
	public class MinimumSpanTreeView extends JPanel{
		
		
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			
			
			ListIterator<Road> list = listOfRoads.listIterator();
			
			Road current;
			int x1, y1, x2, y2;
			Intersection node1, node2;
			
			while( list.hasNext() ){
				
				current = list.next();
				
				node1 = getIntersection( current.a );
				
				node2 = getIntersection( current.b );
				
				x1 = (int) node1.x;
				y1 = (int) node1.y;
				
				x2 = (int) node2.x;
				y2 = (int) node2.y;
				
				
				if( minimumSpanningTree.contains(current) )
					g.setColor(Color.RED);
				else
					g.setColor(Color.BLACK);
				g.drawLine(x1, y1, x2, y2);
			}
			
			
		}
	}
	
	/*
	 * Finds and returns the minimum road in the list of all roads
	 */
	public Road findMinRoad(){
		
		ListIterator<Road> roads = listOfRoads.listIterator();
		
		Road minRoad = roads.next();
		Road current;
				
		while( roads.hasNext() ){
					
			current = roads.next();
					
			if(current.distance < minRoad.distance)
						
				minRoad = current;	
		}
		
		return minRoad;
	}
	
	
	
	
	/*
	 * Generates the minimum spanning tree for my graph using Prim's algorithm:
	 * 
	 * Find the smallest edge, and put the vertex in the tree (vertex a). Your current vertex is now vertex a
	 * 
	 * Repeat the following:
	 * 	Find all the edges from the newest vertex to the other vertices that aren't in the tree. 
	 * 	Put these edges in the priority queue 
	 * 
	 * 	Pick the edge with the lowest weight and add this edge and its destination vertex to the tree
	 */
	public void minimumSpanningTreeAlg(){
		
		Road minRoad = findMinRoad();
		
		LinkedList<Intersection> vertexTree = new LinkedList<Intersection>();
		
		LinkedList<Road> edgeTree = new LinkedList<Road>();
		
		Intersection currentVertex = graph.get(minRoad.a);
		
		vertexTree.add(currentVertex);
		
		
		PriorityQueue<Road> queue = new PriorityQueue<Road>();
		
		HashMap<String, Road> set = new HashMap<String, Road>();//keep roads contained in tree
		
		int count = 0; //number of edges in graph
		
		
		while( count < graph.size() - 1 ){ //total number of edges in minimum spanning tree is one less than graph size
			
			LinkedList<Intersection> neighbors = currentVertex.getPath();
			
			
			while( neighbors.isEmpty() == false ){
				
				Intersection adjacentVertex = neighbors.poll();
				

				if( vertexTree.contains(adjacentVertex) == false){

					
					Road currentEdge = currentVertex.getRoad(adjacentVertex);
				
					
					if( set.get(adjacentVertex.id) != null ){ //replace edge with the smaller one if same destination
						
						Road temp = set.get(adjacentVertex.id);
						
						if( currentEdge.distance < temp.distance){
							
							//update queue
							queue.remove(temp);
							
							queue.add(currentEdge);
						}
					}
					else{
						queue.add(currentEdge);
					}
				}
				
				
			}
			
			//Add smallest road and destination vertex to the tree
			if( queue.isEmpty() == false){

				minRoad = queue.poll();
				
				currentVertex = graph.get(minRoad.b);
				
				if( vertexTree.contains(currentVertex) ){
					
					currentVertex = graph.get(minRoad.a);
				}
				
			
				vertexTree.add(currentVertex);
				
				set.put(currentVertex.id, minRoad);
				
				edgeTree.add(minRoad);
				
				count++;
			}
			
		
						
		}
		
//		System.out.println( graph.size());
//		System.out.println( count);
		for(Road r: edgeTree){
			System.out.println(r);
		}
		minimumSpanningTree = edgeTree;
	}
	
	
	/*
	 * Generates the weighted, shortest path for the given start vertex to every vertex in graph. 
	 * 
	 * Uses dijkstra's algorithm as a guidelines as outlined in Weiss textbook.
	 */
	public void dijkstra( Intersection start ){
		
		Intersection startVertex = graph.get(start.id);
		
		PriorityQueue<Intersection> queue = new PriorityQueue<Intersection>();
		
		LinkedList<Intersection> vertexList = new LinkedList<Intersection>();
		
		vertexList.addAll(graph.values());
		
		int unknownCount = 0; 
		
		for(Intersection current: vertexList){ //initialize all vertices to appropriate values
			
			if( current.id.equals(startVertex.id))
				current.dist = 0;
			
			else
				current.dist = INFINITY;
			
			current.known = false;
			unknownCount++;
			
			current.parent = null;
			
			queue.add(current);
			
		}
		
		
		
		while( unknownCount > 0 ){ //begin algorithm and continue until no more unknowns remain (keeping a count)
			
			Intersection currentVertex = graph.get(queue.poll().id);
			
			currentVertex.known = true;
			
			unknownCount--;
			
			
			LinkedList<Intersection> adjVertices = currentVertex.getPath();
			
			for(Intersection adjVertex: adjVertices){
				
				if( adjVertex.known == false){
					
					double edgeCost = currentVertex.getRoad(adjVertex).distance;
					
					if( currentVertex.dist + edgeCost <  adjVertex.dist ){
						
						adjVertex.dist = currentVertex.dist + edgeCost;
						
						adjVertex.parent = currentVertex;
						
						queue.remove(adjVertex);
						
						queue.add(adjVertex);
					}
				}
			}
			
			
		}
			
	}
	
	
	
	
	/*
	 * Prints the path from the starting vertex given at beginning of program to the intersection passed in
	 */
	public void printPath( Intersection v ){
		
		
		if( v.parent != null){
			
			printPath(v.parent);
			System.out.print( " to " );
		}
		
		System.out.print( v );
		
		shortestPathInterList.add(v);
		
	}
	
	/*
	 * After running dijkstra algorithm, generates roads that represent the shortest path
	 */
	public void generateShortPathRoads(){
		
		Intersection start = shortestPathInterList.poll();
		Intersection current = shortestPathInterList.poll();
		
		
		if( start != null && current != null){
			
			Road road = start.getRoad(current);
			
			shortestPathRoadList.add(road);
			
			while(shortestPathInterList.isEmpty() == false){
				
				Intersection next = shortestPathInterList.poll();
				
				road = current.getRoad(next);
				
				shortestPathRoadList.add(road);
				
				current = next;
				
			}
		}
		
	}

	
/*
 * A main method that contains a small graph I made to test my shortest path and minimum spanning tree algorithm and
 * other methods for my graph implementation
 */
public static void main(String[] args){
		
	//Test small scale graph
		Graph test = new Graph();
		
		Intersection a = new Intersection( "A", 400.1, 420.1);
		
		Intersection b = new Intersection( "B", 420.5, 320.1);
		
		Intersection c = new Intersection( "C", 490.1, 200.1);
		
		Intersection d = new Intersection( "D", 300.2, 100.1);
		
		Intersection e = new Intersection("E", 550.4, 300.1);
		
		Intersection f = new Intersection("F", 200.5, 350.1);
		
		Intersection h = new Intersection("H", 440.5, 500.2);
		
		
	
		
		Road r1 = new Road("r1", "A" , "B");
		Road r2 = new Road("r2", "A" , "C");
		Road r3 = new Road("r3", "B", "H");
		Road r4 = new Road("r4", "B", "E");
		Road r5 = new Road("r5", "C", "F");
		Road r6 = new Road("r6", "C", "D");
		Road r7 = new Road("r7", "F", "D");
		
		
		test.insert(a);
		test.insert(b);
		test.insert(c);
		test.insert(d);
		test.insert(e);
		test.insert(f);
		test.insert(h);
		
		test.addRoadConnection(r2);
		test.addRoadConnection(r1);
		test.addRoadConnection(r3);
		test.addRoadConnection(r4);
		test.addRoadConnection(r5);
		test.addRoadConnection(r6);
		test.addRoadConnection(r7);
		
//		System.out.println(r1 + " " + r1.distance);
//		System.out.println( r2 + " " + r2.distance);
//		System.out.println( r3 + " " + r3.distance);
//		System.out.println( r4 + " " +  r4.distance);
//		System.out.println( r5 + " " + r5.distance);
//		System.out.println(r6 + " " + r6.distance);
//		System.out.println( r7 + " " +r7.distance);
//		
//		System.out.println();
		
		
		System.out.println("Testing shortest path: ");
		test.dijkstra(h);
		
		System.out.println("Shortest Path as intersections: " );
		test.printPath(e);
		
		System.out.println();
		
		System.out.println("Shortest path as roads: ");
		test.generateShortPathRoads();
		
		System.out.println(test.shortestPathRoadList);
		
		
		System.out.println("Testing minimum spanning tree: " );
		test.minimumSpanningTreeAlg();
		
		
	
		
	}

}
