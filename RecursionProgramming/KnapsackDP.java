/*
 * @author Alex Acevedo
 * CS 421
 * Project 2 - 0-1 Knapsack Program by Dynamic Programming approach
 * 
 */

import java.io.*;
import java.util.*;

public class KnapsackDP {
	
	//global variables used in the program
	int nItems;
	int weightMax;
	String weightFile;
	String valueFile;
	
	int[] weightList;
	int[] valueList;
	
	int dpTable[][];
	static int[] itemsSelected;
	static int tableReference;
	
	
	//main method for program
	public static void main(String[] args) throws FileNotFoundException {

		//checks arguments and determines if they are correct length
		if(args.length < 4 || args.length > 4) {

			System.err.println("Incorrect Argument Format: ");
			System.err.println("Usage: java KnapsacRec <n> <W> <w.txt> <v.txt>");

		}
		else {

			//if arguments are correct, store into variables
			int nValue = Integer.parseInt(args[0]);
			int mValue = Integer.parseInt(args[1]);
			String fileW = args[2];
			String fileV = args[3];
			
			//pass stored variables into KnapsacDP method
			new KnapsackDP(nValue, mValue, fileW, fileV);
			
		}

	}
	
	private KnapsackDP(int n, int w, String fileW, String fileV) throws FileNotFoundException{
		
		//store passed in variables to global
		nItems = n;
		weightMax = w;
		weightFile = fileW;
		valueFile = fileV;
		
		itemsSelected = new int[nItems +1];
		dpTable = new int[nItems + 1][weightMax +1];
		
		//pass in global variables to methods used to calculate table, files, and results
		TableInitialization(nItems, weightMax);
		ValueFileGenerator(nItems, weightFile, valueFile);
		KnapsackResultGenerator(nItems, weightMax, itemsSelected);
		
	}
	
	//Creates the table
	private void TableInitialization(int n, int w) {
		
		dpTable = new int[n + 1][w + 1];

		for(int i = 0; i < n + 1; i ++) {

			for(int j = 0; j < w + 1; j++) {

				dpTable[i][j] = 0;

			}
		}
		
	}
	
	//Checks if file is a file and parses it into lists
	private void ValueFileGenerator(int n, String fileW, String fileV) throws FileNotFoundException{
		
		@SuppressWarnings("resource")
		Scanner weightScan = new Scanner(new File(fileW));
		@SuppressWarnings("resource")
		Scanner valueScan = new Scanner(new File(fileV));
		
		weightList = new int[n];
		valueList = new int[n];
		
		int i = 0;
		int j = 0;
		
		while(weightScan.hasNextLine()) {
			
			weightList[i] = Integer.parseInt(weightScan.nextLine());
			i++;
			
		}
		
		while(valueScan.hasNextLine()){
			
			valueList[j] = Integer.parseInt(valueScan.nextLine());
			j++;
			
		}
		
		
	}
	
	//Method used to generate the results used to display on the console
	private boolean KnapsackResultGenerator(int n, int w, int itemsSeldcted[])throws FileNotFoundException{
		
		KnapsackDPSolver(n, w, itemsSelected);
		
		//prints out the table
		for(int i = 0; i < (n + 1); i++) {
			
			for(int j = 0; j < (w + 1); j++) {
				
				int dpBoard = dpTable[i][j];
				String dpString = String.format("%03d", dpBoard);
				System.out.print(dpString + " ");
				
			}
			System.out.println();
		}
		
		System.out.println();
		SelectedIndex(n, w, itemsSelected);
		System.out.println("Total Weight: " + w);
		System.out.println("Optimal Value: " + dpTable[n][w]);
		System.out.println("Number of table references: " + tableReference);
		
		return false;
		
	}
	
	//method used to calculate tables based on definition
	public int KnapsackDPSolver(int n, int w, int itemsSelected[]) throws FileNotFoundException{
		
		for(int i = 0; i <= n; i++) {
			
			tableReference++;
			
			for(int j = 0; j <= w; j++) {
				
				tableReference++;
				
				if(i == 0 || j == 0) {
					
					tableReference++;
					dpTable[i][j] = 0;
					
				}
				
				else if(weightList[i - 1] > j) {
					
					tableReference++;
					itemsSelected[i] = 0;
					dpTable[i][j] = dpTable[i - 1][j];
					
				}
				
				else if(weightList[i -1] <= j) {
					
					//tableReference++;
					itemsSelected[i] = 1;
					dpTable[i][j] = FindMax(dpTable[i - 1][j], (dpTable[i - 1][j - weightList[i - 1]] + valueList[i - 1]));
					
				}	
			}
		}
		return dpTable[n][w];
	}
	
	
	//finds the max value
	private int FindMax(int x, int y) {
		
		if(x > y) {
			
			return x;
			
		}else {
			
			return y;
			
		}
		
	}
	
	//method used to calculate the optimal solution and print results
	public int SelectedIndex(int n, int w, int[] itemsSelected) throws FileNotFoundException {
		
		int j = 0;
		int y = 0;
		
		for(int i = n; i > 0; i--) {
			
			if((w - weightList[i-1] >= 0) && (dpTable[i][w] - dpTable[i-1][w-weightList[i-1]] == valueList[i-1])) {
				
				itemsSelected[y++] = i - 1;
				
			}
			
		}
		
		System.out.println("Optimal Solution: ");
		System.out.print("{");
		
		
		for(j = y-1; j >= 0; j--) {
			
			System.out.print(itemsSelected[j] + 1 + ", ");
			
		}
		
		System.out.println("} ");
		
		return dpTable[n][w];
	}
	
	
	

}
