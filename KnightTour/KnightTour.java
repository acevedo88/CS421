/*
 * @author Alex Acevedo
 * CS 421
 * Project 1 - Knights Tour 
 * 
 */
public class KnightTour {
	
	private static int boardSize;
	private static int xPos;
	private static int yPos;
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		//Checks if there is enough arguments inputed 
		if(args.length < 4 || args.length > 4) {

            System.err.println("Wrong Format.  Please use the following format: "+"\n"+"<0/1/2 (no/heuristicI/heuristicII search)> <n - Matrix Size>"
            		+ " <X - Cordinate> <Y - Cordinate>");
            return;

        }
		
		try 
		{
			//Set the argument values
			boardSize = Integer.parseInt(args[1]);
			xPos = Integer.parseInt(args[2]);
			yPos = Integer.parseInt(args[3]);
			
			//Create a new instance of the KnightBoard
			KnightBoard knightBoard = new KnightBoard();
			
			//Checks to make sure the first argument is only 0, 1, or 2
			if(boardSize <= 2) {
				System.err.println("Board Size cannot be 2 or less.\n");
			}
			
			
			//Basic Search operation
			if(args[0].equals("0")) {
				
				knightBoard.KnightBoard(boardSize, xPos, yPos);
				knightBoard.BasicSearch(xPos, yPos);
				
			}
			//Heuristic I search operation
			else if(args[0].equals("1")) {
				
				knightBoard.KnightBoard(boardSize, xPos, yPos);
				knightBoard.HeuristicISearch(xPos, yPos);
				
			}
			//Heuristic II search operation
			else if(args[0].equals("2")){
				
				knightBoard.KnightBoard(boardSize, xPos, yPos);
				knightBoard.HeuristicIISearch(xPos, yPos);
				
			}
			//Prints error if first argument isn't right
			else {
				System.err.println("Error:  First argument must be 0, 1 or 2");
			}
			
			
		} 
		
		
		
		
		catch (Exception e) 
		{
			
			e.printStackTrace();
			
		}

	}

}
