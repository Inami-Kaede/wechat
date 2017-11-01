package com.hayate.algorithm;

import java.util.Arrays;

public class Test {
	public static void shellSortSmallToBig(int[] data) {
		int j = 0;
		int temp = 0;
		for (int increment = data.length / 2; increment > 0; increment /= 2) {
			System.out.println("increment:" + increment);
			for (int i = increment; i < data.length; i++) {
				// System.out.println("i:" + i);
				temp = data[i];
				for (j = i - increment; j >= 0; j -= increment) {
					// System.out.println("j:" + j);
					// System.out.println("temp:" + temp);
					// System.out.println("data[" + j + "]:" + data[j]);
					if (temp < data[j]) {
						data[j + increment] = data[j];
					} else {
						break;
					}
				}
				data[j + increment] = temp;
			}
			for (int i = 0; i < data.length; i++){
				System.out.print(data[i] + " ");
			}
			System.out.println("");
		}
	}

	public static void main(String[] args) {
		int[] data = new int[] { 26, 53, 67, 48, 57, 13, 48, 32, 60, 50 };
//		shellSortSmallToBig(data);
		sort(data);
		System.out.println(Arrays.toString(data));
	}
	
	/**
     * 希尔排序
     * @param arrays 需要排序的序列
     */
    public static void sort(int[] arrays){
        if(arrays == null || arrays.length <= 1){
            return;
        }
        //增量
        int incrementNum = arrays.length/2;
        while(incrementNum >=1){
            for(int i=0;i<arrays.length;i++){
                //进行插入排序
                for(int j=i;j<arrays.length-incrementNum;j=j+incrementNum){
                    if(arrays[j]>arrays[j+incrementNum]){
                        int temple = arrays[j];
                        arrays[j] = arrays[j+incrementNum];
                        arrays[j+incrementNum] = temple;
                    }
                }
            }
            //设置新的增量
            incrementNum = incrementNum/2;
        }
    }
}
