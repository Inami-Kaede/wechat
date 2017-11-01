package com.hayate.algorithm.sort;

import java.util.Arrays;

public class BinaryInsertSort {

	public static void main(String[] args) {
		int[] array = {9,3,2,6,10,44,83,28,5,1,0,36};
		binaryInsertSort(array);
		Arrays.stream(array).forEach(i -> System.out.print(i+","));
	}
	
	public static void binaryInsertSort(int[] array){
		for(int i = 0; i < array.length; i++){
			int next = array[i];
			int left,right,mid;
			left = 0;
			right = i-1;
			while(left <= right){
				mid = (left+right)/2;
				if(next > array[mid]){
					left = mid + 1;
				}else if(next < array[mid]){
					right = mid - 1;
				}
			}
			for(int j = i - 1; j >= left; j --){
				array[j+1] = array[j];
			}
			if(left != i){
				array[left] = next;
			}
		}
	}
}
