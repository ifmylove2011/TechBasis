package com.xter.algorithm.sort;

import java.util.Arrays;

/**
 * @author XTER
 * @date 2019/6/24
 */
public class BubbleSort implements ISortStrategy {

	@Override
	public void sort(int[] origin) {
		int size = origin.length;
		for (int i = 0; i < size; i++) {
			boolean exchanged = false;
			for (int j = 0; j < size - 1; j++) {
				if (origin[j] > origin[j + 1]) {
					exchanged = true;
					int temp = origin[j];
					origin[j] = origin[j + 1];
					origin[j + 1] = temp;
				}
			}
			if (!exchanged)
				break;
			System.out.println("n=" + i);
			System.out.println(Arrays.toString(origin));
		}
	}
}
