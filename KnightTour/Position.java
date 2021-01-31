/*
 * @author Alex Acevedo
 * CS 421
 * Project 1 - Knights Tour 
 * 
 */



public class Position implements Comparable{


	//Global Variables used for the methods
	private int edge = 0;
	public int x;
	public int y;


	//Saves an instance of the position
	public Position(int x, int y) {

		this.x = x;
		this.y = y;

	}
	
	//Saves an instance of the edge
	public void SetEdge(int edge){
		
		this.edge = edge;
		
	}
	
	//Returns the edge value
	public int GetEdge() {
		
		return edge;
		
	}
	
	//Getter for x position value
	public int GetX() {
		
		return x;
		
	}
	
	//Getter for y position value
	public int GetY() {
		
		return y;
		
	}

	@Override
	public int compareTo(Object o) {
		
		int compareEdge = ((Position)o).GetEdge();
		return this.edge-compareEdge;
		
	}


}
