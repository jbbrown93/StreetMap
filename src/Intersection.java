/*
 * Intersection.java
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
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

/*
 * Intersection class represents the vertices in my graph implementation. Contains a unique id, vertices path,
 * x and y coordinates, connected road representations, and information for the shortest path algorithm concerning
 * whether it's shortest route is known, it's parent, and it's overall distance.
 * 
 * Implements Comparable so I can use java's Priority Queue implementation, so I can define this class'
 * natural ordering to be used for java's library
 */
public class Intersection implements Comparable<Intersection>{
	
	public final double x, y;
	
	public final String id;
	
	public LinkedList<Intersection> path;

	public HashMap< Intersection , Road > connectingRoads;
	
	public boolean known;
	public double dist;
	public Intersection parent;
	
	//Constructor to initialize variables
	public Intersection( String id, double x , double y ){
		
		this.id = id;
		
		this.x = x; 
		
		this.y = y;
		
		path = new LinkedList<Intersection>();
		
		connectingRoads = new HashMap< Intersection , Road >();
		
		known = false;
		dist = 1000000;
		parent = null;
	
	}
	
	/*
	 * Returns all the road connections the intersection contains
	 */
	public Iterator<Road> getAllRoads(){
		Collection<Road> list = connectingRoads.values();
		
		Iterator<Road> iterator = list.iterator();
		
		return iterator;
	}
	
	/*
	 * Retrieves a road connection to the intersection, according to the intersection it's connected to
	 */
	public Road getRoad( Intersection intersection ){
		
		return connectingRoads.get(intersection);
	}
	
	/*
	 * Adds a road connection to the list of connecting roads
	 */
	public void addRoad( Intersection intersection , Road road ){
		
		connectingRoads.put(intersection,  road);
	}
	
	/*
	 * Gets all the neighbors or adjacent vertices to this intersection
	 */
	public LinkedList<Intersection> getPath(){
		return path;
	}

	/*
	 * Adds an intersection to it's list of neighbors and synchs the road connection
	 */
	public void addToPath( Intersection intersection , Road road){
		
		path.add(intersection);
		
		addRoad( intersection , road );
	
	}
	
	
	/*
	 * Returns an iterator for the neighbors of the adjacent intersections
	 */
	public ListIterator<Intersection> getAllPath(){
		
		return path.listIterator();
	}
	
	/*
	 * Returns a string representation of intersection
	 */
	@Override
	public String toString(){
		return id;
	}

	/*
	 * Methods returns 0 if the distances of the intersection is same as comparison intersection, negative if 
	 * the intersection is less than, and positive if greater
	 */
	@Override
	public int compareTo(Intersection o) {
		if( this.dist == o.dist )
			return 0;
		
		else if( this.dist < o.dist )
			return -1;
		
		else
			return 1;
	}



}//end of Intersection class
