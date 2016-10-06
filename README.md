# QuickSort

Quick Sort Analysis

QuickSortTest and all of its acccompanying files have been tested and proven to run on Wichita State University CS servers. 

If using GUI:
	A. Open a terminal using shortcut "Ctrl+Alt+t" or by finding the LXterminal in the menu.

Steps to compile:
	1. Change directories to where the QuickSortTest.java, QuickSort.java, RandomizedQuickSort.java are located. by using "cd"	
	2. Once you are in the directory, type:

		javac QuickSortTest.java QuickSort.java RandomizedQuickSort.java

		OR

		javac *.java

Steps to run:
	1. Due to the recursive nature of this algorithm, you will need to allocate a larger amount of memory to the JVM for all input values greater than 8000.

	2. You have the option to provide command line arguments for the program in order to save time. on top of the traditional lines necessary to run the process, you can add two integers. The first integer is the N, which is the size of the array you wish to sort. the second argument is X, which is the value you wish to decrease by.

	EXAMPLES: 
		the following example is a traditional run of QuickSortTest (note: you cannot exceed n = 8000 using this method)

			java QuickSortTest

		The following is an example of a traditional run for n > 8000

			java -Xss256m QuickSortTest

		The following is an example of a run QuickSortTest using command line arguments AND allocating memory to the JVM. 10000 is the input value for N, 1 is the number which you want to decrease by.
		
			java -Xss256m QuickSortTest 10000 1


ALTERNATIVE: you can copy and paste the contents of runThese.txt and paste them into the command line (make sure you are in the same directory in which you compiled the program). This will execute all of the instances I tested in order to calculate the average. (DO NOT ENTER THE LAST 10 RUNS UNLESS YOU HAVE HOURS OF TIME TO WASTE)

All output will be located in a file titled "output.txt" which will be located in the same directory in which you compiled/ran the program.

A file titled "RunCountFile.txt" is also created. Ignore this file as it is simply for bookkeeping.
