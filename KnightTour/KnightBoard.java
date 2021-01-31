/*
 * @author Alex Acevedo
 * CS 421
 * Project 1 - Knights Tour 
 * 
 */

import java.util.*;

public class KnightBoard {

	//Global variables used to create the Knight Board
	private int board[][];
	private int heuristicBoard[][];
	private int moves;
	private int boardSize;

	//Global variables used for movements and tracking movements
	private int xMoves[] = {-2, -1, 1, 2, 2, 1, -1, -2};
	private int yMoves[] = {1, 2,  2, 1, -1, -2, -2, -1};
	private final int HORSE_MOVES = 8;
	private int totalMoves = 1;

	private Position pos;


	//Create the object KnightBoard
	public void KnightBoard(int size, int xPos, int yPos) {

		board = new int[size][size];
		heuristicBoard = new int[size][size];
		boardSize = size;
		pos = new Position(xPos, yPos);
		CreateBoard();
		CreateHeuristicBoard();

	}

	//Method used to do the basic search
	public boolean BasicSearch(int xPos, int yPos) {

		board[xPos][yPos] = 1;
		if(!ClockWiseMove(xPos, yPos, 2)) {

			System.out.println("The total number of Moves is " + totalMoves);
			System.out.println("There was no solution found!\n");
			return false;

		}else {

			System.out.println("The total number of Moves is " + totalMoves);
			System.out.println(PrintBoardResults());

		}

		return ClockWiseMove(xPos, yPos, 2);

	}

	//Method used to perform the Heuristic I Search
	public boolean HeuristicISearch(int xPos, int yPos) {

		board[xPos][yPos] = 1;
		
		if(!CloseToEdge(xPos, yPos, 2)) {

			System.out.println("The total number of Moves is " + totalMoves);
			System.out.println("There was no solution found!\n");
			return false;

		}else {
			
			System.out.println("The total number of Moves is " + totalMoves);
			System.out.println(PrintBoardResults());
			
		}
		return CloseToEdge(xPos, yPos, 2);

	}
	
	//Method used to perform the Heuristic II Search
	public boolean HeuristicIISearch(int xPos, int yPos) {
		
		board[xPos][yPos] = 1;
		
		if(!WarndorffRule(xPos, yPos, 2)) {
			
			System.out.println("The total number of Moves is " + totalMoves);
			System.out.println("There was no solution found!\n");
			
		}else {
			
			System.out.println("The total number of Moves is " + totalMoves);
			System.out.println(PrintBoardResults());
			
		}
		return WarndorffRule(xPos, yPos, 2);
		
	}


	//This algorithm is used to move the knight in a clockwise motion
	public boolean ClockWiseMove(int xPos, int yPos, int move) {

		int xNext;
		int yNext;

		//Base case
		if(move > (boardSize*boardSize)) {

			return true;

		}

		//Main for loop of the algorithm
		for(int i = 0; i < HORSE_MOVES; i++) {

			xNext = xPos + xMoves[i];
			yNext = yPos + yMoves[i];

			//Checks if position is in bounds and is an empty spot
			if(IsInBounds(xNext, yNext) && IsEmpty(xNext, yNext)) {

				board[xNext][yNext] = move;
				totalMoves++;

				//Recursion for the algorithm
				if(ClockWiseMove(xNext, yNext, move + 1)) {

					return true;

				}else {

					board[xNext][yNext] = -1;

				}

			}

		}

		return false;

	}
	
	//Algorithm for Heuristic II search
	public boolean WarndorffRule(int xPos, int yPos, int move) {
		
		int xNext;
		int yNext;
		
		//Base Case
		if(move > (boardSize*boardSize)) {
			
			return true;
			
		}
		
		//Array List of position to sort
		ArrayList<Position> arrayPosition = SortedWarndorff(xPos, yPos);
		
		//For each loop that uses sorted positions
		for(Position position: arrayPosition) {
			
			xNext = position.GetX();
			yNext = position.GetY();
			
			//Checks if position is in bounds and is an empty spot
			if(IsInBounds(xNext, yNext) && IsEmpty(xNext, yNext)) {
				
				board[xNext][yNext] = move;
				totalMoves++;
				
				//Recursion for Algorithm
				if(WarndorffRule(xNext, yNext, move + 1)) {
					
					return true;
					
				}else {
					
					board[xNext][yNext] = -1;
					
				}	
			}
		}
		return false;
	}


	//Method used to calculate if the position is close to the edge of the board
	public boolean CloseToEdge(int xPos, int yPos, int move) {

		int xNext;
		int yNext;

		//Base Case
		if(move > (boardSize*boardSize)) {

			return true;

		}

		ArrayList<Position> arrayPosition;
		CreateHeuristicBoard();
		arrayPosition = SortedPositions(xPos, yPos);
		
		for(Position position: arrayPosition) {
			
			xNext = position.GetX();
			yNext = position.GetY();
			
			//Checks if position is in bounds and is an empty spot
			if(IsInBounds(xNext, yNext) && IsEmpty(xNext, yNext)) {
				
				board[xNext][yNext] = move;
				totalMoves++;
				
				if(CloseToEdge(xNext, yNext, move +1)) {
					
					return true;
					
				}else {
					
					board[xNext][yNext] = -1;
					
				}
				
			}
			
		}
		return false;

	}
	
	
	//Creates the matrix board
	private void CreateBoard() {

		for(int i = 0; i < boardSize; i++){

			for(int j = 0; j < boardSize; j++){

				board[i][j] = -1;

			}
		}

	}

	//Used to create the matrix board for Heuristic searches
	public void CreateHeuristicBoard() {

		for(int i = 0; i < boardSize; i++){

			for(int j = 0; j < boardSize; j++){

				heuristicBoard[i][j] = EdgeCalculation(i,j);

			}
		}

	}
	
	//Checks if position is in bounds of the matrix
	public boolean IsInBounds(int xPos, int yPos) {

		return(xPos >= 0 && xPos < boardSize && yPos >= 0 && yPos < boardSize);

	}

	//Checks if position is not take
	public boolean IsEmpty(int xPos, int yPos) {

		return board[xPos][yPos] == -1;

	}
	
	//Calculates the closest position to the edge of the matrix
	public int EdgeCalculation(int xPos, int yPos) {
		
		int value = 1000;
		
		if(Math.abs(0 - xPos) < value) {
			
			value = Math.abs(0 - xPos);
			
		}
		if(Math.abs((boardSize - 1) - xPos) < value){
			
			value = Math.abs((boardSize - 1) - xPos);
			
		}
		if(Math.abs(0 - yPos) < value) {
			
			value = Math.abs(0 - yPos);
			
		}
		if(Math.abs((boardSize - 1) - yPos) < value){
			
			value = Math.abs((boardSize - 1) - yPos);
			
		}
		
		heuristicBoard[xPos][yPos] = value;
		return value;
		
	}
	
	//Method used to sort the positions for Heuristic I
	@SuppressWarnings("unchecked")
	private ArrayList<Position> SortedPositions(int xPos, int yPos){
		
		int xNext;
		int yNext;
		
		ArrayList<Position> arrayPosition = new ArrayList<Position>();
		
		for(int i = 0; i < HORSE_MOVES; i++) {
			
			xNext = xPos + xMoves[i];
			yNext = yPos + yMoves[i];
			
			//Checks if position is in bounds and is an empty spot
			if(IsInBounds(xNext, yNext) && IsEmpty(xNext, yNext)) {
				
				Position position = new Position(xNext, yNext);
				position.SetEdge(heuristicBoard[xNext][yNext]);
				arrayPosition.add(position);
				
			}
			
		}
		Collections.sort(arrayPosition);
		return arrayPosition;
		
	}
	
	//Method used to sort the position for Heuristic II
	@SuppressWarnings("unchecked")
	private ArrayList<Position> SortedWarndorff(int xPos, int yPos){
		
		int xNext;
		int yNext;
		
		ArrayList<Position> arrayPosition = new ArrayList<Position>();
		
		for(int i = 0; i < HORSE_MOVES; i++) {
			
			xNext = xPos + xMoves[i];
			yNext = yPos + yMoves[i];
			
			if(IsInBounds(xNext, yNext) && IsEmpty(xNext, yNext)) {
				
				Position position = new Position(xNext, yNext);
				position.SetEdge(NumberOfMoves(xNext, yNext));
				arrayPosition.add(position);
				
			}
			
		}
		Collections.sort(arrayPosition);
		return arrayPosition;
	}
	
	//Method that returns the number of moves
	private int NumberOfMoves(int xPos, int yPos) {
		
		int xNext;
		int yNext;
		int count = 0;
		
		for(int i = 0; i < HORSE_MOVES; i++) {
			
			xNext = xPos + xMoves[i];
			yNext = yPos + yMoves[i];
			
			//Checks if position is in bounds and is an empty spot
			if(IsInBounds(xNext, yNext) && IsEmpty(xNext, yNext)) {
				
				count++;
				
			}
			
		}
		return count;
		
	}

	//Uses a string builder to print the results of the board
	public String PrintBoardResults() {

		StringBuilder string = new StringBuilder();

		for(int i = 0; i < boardSize; i++) {

			for(int j = 0; j < boardSize; j++) {

				int tempBoard = board[i][j];
				String tempString = String.format("%-2s", tempBoard);
				string.append(tempString + " ");

			}

			string.append("\n");

		}

		return string.toString();
	}
	

}
