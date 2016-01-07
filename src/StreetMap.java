/*
 * StreetMap.java
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


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;




/*
 * StreetMap class generates a graph based on input file and contains frame to draw graph inside. 
 */
public class StreetMap {
	
	//List of intersection and road objects
	public LinkedList<Intersection> intersectionList;
	
	public LinkedList<Road> roadList;
	
	//List of intersection and road content from file
	public LinkedList<String> intersectionContent;
	public LinkedList<String> roadContent;
	
	//Raw data from file
	public LinkedList<String> fileContent;
	
	//number of vertices in graph
	public int numOfVerticies;
	
	//Our graph
	public Graph graph;
	
	/*
	 * Method takes in a file name and generates a graph based on that file, if file not found informs user.
	 */
	public StreetMap( String fileName , String start, String end){
		
		String currentLine = null; //current line from input file
		
		//Initialize variables
		numOfVerticies = 0;
		
		intersectionList = new LinkedList<Intersection>();
		
		roadList = new LinkedList<Road>();
		
		roadContent = new LinkedList<String>();
		intersectionContent = new LinkedList<String>();
		
		fileContent = new LinkedList<String>();
		
		graph = new Graph();
		
		try{
			
			FileReader fileReader = new FileReader(fileName);
			
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			
			while( ( currentLine = bufferedReader.readLine() ) != null ){ //for each line in the input file
				
				
				if( currentLine.startsWith("i") ){ //We have an intersection
					
					numOfVerticies++;
					
					addToList( true, currentLine ); //Add to intersection list
					
				}
				
				else{//We have a road
					
					addToList( false, currentLine ); //Add to road list
				}
				
				
			}//end of reading content from input file
			
			
			
			
			//Create the Intersection and Road objects
			
			createIntersections();
			
			createRoads();
		
			//calculate shortest path here
			System.out.println("Compute shortest path from " + start + " to " + end);
			graph.dijkstra(graph.getIntersection(start));
			
			System.out.println("Print shortest path as a series of vertices: ");
			graph.printPath(graph.getIntersection(end));
			
			System.out.println("\nPrint shortest path as a series of roads: ");
			graph.generateShortPathRoads();
			
			System.out.println(graph.shortestPathRoadList);
			
			
			//calculate minimum spanning weight tree here
			
			System.out.println();
			
			System.out.println("Compute minimum spanning tree. Takes at maximum 9 to 10 seconds for largest graph file.");
			System.out.println("It then takes approximately 19 seconds for all the roads to print out in the largest graph, then my GUI will appear.");
			graph.minimumSpanningTreeAlg();
			
			//System.out.println(graph.minimumSpanningTree);
			//Draw frame and draw the graph
			JFrame frame = new JFrame();
			
			frame.setTitle("Street Map");
			 
			 frame.setSize(1000, 700);
			 
			 
			 //draw different graphs here
			 
			 JPanel view = graph.drawGraph();
			 
			 JPanel viewShortestPath = graph.drawShortestPath();
			 
			 JPanel viewMinSpanTree = graph.drawMinSpanTreeView();
			 
			 JTabbedPane tabbedPane = new JTabbedPane();
			 
			 tabbedPane.addTab("Initial Graph", view);
			 
			 tabbedPane.add("Shortest Path Graph: Colored Blue", viewShortestPath);
			 
			tabbedPane.add("Minimum Spanning Tree: Colored Red (NOTE: Allow to load for 4 to 5 secs for largest graph)", viewMinSpanTree);
			
			frame.add(tabbedPane);
			 
			 
			 frame.setVisible(true);
			 
			frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
			
			
			
			
			
			
			bufferedReader.close();
			
		}
		
		catch(FileNotFoundException ex) {
            System.out.println("Unable to open input file " + fileName);                
        }
		
        catch(IOException ex) {
            System.out.println("Error reading input file "+ fileName);                  
        }
		
		
		
	}//end of constructor
	
	/*
	 * Initializes intersection objects and places them inside the graph
	 */
	public void createIntersections(){
		
		String id;
		double x, y;
		Intersection intersection;
		
		while( intersectionContent.isEmpty() == false ){ //filter through all intersection content and place in graph
			
			
			if( intersectionContent.peek().equals("i") ){
				
				intersectionContent.poll();
				
				id = intersectionContent.poll();
				
				x = Double.parseDouble( intersectionContent.poll() );
				
				y = Double.parseDouble( intersectionContent.poll() );
				
				intersection = new Intersection( id, x, y );
				
				//add intersection to graph here
				graph.insert(intersection);
				
				
			}
			
			
		}
	}//end of method createIntersections
	
	/*
	 * Initializes road objects and places them inside the graph
	 */
	public void createRoads(){
		
		Road road;
		String intersection1;
		String intersection2; 
		String id;
		
		while( roadContent.isEmpty() == false ){ //filter through all road content and place in graph
			
			if(roadContent.peek().equals("r") ){
				
				roadContent.poll();
				
				id = roadContent.poll();
				
				intersection1 = roadContent.poll();
				
				intersection2 = roadContent.poll();
				
				road = new Road( id, intersection1, intersection2 );
				
				graph.addRoadConnection(road);
				
			}
		}
		
	}//end of method createRoads
	
	/*
	 * Puts file content into appropriate list, i.e. either an intersection or road list
	 */
	public void addToList( boolean isIntersection , String currentLine ){
		
		
		//Look through string if not blank put into a queue or something, if you hit a blank stop concatenating
		//and wait till you hit another item then do it again
		
		
		String element = "";
		
		for(int i = 0; i < currentLine.length(); i++){
			
			if( !( Character.isWhitespace( currentLine.charAt(i) ) )   ){
				
				
				element = element + currentLine.charAt(i);
			}
			
			else{
			
				if( isIntersection == true )
					intersectionContent.add(element);
				else
					roadContent.add(element);
			
				
				element = "";
			}
			
		}
		
		if( !(element.equals("")) ){
			
			if( isIntersection == true )
				intersectionContent.add(element);
			else
				roadContent.add(element);
		}
		
	}//end of method addToList
	
	
	

}//end of class StreetMap
