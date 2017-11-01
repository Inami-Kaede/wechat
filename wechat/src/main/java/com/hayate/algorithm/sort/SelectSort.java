package com.hayate.algorithm.sort;

import java.util.Arrays;

public class SelectSort {

	public static void selectSort(int[] array){
		
		for(int i = 0; i < array.length; i ++){
			int minId = i;
			for(int j = i + 1; j < array.length; j ++){
				if(array[j] < array[minId]){
					minId = j;
				}
			}
			int temp = array[i];
			array[i] = array[minId];
			array[minId] = temp;
		}
	}
	public static void main(String[] args) {
		int [] array = {55,12,33,88,99,77};
		selectSort(array);
		Arrays.stream(array).forEach(i -> System.out.print(i+","));
	}
}
