package arrays;

import java.util.Arrays;

/**
 * This class contains the solutions for exercise 4a
 * 
 * @author Mattias Jönsson
 * Date 14/11-2018
 *
 */
public class IntegerArrays {
	
	/**
	 * Prints out the number in the array
	 * 
	 * @param array		the array of numbers
	 * @return a string of the numbers
	 */
	public static String toString(int[] array) {		
		int l = array.length;
		String str = "{";
		for(int i=0;i<l-1;i++) str += array[i]+", ";
		str += array[l-1]+"}";
		return str;		
	}

	/**
	 * Checks the highest number in the array
	 * 
	 * @param array		the array of numbers
	 * @return a number
	 */
	public static int max(int[] array) {
		int max = Integer.MIN_VALUE;
		for(int i=0;i<array.length;i++) if(array[i]>max) max=array[i];
		return max;
	}
	
	/**
	 * Checks the lowest number in the array
	 * 
	 * @param array		the array of numbers
	 * @return a number
	 */
	public static int min(int[] array) {
		int min = Integer.MAX_VALUE;
		for(int i=0;i<array.length;i++)	if(array[i]<min) min=array[i];
		return min;
	}
	
	/**
	 * Checks the sum of the numbers in the array
	 * 
	 * @param array		the array of numbers
	 * @return the sum
	 */
	public static int sum(int[] array) {
		int sum = 0;
		for(int i=0;i<array.length;i++)sum += array[i];
		return sum;
	}
	
	/**
	 * Checks the average of the numbers in the array
	 * 
	 * @param array		the array of numbers
	 * @return the average
	 */
	public static Object average(int[] array) {
		int sum = 0;
		for(int i=0;i<array.length;i++)sum += array[i];
		return (float)sum/array.length;
	}
	
	/**
	 * Checks the range of the numbers in the array
	 * 
	 * @param array		the array of numbers
	 * @return the range
	 */
	public static int range(int[] array) {
		int min = Integer.MAX_VALUE;
		int max = 0;
		for(int i=0;i<array.length;i++) if(array[i]<min)min=array[i];		
		for(int i=0;i<array.length;i++) if(array[i]>max)max=array[i];
		return max-min;
	}
	
	/**
	 * Copies the given array to an new array 
	 * 
	 * @param array		the array of numbers
	 * @return the new array
	 */
	public static int[] copy(int[] array) {
		int[] newArr = new int[array.length];
		for(int i=0;i<array.length;i++)	newArr[i]=array[i];
		return newArr;
	}
	
	/**
	 * Sorts the array in ascending order
	 * 
	 * @param array		the array of numbers
	 */
	public static void sortAsc(int[] array) {	
		Arrays.sort(array); 
	}
	
	/**
	 * Sorts the array in descending order
	 * 
	 * @param array		the array of numbers
	 */
	public static void sortDesc(int[] array) {
		int temp;
		Arrays.sort(array);
		for(int i=0;i<array.length/2;i++) {
			temp = array[i];
			array[i]=array[array.length-i-1];
			array[array.length-i-1]=temp;		
		}
	}
	
	/**
	 * Checks the median of the numbers in the array
	 * 
	 * @param array		the array of numbers
	 * @return the median
	 */
	public static double median(int[] array) {
		int[] arr = array; 
		Arrays.sort(arr);
		double median;
		if (array.length % 2 == 0) median = ((double)arr[arr.length/2] + (double)arr[arr.length/2 - 1])/2;
		else median = (double) arr[arr.length/2];
		return median;
	}
	
	/**
	 * Checks the mode of the numbers in the array
	 * 
	 * @param array		the array of numbers
	 * @return the mode
	 */
	public static int mode(int[] array) {
		int maxValue = 0, maxCount = 0;
        for (int i = 0; i < array.length; ++i) {
            int count = 0;
            for (int j = 0; j < array.length; ++j)if (array[j] == array[i])count++;
            if (count > maxCount) {
                maxCount = count;
                maxValue = array[i];
            }
        }
        return maxValue;	
	}
}