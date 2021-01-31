/*
 * @author Alex Acevedo
 * CS 421
 * Project 2 - Shuffle-ness Program by Dynamic Programming approach
 * 
 */
import java.sql.Ref;


public class ShuffleDP {

	//global variables
	static String X;
	static String Y;
	static String Z;
	static int a;
	static int b;
	static int c;

	static int dpTable[][];
	static int dpRefNum = 0;

	//Main method used to check arguments
	public static void main(String[] args) {

		//checks that the argument inputed is the correct format
		if(args.length < 3 || args.length > 3) {

			System.err.println("Incorrect Argument Format: ");
			System.err.println("Usage: java SuffleDP <String X> <String Y> <String Z> \n");

		}else {

			//passes arguments into method
			String xVal = args[0];
			String yVal = args[1];
			String zVal = args[2];

			new ShuffleDP(xVal, yVal, zVal);
		}
	}


	//Method used to do most of the passing of variables
	private ShuffleDP(String x, String y, String z) {

		X = x;
		Y = y;
		Z = z;

		a = X.length() + 1;
		b = Y.length() + 1;
		c = Z.length() + 2;

		if(c != (a+b)) {

			System.err.println("The lengths of X and Y does not equal Z.  Enter correct format.");

		}else {

			TableInitializer(a, b);
			ShuffleDPSolver();

		}
	}

	//Creates the table
	private void TableInitializer(int x, int y) {

		dpTable = new int[x][y];

		for(int i = 0; i < x; i ++) {

			for(int j = 0; j < y; j++) {

				dpTable[i][j] = 0;

			}
		}
	}

	//Uses the definitions to help solve the shuffle-ness problems
	private void ShuffleDPSolver() {

		int i = 0;
		int j = 0;

		dpTable[i][j] = 1;

		//base
		for(i = 0; i < X.length(); i++) {

			if(X.charAt(i) == Z.charAt(i)) {

				dpTable[i+1][0] = dpTable[i][0];
				dpRefNum++;

			}else {

				dpTable[i+1][0] = 0;

			}
		}

		//next
		for(j = 0; j < Y.length(); j++) {

			if(Y.charAt(j) == Z.charAt(j)) {

				dpTable[0][j+1] = dpTable[0][j];
				dpRefNum++;

			}else {

				dpTable[0][j+1] = 0;

			}
		}

		//next
		for(i = 0; i < X.length(); i++) {

			for(j = 0; j < Y.length(); j++) {

				if(X.charAt(i) == Z.charAt(i + j + 1)) {

					dpRefNum++;

					if(dpTable[i][j+1] == 1) {

						dpTable[i+1][j+1] = 1;

					}else {

						dpTable[i+1][j+1] = 0;

					}
				}

				if(Y.charAt(j) == Z.charAt(i + j + 1)) {

					dpRefNum++;

					if(dpTable[i+1][j] == 1) {

						dpTable[i+1][j+1] = 1;

					}else {

						dpTable[i+1][j+1] = 0;

					}
				}

				if(X.charAt(i) == Y.charAt(j)) {

					if(dpTable[i][j+1] == 1 | dpTable[i+1][j] == 1) {

						dpTable[i+1][j+1] = 1;

					}else {

						dpTable[i+1][j+1] = 0;

					}
				}

				if((X.charAt(i) != Z.charAt(i + j + 1)) && (Y.charAt(j) != Z.charAt(i + j + 1))) {

					dpTable[i+1][j+1] = 0;

				}
			}	
		}

		ShuffleResults(dpTable, a, b);

	}

	//Method used to print out the results for the program
	private void ShuffleResults(int[][] dpTable, int a , int b) {

		boolean shuffleCheck = false;
		int x = a - 1;
		int y = b - 1;

		for(int i = 0; i < a; i++) {

			for(int j = 0; j < b; j++) {

				int dpBoard = dpTable[i][j];
				String tableString = String.format("%01d ", dpBoard);
				System.out.print(tableString + " ");

			}

			System.out.println();

		}

		while(x > 0 || y > 0) {

			if(x > 0 && dpTable[x-1][y] == 1) {

				x--;

				if(x == 0 && y == 0) {

					shuffleCheck = true;

				}

			}

			else if(y > 0 && dpTable[x][y-1] == 1) {

				y--;
				if(x == 0 && y == 0) {

					shuffleCheck = true;

				}

			}

			else if(x > 0 && y > 0 && dpTable[x-1][y-1] == 1) {

				x--;
				y--;

				if(x == 0 && y == 0) {

					shuffleCheck = true;

				}

			}

			else break;

		}

		if(shuffleCheck) {

			System.out.println("yes");
			System.out.println("Number of recursive calls: " + dpRefNum + "\n");

		}else {

			System.out.println("no");
			System.out.println("Number of recursive calls: " + dpRefNum + "\n");

		}

	}


}
