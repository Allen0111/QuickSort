/**
 * Quickort	is a sort in place algorithm
 * that has worst case runtime of 0(n^2), and average case runtime of 0(nlgn)
 * it is the most commonly used algorithm at the moment because of its good
 * average runtime and little space complexity.
 * 
 * @author Allen Bui, X339Y958
 * @version 1
 * March 31, 2016
 */
public class QuickSort {	
	
	/**
	 * sort is where the timer is set and the sorting begins. 
	 * After finished sorting, it will take the time again for later calculation
	 * 
	 * @param array is the array to be sorted
	 * @param left is the first index of the array
	 * @param right is the last index of the array (n-1)
	 */
	public static void sort(int[] array, int left, int right){	//log the time when the we begin sorting
		sortHelper(array, left, right);
	}
	
	/**
	 * sortHelper is the the beginning of the (not randomized) QuickSort algorithm
	 * 
	 * @param nArray is the array 
	 * @param left the first index of the array (0)
	 * @param right the last index of the array (n - 1)
	 */
	private static void sortHelper(int[] nArray, int left, int right) {
		if (left < right) {		
			int partition = partitionFunction(nArray, left, right);
			sortHelper(nArray, left, (partition - 1));		// recursively call the left subarrays
			sortHelper(nArray, (partition + 1), right);			//recursively call the right subarrays
		}
	}
	
	/**
	 * The partition function rearranges subarrays in place and returns the pivot element.
	 * 
	 * @param nArray
	 * @param leftMost is the first index of the subarray
	 * @param rightMost is the last index of the subarray (n - 1)
	 * @return returns an int value which will be the pivot element
	 */
	protected static int partitionFunction(int[] nArray, int leftMost, int rightMost) {
		int pivotValue, partitionIndex;
		pivotValue = nArray[rightMost];
		partitionIndex = (leftMost - 1);
		
		for (int jIndex = leftMost; jIndex < (rightMost); jIndex++) {
			if (nArray[jIndex] <= pivotValue) {
				partitionIndex++;
				exchange(nArray, partitionIndex, jIndex);
			}
		}
		
		exchange(nArray, partitionIndex+1, rightMost);
		
		return (partitionIndex + 1);
	}
	
	/**
	 * exchange is a function that swaps two elements of an array at indicies i & j.
	 * 
	 * @param nArray is an array of type int
	 * @param i is an integer indicating the index to be swapped
	 * @param j is an integer indicating the index to be swapped
	 */
	protected static void exchange(int[] nArray,int i, int j) {
		int temp = nArray[i];
		nArray[i] = nArray[j];
		nArray[j] = temp;
	}
}