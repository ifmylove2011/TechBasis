package com.xter.algorithm.sort;

import java.util.Arrays;

/**
 * @author XTER
 * @date 2019/6/24
 */
public class ShellSort implements ISortStrategy {

	private int counter = 0;

	@Override
	public void sort(int[] origin) {
		int size = origin.length;
		int delta = size;
		do {
			delta = delta / 3 + 1;

			for (int i = delta; i < size; i++) {
				for (int j = i; j >= delta && origin[j] < origin[j - delta]; j -= delta) {
					++counter;
					System.out.println(Arrays.toString(origin));
					int temp = origin[j];
					origin[j] = origin[j - delta];
					origin[j - delta] = temp;
				}
			}
			System.out.println("间距" + delta);
			System.out.println();
		} while (delta > 1);
	}


	public int getCounter() {
		return counter;
	}
}
