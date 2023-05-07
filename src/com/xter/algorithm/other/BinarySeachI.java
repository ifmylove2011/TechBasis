package com.xter.algorithm.other;

import java.util.Collections;

/**
 * @author XTER
 * @desc 二分查找
 * @date 2019/12/16
 */
public class BinarySeachI {

	static final int size = 100;
	static final float offset = 1.2f;

	public static void main(String[] args) {
		int[] array = new int[size];
		for (int i = 0; i < size; i++) {
			array[i] = i + 20;
		}

		System.out.println(search(array, 88));
	}


	static int search(int[] array, int num) {
		int low = 0;
		int high = array.length - 1;
		while (low <= high) {
			int midIndex = (low + high) >>> 1;
			if (array[midIndex] == num) {
				return midIndex;
			} else if (array[midIndex] < num) {
				low = midIndex + 1;
			} else {
				high = midIndex - 1;
			}
		}
		return -(low + 1);
	}
}
