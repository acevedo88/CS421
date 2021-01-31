/*
 * @author Alex Acevedo
 * CS 421
 * Project 2 - Shuffle-ness Program by pure recursion approach
 * 
 */

public class ShuffleRec {
	
	//global variables used in the program
	static String X;
	static String Y;
	static String Z;
	static int a;
	static int b;
	static int c;
	static int recurranceNum;
	
	
	//main method to run program
	public static void main(String[] args) {
		
		if(args.length < 3 || args.length > 3) {
			
			//checks if arguments are correct length and format
			System.err.println("Incorrect Argument Format: ");
			System.err.println("Usage: java SuffleRec <String X> <String Y> <String Z> \n");
			
		}
		else {
			
			//if correct run program and store arguments
			String xVal = args[0];
			String yVal = args[1];
			String zVal = args[2];
			
			new ShuffleRec(xVal, yVal, zVal);
		}
		
	}
	
	//Method used to solve ShuffleRec
	private ShuffleRec(String x, String y, String z) {
		
		X = x;
		Y = y;
		Z = z;
		
		a = X.length();
		b = Y.length();
		c = Z.length();
		
		if(c != (a+b)) {
			
			System.err.println("The lengths of X and Y does not equal Z.  Enter correct format.");
			
		}else {
			
			ShuffleRecursion();
			
		}
			
	}
	
	//Method used to print out the results of the recursion 
	private boolean ShuffleRecursion() {
		
		if(ShuffleRecSolver(a, b)) {
			
			System.out.println("yes");
			System.out.println("Number of recursive calls: " + recurranceNum + "\n");
			return false;
			
		}else {
			
			System.out.println("no");
			System.out.println("Number of recursive calls: " + recurranceNum + "\n");
			return true;
			
		}
		
	}
	
	//Method used to solve the problems based on definitions  
	private boolean ShuffleRecSolver(int a, int b) {
		
		
		recurranceNum++;
		
		if((a + b) == 0 && a == 0 && b ==0){
			
			return true;
			
		}
		
		else if ( a > 0 && b == 0) {
			
			if(X.charAt(a-1) == Z.charAt(a+b -1)) {
				
				if(ShuffleRecSolver(a-1, b)) {
					
					return true;
					
				}else {
					
					return false;
					
				}
				
			}else {
				
				return false;
				
			}
		}
		
		else if(a == 0 && b > 0) {
			
			if(Y.charAt(b-1) == Z.charAt(a+b-1)) {
				
				if(ShuffleRecSolver(a, b-1)) {
					
					return true;
					
				}else {
					
					return false;
					
				}
				
			}else {
				
				return false;
			}
			
		}
		
		else if( a > 0 && b > 0) {
			
			if(X.charAt(a-1) == Z.charAt(a+b-1)) {
				
				if(ShuffleRecSolver(a-1, b)) {
					
					return true;
					
				}
				else if(Y.charAt(b-1) == Z.charAt(a+b-1)) {
					
					if(ShuffleRecSolver(a, b-1)) {
						
						return true;
						
					}else {
						
						return false;
						
					}
				}
			}
			
			else if(Y.charAt(b-1) == Z.charAt(a+b-1)){
				
				if(ShuffleRecSolver(a, b-1)) {
					
					return true;
					
				}
				else if(Y.charAt(a-1) == Z.charAt(a+b-1)) {
					
					if(ShuffleRecSolver(a-1, b)) {
						
						return true;
						
					}else {
						
						return false;
						
					}
					
				}
				
			}else {
				
				return false;
				
			}
			
		}
		return false;
	
	}
	
	
}
