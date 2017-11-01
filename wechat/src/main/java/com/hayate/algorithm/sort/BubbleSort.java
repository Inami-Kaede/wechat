package com.hayate.algorithm.sort;

import java.util.Arrays;

public class BubbleSort {

	public static void bubbleSort(int[] array){
		for(int i = 0; i < array.length; i ++){
			int temp;
			for(int j = 0; j + 1 < array.length; j ++){
				if(array[j] > array[j+1]){
					temp = array[j];
					array[j] = array[j+1];
					array[j+1] = temp;
				}
			}
		}
	}
	public static void main(String[] args) {
		int [] array = {55,12,33,88,99,77};
		bubbleSort(array);
		Arrays.stream(array).forEach(i -> System.out.print(i+","));
	}
}
