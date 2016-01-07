/*
 * Road.java
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


/*
 * Road class represents the edges in my graph implementation. Contains a unique id, distance of road,
 * and two intersections that connect the road, intersection a and b. Provides methods to tell if a duplicate road,
 * retrieve the road, if two roads have duplicate destinations, and a comparing method. 
 * 
 * Implements Comparable so I can use java's Priority Queue implementation, so I can define this class'
 * natural ordering to be used for java's library
 */
public class Road implements Comparable<Road>{
	
	//an road (edge) from a to b
	public final String a, b; //two vertices between road
	
	public final String id;//road's unique id
	
	public double distance;//distance of the road 
	
	//constructor to create a road
	public Road( String id, String a, String b ){
		
		this.id = id;
		this.a = a;
		this.b = b;
		distance = 0;
	}
	
	/*
	 * Returns a road, according to two given intersections
	 */
	public Road getRoad( String a , String b ){
		
		if( a.equals( this.a ) && b.equals( this.b ) )
			return this;
		
		return null;
	}
	
	/*
	 * Returns true if the road passed in is going to the same destination (id B, is equivalent)
	 */
	public boolean isDuplicateDestination( Road road ){
		
		if( this.b.equals(road.b) )
			return true;
		
		return false;
	}
	
	
	
	/*
	 * Returns true if the road is actually the same Road, except the Id's are in a different order.
	 * 
	 * E.g.
	 * 
	 * Road: A - B
	 * Road: B - A
	 * 
	 * These are duplicate roads 
	 */
	public boolean isDupRoad( Road road ){
		
		if( a.equals( road.b ) && b.equals( road.a ) )
			return true;
		
		return false;
	}

	/*
	 * If the road is less than return negative, if greater than return positive, else return 0
	 * 
	 * This is a class' natural ordering to be used for java's priority queue
	 */
	@Override
	public int compareTo(Road arg0) {
		
		if( this.distance == arg0.distance )
			return 0;
		
		else if( this.distance < arg0.distance )
			return -1;
		
		else
			return 1;
		
	}
	
	/*
	 * Returns a string representation of the road object
	 */
	@Override
	public String toString(){
		return a + " to " + b;
	}
	

}//end of class Road
