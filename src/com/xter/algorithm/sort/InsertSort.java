package com.xter.algorithm.sort;

import java.util.Arrays;

/**
 * @author XTER
 * @date 2019/6/24
 */
public class InsertSort implements ISortStrategy {

	@Override
	public void sort(int[] origin) {
		int size = origin.length;
		for (int i = 1; i < size; i++) {
			int candidate = origin[i];
			int finalIndex = i;
			for (int j = i - 1; j >= 0; j--) {
				if (candidate < origin[j]) {
					origin[j + 1] = origin[j];
					finalIndex = j;
				} else {
					break;
				}
			}
			if (finalIndex != i) {
				origin[finalIndex] = candidate;
			}

			System.out.println("n=" + i);
			System.out.println(Arrays.toString(origin));
		}
	}
}
