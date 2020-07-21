package com.xter.algorithm.sort;

import java.util.Arrays;

/**
 * @author XTER
 * @date 2019/6/24
 */
public class HeapSort implements ISortStrategy {

	private int counter = 0;

	@Override
	public void sort(int[] origin) {
		int size = origin.length;
		int[] src = new int[size + 1];
		System.arraycopy(origin, 0, src, 1, size);
//		origin = src;
//		size = origin.length;
		System.out.println(Arrays.toString(src));

		for (int i = size / 2; i > 0; i--) {
			sink(src, i, size);
		}
		System.out.println(Arrays.toString(src));
		while (size > 1) {
			int temp = src[1];
			src[1] = src[size];
			src[size] = temp;
			++counter;
			size--;
			sink(src, 1, size);
		}
		System.arraycopy(src, 1, origin, 0, origin.length);
	}

	private void sink(int[] array, int k, int n) {
		while (2 * k <= n) {
			int j = 2 * k;
			if (j < n && array[j] < array[j + 1]) {
				j++;
			}
			//当前结点已处于正确位置
			if (array[k] >= array[j]) {
				break;
			}
			int temp = array[k];
			array[k] = array[j];
			array[j] = temp;
			++counter;
			k = j;
		}
	}


	public int getCounter() {
		return counter;
	}
}
