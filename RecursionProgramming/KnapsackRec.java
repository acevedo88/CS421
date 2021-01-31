/*
 * @author Alex Acevedo
 * CS 421
 * Project 2 - 0-1 Knapsack Program by pure recursion approach
 * 
 */

import java.io.*;
import java.util.*;


public class KnapsackRec {

	//declare variables used for program
	int nItems;
	int weightMax;
	String weightFile;
	String valueFile;
	
	static int[] itemsSelected;
	static int recurrsionNumber;
	
	int[] weightList;
	int[] valueList;
	
	
	//main method to run program
	public static void main(String[] args) throws FileNotFoundException {
		
		//checks if arguments are correct length and format
		if(args.length < 4 || args.length > 4) {

			System.err.println("Incorrect Argument Format: ");
			System.err.println("Usage: java KnapsacRec <n> <W> <w.txt> <v.txt>");

		}
		else {

			//if correct run program and store arguments
			int nValue = Integer.parseInt(args[0]);
			int mValue = Integer.parseInt(args[1]);
			String fileW = args[2];
			String fileV = args[3];
			
			//create a new instance of KnapsacRec and run with variables
			new KnapsackRec(nValue, mValue, fileW, fileV);
			
		}

	}
	
	
	//Knapsack Method
	private KnapsackRec(int n, int w, String fileW, String fileV) throws FileNotFoundException{
		
		//store values into global variables
		nItems = n;
		weightMax = w;
		weightFile = fileW;
		valueFile = fileV;
		
		itemsSelected = new int[nItems +1];
		
		//Methods to determine if file is a file, and to create the output for Knapsack
		ValueFileGenerator(nItems, weightFile, valueFile);
		KnapsackStringGenerator(nItems, weightMax, itemsSelected);
		
		
	}
	
	//method used to determine if file is a file and to store its contents into list arrays
	private void ValueFileGenerator(int n, String fileW, String fileV) throws FileNotFoundException{
		
		@SuppressWarnings("resource")
		Scanner weightScan = new Scanner(new File(fileW));
		@SuppressWarnings("resource")
		Scanner valueScan = new Scanner(new File(fileV));
		
		//instantiate lists with size n
		weightList = new int[n];
		valueList = new int[n];
		
		int i = 0;
		int j = 0;
		
		//parse through weight text file and put values into array list
		while(weightScan.hasNextLine()) {
			
			weightList[i] = Integer.parseInt(weightScan.nextLine());
			i++;
			
		}
		
		//parse through value text file and put values into array list
		while(valueScan.hasNextLine()){
			
			valueList[j] = Integer.parseInt(valueScan.nextLine());
			j++;
			
		}
		
		
	}
	
	
	//method used to get a selected index and print out template
	private boolean KnapsackStringGenerator(int n, int w, int[] itemsSelected) throws FileNotFoundException {
		
		SelectedIndex(n, w, itemsSelected);
		System.out.println("Total Weight: " + w);
		System.out.println("Optimal Value: " + KnapsackRecursion(n, w, itemsSelected));
		System.out.println("Number of recursice calls: " + recurrsionNumber);
		
		return false;
		
	}
	
	//method used to find the selected index and print out its optimal solution
	public void SelectedIndex(int n, int w, int[] itemsSelected) throws FileNotFoundException {
		
		int j = 0;
		int k = 0;
		
		//stores optimal solution into items selected index array
		for(int i = n; i > 0; i--) {
			
			if((w - weightList[i-1] >= 0) && SelectedSolver(i, w, itemsSelected) - SelectedSolver(i-1, w - weightList[i-1], itemsSelected) == valueList[i-1]) {
				
				itemsSelected[k++] = i -1;
				w -= weightList[i-1];
				
			}
			
		}
		
		//prints out optimal solution 
		System.out.println("Optimal Solution: ");
		System.out.print("{");
		
		
		for(j = k-1; j >= 0; j--) {
			
			System.out.print(itemsSelected[j] + 1 + ", ");
			
		}
		
		System.out.println("} ");
		
		
	}
	
	//method used to solve the recursion using its cases.
	public int SelectedSolver(int n, int w, int[] itemsSelected) throws FileNotFoundException {
		
		//base case
		if(n == 0 || w == 0) {
			
			return 0;
			
		}
		
		if(weightList[n-1] > w) {
			
			return SelectedSolver(n-1, w, itemsSelected);
			
		}
		
		else if(weightList[n-1] <= w) {
			
			return FindMax(SelectedSolver(n-1, w, itemsSelected), SelectedSolver(n-1, w-weightList[n-1], itemsSelected) + valueList[n-1]);
			
		}
		
		return SelectedSolver(n, w, itemsSelected);
		
	}
	
	//method to induce recursion
	public int KnapsackRecursion(int n, int w, int[] itemsSelected) {
		
		recurrsionNumber ++;
		
		if(n == 0 || w == 0) {
			
			return 0;
			
		}
		
		if(weightList[n-1] > w) {
			
			return KnapsackRecursion(n-1, w, itemsSelected);
			
		}
		
		else if(weightList[n-1] <= w) {
			
			return FindMax(KnapsackRecursion(n-1, w, itemsSelected), KnapsackRecursion(n-1, w - weightList[n-1], itemsSelected) + valueList[n-1]);
			
		}
		
		return KnapsackRecursion(n, w, itemsSelected);
		
	}
	
	//method used to find the max value
	private int FindMax(int x, int y) {
		
		if(x > y) {
			
			return x;
			
		}else {
			
			return y;
			
		}
		
	}



}
