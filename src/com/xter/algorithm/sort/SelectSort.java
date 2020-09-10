package com.xter.algorithm.sort;

import java.util.Arrays;

/**
 * @author XTER
 * @date 2019/6/24
 */
public class SelectSort implements ISortStrategy {

	@Override
	public void sort(int[] origin) {
		int size = origin.length;
		for (int i = 0; i < size; i++) {
			int min = origin[i];
			int k = i;
			for (int j = i + 1; j < size; j++) {
				if (min > origin[j]) {
					min = origin[j];
					k = j;
				}
			}
			int temp = origin[i];
			origin[i] = origin[k];
			origin[k] = temp;
			System.out.println("n=" + i);
			System.out.println(Arrays.toString(origin));
		}
	}
}
