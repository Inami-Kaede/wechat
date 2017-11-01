package com.hayate.algorithm.sort;

import java.util.Arrays;

public class ShellSort {

	public static void shellSort(int[] array, int step){
		while(step > 0){
			for(int i = 0; i < array.length; i ++){
				for(int j = i; (j + step) < array.length; j += step){
					if(array[j + step] < array[j]){
						int temp = array[j + step];
						array[j + step] = array[j];
						array[j] = temp;
					}
				}
			}
			//step--;
			step /= 2;
		}
	}
	
	public static void main(String[] args) {
//		int [] array = {49,38,44,2,0,7,28,1,-9,7,2,5,23,12,99,76};
		int [] array = {26, 53, 67, 48, 57, 13, 48, 32, 60, 50 };
		shellSort(array, 5);
		Arrays.stream(array).forEach(i -> System.out.print(i+","));
	}
}
