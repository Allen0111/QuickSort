import java.util.Random;

/**
 * Randomized Quicksort is a variation of quicksort that ... 
 * @author Allen Bui
 * @version 1
 * March 31, 2016
 */
public class RandomizedQuickSort extends QuickSort{
	
	public static Random generator = new Random();
	/**
	 *  randomSort is essentially the same as QuickSort except that it adds
	 *  an additional step which is to randomly swap the pivot element with
	 *  another element in the array.
	 * 
	 * @param array is the array to be sorted
	 * @param left is the first index of the array
	 * @param right is the last index of the array (n-1)
	 */
	public static void randomSort(int[] nArray, int left, int right) {
		if (left < right) {		 // continue to execute until left is less than right
			int partitionIndex = randomPartition(nArray, left, right);	// get the partition index of the subarray
			randomSort(nArray, left, (partitionIndex - 1));					// split the left subarray
			randomSort(nArray, (partitionIndex + 1), right);		// split the right subarray
		}
	}
	
	/**
	 * randomPartition adds one step prior to calling the regular partition
	 * function which is located in the parent, QuickSort. the added step is 
	 * to swap with an element in the subarray at random in order to achieve 
	 * 0(nlgn) runtime. 
	 * 
	 * @param nArray is the original array
	 * @param left is the leftmost element of the subarray
	 * @param right is the rightmost element of the subarray
	 * @return int value which is the index that partitions the subarrays
	 */
	private static int randomPartition(int[] nArray, int left, int right) {	
		
		int i = left + generator.nextInt(right - left + 1);	// making sure we're swapping with a random 
															// index that is within the bounds of the subarray
		
		exchange(nArray, i, right);		// perform the swap of indicies

		return(partitionFunction(nArray, left, right));		// call the original partition function to do the real work
	}
}