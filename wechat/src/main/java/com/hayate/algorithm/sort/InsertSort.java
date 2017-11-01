package com.hayate.algorithm.sort;

import java.util.Arrays;

public class InsertSort {

	public static void main(String[] args) {
		int[] array = {9,3,2,6,10,44,83,28,5,1,0,36};
		insertSort(array);
		Arrays.stream(array).forEach(i -> System.out.print(i+","));
	}
	public static void insertSort(int[] array){
		for(int i = 1; i < array.length; i ++){
			int next = array[i];
			int j;
			for(j = i - 1; j >= 0; j --){
				if(next < array[j]){
					array[j+1] = array[j];
				}else{
					break;
				}
			}
			array[j+1] = next;
		}
	}
}
