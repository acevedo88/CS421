import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/*
 * @author Alex Acevedo
 * CS 421
 * Project 3 - Critical Path
 * 
 */

public class CriticalPath {

	//Data structures used to store values and graphs
	private static HashMap<Character, ArrayList<Integer>> adjecencyList;
	private static HashMap<Character, Integer> eGraph;
	private static HashMap<Character, Integer> finishedGraph;
	private static LinkedList<Character> vert;


	//Main method to run the program
	public static void main(String[] args) {

		//checks if the argument is of correct size then checks if its a file
		if(args.length < 1 || args.length > 1) {

			errorMessage();

		}else {
			//try catch method
			try {

				//instantiates all data structure variables
				adjecencyList = new HashMap<>();
				vert = new LinkedList<>();
				eGraph = new HashMap<>();
				finishedGraph = new HashMap<>();

				//methods called to calculate outputs
				File newFile = new File(args[0]);
				fileParser(newFile);
				transformGraph();
				calculateLC();
				finalResults();


			}catch(Exception e){

				e.printStackTrace();

			}

		}

	}

	//
	private static void transformGraph() {

		HashMap<Character, Integer> priorValues = new HashMap<>();
		int previousValue = 0;

		//checks if value is 0 which is end of path
		for(int i = 0; i < vert.size(); i++) {

			if(i == 0) {

				priorValues = transform(priorValues, i , 0);

			}

			ArrayList<Integer> linePaths = adjecencyList.get(vert.get(i));

			//stores each value in matrix 
			for(int j = 0; j < vert.size(); j++) {

				if(linePaths.get(j) != -1) {

					if(i > 0) {

						previousValue = priorValues.get(vert.get(i));

					}

					int value = previousValue + linePaths.get(j);

					if(eGraph.containsKey(vert.get(j))) {

						if(linePaths.get(j) + previousValue > eGraph.get(vert.get(j))) {

							priorValues = transform(priorValues, j, value);

						}

					}else {

						priorValues = transform(priorValues, j, value);

					}	
				}	
			}	
		}
	}

	//Method used to store the previous value and current value into a list
	private static HashMap<Character, Integer> transform(HashMap<Character, Integer> prevValues, int index, int value){

		eGraph.put(vert.get(index), value);
		prevValues.put(vert.get(index), value);
		return prevValues;

	}

	//Method used to calculate the latest completion
	private static void calculateLC() {

		HashMap<Character, Integer> value = new HashMap<>();

		int countValue = 0;

		for(int i = vert.size()-1; i >= 0; i--) {

			//checks for final variable 
			if(vert.get(i) == 'F') {

				value.put('F', eGraph.get('F'));

			}else {

				//Else places values into lists
				ArrayList<Integer> newPath = adjecencyList.get(vert.get(i));

				for(int j = 0; j < newPath.size(); j++) {

					if(newPath.get(j) > -1) {
						if(i < vert.size()-1) { 

							countValue = value.get(vert.get(j)); 

						}
						int val = countValue - newPath.get(j);
						if(!value.containsKey(vert.get(i))) { 

							value.put(vert.get(i), val); 

						}
						else {

							if(val < value.get(vert.get(i))) { value.put(vert.get(i), val); }

						}

					}

				}
			}

		}
		finishedGraph = value;

	}

	//Method used to parse a files and store values into a data structure
	private static void fileParser(File file) throws FileNotFoundException {

		Scanner newScan = new Scanner(file);
		int lineNum = 0;

		//While loop used to scan each character within the text file
		while(newScan.hasNextLine()) {

			String line = newScan.nextLine();
			char[] lineChar = line.toCharArray();
			ArrayList<Integer> linePath = new ArrayList<>();
			char token = ' ';

			//checks if character is a digit or char value and stores in appropriate structure
			for(int i = 0; i < lineChar.length; i++) {

				if(lineNum == 0) {

					if(Character.isLetter(lineChar[i])) {

						adjecencyList.put(lineChar[i], new ArrayList<Integer>());
						vert.add(lineChar[i]);

					}

				}else {

					if(Character.isLetter(lineChar[i])) {

						token = lineChar[i];
					}

					else if(Character.isDigit(lineChar[i])) {

						linePath.add(Character.getNumericValue(lineChar[i]));

					}

					else if(lineChar[i] == '-') {

						linePath.add(-1);
						i+=2;

					}

				}	
			}

			if(linePath.size() > 0) {

				adjecencyList.put(token, new ArrayList<>(linePath));

			}

			lineNum++;
		}

		newScan.close();

	}
	
	//Prints a formated result to the console
    private static void finalResults() {
    	
        System.out.println("Activity Node   EC     LC     SlackTime");
        System.out.println("-----------------------------------------------------");
        
        for(int i = 0; i < vert.size(); i++) {
        	
            int ec = eGraph.get(vert.get(i));
            int lc = finishedGraph.get(vert.get(i));
            int slackTime = lc - ec;
            System.out.printf("%-16s", vert.get(i));
            System.out.printf("%-7s", ec);
            System.out.printf("%-10s", lc);
            System.out.printf("%-1s\n", slackTime); 
            
        }
 
    }

    //Error messages with the correct format
	private static void errorMessage() {

		System.err.println("Invalid usage format.");
		System.err.println("Usage:  java CriticalPath <fileName>");

	}

}
