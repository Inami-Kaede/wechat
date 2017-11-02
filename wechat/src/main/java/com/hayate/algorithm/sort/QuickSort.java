package com.hayate.algorithm.sort;

import java.util.Arrays;

public class QuickSort {

	public static void quickSort(int[] array, int low, int high){
		if(low < high){
			int middile = getMiddile(array, low, high);
			quickSort(array, 0, middile-1);
			quickSort(array, middile+1, high);
		}
	}
	
	public static int getMiddile(int[] array, int low, int high){
		int temp = array[low];
		while(low < high){
			while(low < high && array[high] >= temp){
				high--;
			}
			array[low] = array[high];
			while(low < high && array[low] <= temp){
				low++;
			}
			array[high] = array[low];
		}
		array[low] = temp;
		return low;
	}
	
	public static void main(String[] args) {
		int [] array = {49,38,44,2,0,7,28,1,-9,7,2,5,23,12,99,76};
		quickSort(array, 0, array.length-1);
		Arrays.stream(array).forEach(i -> System.out.print(i+","));
	}
}
