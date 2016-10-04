/**
 * You are to use Quicksort to sort N randomly generated integers and then analyze various characteristics. The project
 * will have two parts, one for the basic implementation of the program that sorts the integers, and second for analyzing
 * what you see in terms of performance
 * 
 * Part A) 
 * Desired Program Behavior:
 * 1) User is prompted to enter a value for N as input
 * 2) The program generates N integers which are either a) sorted in decreasing order, or b) All integers are picked randomly
 * with uniform distribution from the range [0, MAX_RANGE]. Use MAX_RANGE = 10000. For part (a) the program should ask an input
 * X from the user and set the first element of the array to N-X, and the second element to N-2X and so on. 
 * (NOTE: N & X are positive ints)
 * 3) The program sorts these N integers using QuickSort and Randomized QuickSort and outputs a file output.txt with original
 * and sorted values.
 * 4) The program also outputs the computation time T(N) for sorting N integers. Be sure that this time includes only the computation 
 * time and not the time spent interacting with user and or generating the inteers.
 * 
 * You may use any implementation of Quicksort that you prefer, and also any programming language of choice.
 * 
 * Part B)
 * (1) Compute the averae case T(N) for N = 10, 100, 1000, 10000 for five different runs of QuickSort with each value. Integers
 * are to be picked randomly from [0, MAX_RANGE] (Part A.2.b above). For example, for N = 10, run your program five times to get
 * five values of T(N). Then take the average of these readings to get average T(N) = 10.
 * (2) Repeat (1) but this time use RandomizedQuickSort to compute T'(N) for N = 10, 100, 1000, 10000
 * (3) Repeat (1) using part A.2.a as above to compute T"(N) for N = 10, 100, 1000, 10000
 * (4) Repeat (3) using RandomizedQuickSort to compute T'''(N) for N = 10, 100, 1000, 10000
 * 
 * (4) Plot T(N), T'(N), T''(N), T'''(N) along with the functions N, NlgN, and N^2 for N = 10, 100, 1000, 10000
 * 
 * Comment on the asymptotic running times of T(N), T'(N), T''(N), T'''(N) in comparison to the other functions and explain your 
 * reasoning in detail 
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Random;
//import java.util.Random;
import java.util.Scanner;

/**
 * @author allen
 * @version 1
 */
public class QuickSortTest {
	/* * * * * * * * * * * * * * * * * * * * * *
	 * Constants to increase code readability  *
	 * * * * * * * * * * * * * * * * * * * * * */
	private static final String RUN_COUNT_FILE = "RunCountFile.txt";
	private static final String OUTPUT_FILE = "output.txt";
	private static final int MAX_RANGE = 10000;				// increase if you intend to increase your input N to be greater than 10k
	private static final boolean SORTED = true;
	private static final boolean RANDOM = true;
	private static final boolean DECREASING = true;
	private static final boolean APPEND = true;
	
	protected static Long startTime;	// variables to store time in nanoseconds
	protected static Long endTime; 	
	
	/**
	 * main() executes the required steps to accomplish the assignment requirements
	 * @param args n/a
	 */
	public static void main(String[] args) {
		
		Random generator = new Random(MAX_RANGE);	// instantiate a random number generator 
		Scanner userInput = new Scanner(System.in);		// instantiate a scanner to read user input
		boolean correctX = false;							// boolean values to maintain a loop until user has entered correct input
		boolean correctN = false;
		int nValue = 10;					// default values of n and x are 10 and 1, respectively (java doesnt like uninitialized var
		int xValue = 1;
		int runNumber = fetchRunNumber() + 1;		// get the number of times the program has been run for increased output.txt readability
		
		if (args.length != 0) {
			if (args.length != 2) {
				System.out.println("You have entered an incorrect amount of arguments at the command line.\n\n");
			} else {
				try {
					nValue = Integer.parseInt(args[0]);
					if(nValue < 0) {
						throw new IllegalArgumentException();
					}
					correctN = true;
				} catch (IllegalArgumentException n) {
					System.out.println("Only positive integer values are accepted as input");
				}

				try {
					xValue = Integer.parseInt(args[1]);
					correctX = true;
				} catch (IllegalArgumentException n) {
					System.out.println("Only positive integer values are accepted as input");
				}
			}
		}
		
		while(!correctN && !correctX) {		//loop until we get the necessary info from user
			
			if(!correctN) {												// getting the correct N
				System.out.print("Enter a value for N as input: ");
				try {
					nValue = Integer.parseInt(userInput.nextLine());
					if(nValue < 0) {
						throw new IllegalArgumentException();
					}
					correctN = true;
				} catch (IllegalArgumentException n) {
					System.out.println("Only positive integer values are accepted as input");
					continue;
				}
			}
			
			if (!correctX) {											// getting the correct X
				System.out.print("Enter a value for X: ");
				try {
					xValue = Integer.parseInt(userInput.nextLine());
					correctX = true;
				} catch (IllegalArgumentException n) {
					System.out.println("Only positive integer values are accepted as input");
				}
			}
			
		}
		
		int multiplier = 1;							// increase multiplier by 1 on each iteration (per programming assignment requirements)
		int[] nArrayA, nArrayB, nArrayC, nArrayD;		// arrays for testing
		nArrayA = new int[nValue]; 
		nArrayB = new int[nValue]; 
		nArrayC = new int[nValue]; 
		nArrayD = new int[nValue];
		
		for (int i = 0; i < nArrayA.length; i++, multiplier++) {	// input numbers into the arrays
			nArrayA[i] = nValue - (multiplier * xValue);			// arrayA will hold decreasing integers
			nArrayC[i] = generator.nextInt(MAX_RANGE);				// arrayC will contain randomly generated numbers
		}
		
		nArrayB = nArrayA.clone();	// A & B are both decreasing numbers
		nArrayD = nArrayC.clone();	// C & D are both randomly generated
		

		/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 * 																												   *  
		 *  Part A states that the array should be structured in a manner that each subsequent element is less than the    *
		 *  previous elements. we use the multiplier and user input 'X' in order to perform this 						   *
		 *  																											   *
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
		
		/*
		 *  Quicksort of Part A.2.a
		 */
		outputToFile(nArrayA, !SORTED, !RANDOM, DECREASING, runNumber, nValue);
		
		startTimer();
		QuickSort.sort(nArrayA, 0, nValue - 1);
		endTimer();
		
		outputToFile(nArrayA, SORTED, !RANDOM, DECREASING, runNumber, nValue);
		
		/*
		 *  Randomized Quicksort of Part A.2.a
		 */
		outputToFile(nArrayB, !SORTED, RANDOM, DECREASING, runNumber, nValue);
		
		startTimer();
		RandomizedQuickSort.randomSort(nArrayB, 0, nValue - 1);
		endTimer();
		
		outputToFile(nArrayB, SORTED, RANDOM, DECREASING, runNumber, nValue);
		
		/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 * 																												   *  
		 *  Part B states that All integers are picked randomly with a uniform distribution from the range [0, MAX_RANGE]  *
		 *  																											   *
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
		
		/*
		 *  Quicksort of Part A.2.b
		 */
		outputToFile(nArrayC, !SORTED, !RANDOM, !DECREASING, runNumber, nValue);
		
		startTimer();
		QuickSort.sort(nArrayC, 0, nValue - 1);
		endTimer();
		
		outputToFile(nArrayC, SORTED, !RANDOM, !DECREASING, runNumber, nValue);
		
		/*
		 *  Randomized Quicksort of Part A.2.b		//Theoretically, there should be no benefit by doing this
		 */
		outputToFile(nArrayD, !SORTED, RANDOM, !DECREASING, runNumber, nValue);
		
		startTimer();
		RandomizedQuickSort.randomSort(nArrayD, 0, nValue - 1);
		endTimer();
		
		outputToFile(nArrayD, SORTED, RANDOM, !DECREASING,runNumber, nValue);
		
		userInput.close();
		writeRunNumber(runNumber);
	}
	
	/**
	 * outputToFile is a helper function used by main 
	 * to print all of the results to a text file
	 */
	public static void outputToFile(int[] nArray, boolean sorted, boolean random, boolean decreasing, int runNumber, int nValue) {       
		
		try {
																				
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(OUTPUT_FILE, APPEND)));
            
			if (!sorted) {
            	if (random && decreasing) {
	            	out.println("\n\n################################## |" + " Decreasing, Randomized Quick Sort Stats " + " | ##################################"  + " (2)");
	            	System.out.println("\n\n################################## |" + " Decreasing, Randomized Quick Sort Stats " + " | ##################################"  + " (2)");
            	} else if (!random && decreasing) {
            		out.println("\n\n################################## |" + " Decreasing, Non-random Quick Sort Stats " + " | ##################################"  + " (1)");
            		System.out.println("\n\n################################## |" + " Decreasing, Non-random Quick Sort Stats " + " | ##################################"  + " (1)");
            	} else if (!random && !decreasing) {
            		out.println("\n\n################################## |" + " Randomly Generated, Non-random Quick Sort Stats " + " | ##################################"  + " (3)");
            		System.out.println("\n\n################################## |" + " Randomly Generated, Non-random Quick Sort Stats " + " | ##################################"  + " (3)");
            	} else {
            		out.println("\n\n################################## |" + " Randomly Generated, Randomized Quick Sort Stats " + " | ##################################"  + " (4)");
            		System.out.println("\n\n################################## |" + " Randomly Generated, Randomized Quick Sort Stats " + " | ##################################"  + " (4)");
            	}
    
            	out.println("\t\t\t################################## |" + " Test Run Number: " + runNumber + " | ##################################\n");
            	System.out.println("\t\t\t################################## |" + " Test Run Number: " + runNumber + " | ##################################\n");
            	
            	for (int i = 0; i < nArray.length; i++) {
            		out.print(Integer.toString(nArray[i]));
        			if (!(i == nArray.length - 1)) {
        				out.print(", ");
        			}
        		}
            	
            	out.println("\n");
            	
            } else {
            	for (int i = 0; i < nArray.length; i++) {	
            		out.print(Integer.toString(nArray[i]));
        			if (!(i == nArray.length - 1)) {
        				out.print(", ");
        			}
        		}
            
            	out.println("\n");
            	out.print("T(n) = " + Long.toString(getRuntime()) + " nanoseconds, n = " + Integer.toString(nValue) + "\n");
            	System.out.print("T(n) = " + Long.toString(getRuntime()) + " nanoseconds, n = " + Integer.toString(nValue) + "\n");
            }
			out.close();
		} catch (IOException e) {		// catch block 
			System.out.println(e);
            System.out.println("Problem writing to the file statsTest.txt");
            System.exit(0);
		}
    }

	/**
	 * fetchRunNumber takes no arguments and simply returns the number of times the program
	 * has been run. This is to attempt to increase readability of output.txt file
	 * @return int value of the number of times the program has been run
	 */
	public static int fetchRunNumber() {
		Scanner readFile = null;
		String lineFromFile;
		int runNumber = 0;
		
		try {
			readFile = new Scanner(new FileInputStream(RUN_COUNT_FILE));
			
			while (readFile.hasNext()) {
				lineFromFile = (readFile.nextLine());// != null
				if (!lineFromFile.contains("#") && !lineFromFile.trim().isEmpty()) {
					runNumber = Integer.parseInt(lineFromFile);
			   }
			}
			
			//close file
			readFile.close();
		} catch (FileNotFoundException e) {
			runNumber = 0;
		}
		
		return runNumber;
	}
	
	/**
	 * writeRunNumber is a function that will open or create a file named
	 * runCountfile.txt and overwrite with the most updated integer.
	 * @param runNumber is the integer number indicating the number of times the file has been run
	 */
	public static void writeRunNumber(int runNumber) {
	       try {
	            File statText = new File(RUN_COUNT_FILE);
	            FileOutputStream is = new FileOutputStream(statText);
	            OutputStreamWriter osw = new OutputStreamWriter(is);
	            Writer w = new BufferedWriter(osw);
	            
	            w.write("### This file stores the number of times the program has been executed ###\n\n");
	            w.write(Integer.toString(runNumber) + "\n\n");
	            w.close();
	        } catch (IOException e) {
	            System.out.println("Problem writing to the file " + RUN_COUNT_FILE + "\n\n");
	            System.exit(0);
	        }
	}
	
	/**
	 * getRuntime calculates the runtime of QuickSort (& randomized QuickSort)
	 * @return Long that is the runtime of QUickSort in nanoseconds
	 */
	public static Long getRuntime() {
		return endTime - startTime;
	}
	
	public static void startTimer() {
		startTime = System.nanoTime();
	}
	
	public static void endTimer() {
		endTime = System.nanoTime();
	}
	
}