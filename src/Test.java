/*
 * Test.java
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
 * Test class provides a main method that takes in input via command line and tests my implementation of the 
 * project requirements.
 */
public class Test {

	//start of main method
	public static void main(String[] args) {
		
		String mapFileName = args[0]; //file location of the map
		
		String startIntersection = args[1]; //string representation of the starting intersection id
		
		String endIntersection = args[2]; //string representation of the ending intersection id
		
		StreetMap test = new StreetMap( mapFileName , startIntersection, endIntersection);
		
	}//end of main method

}//end of class Test
