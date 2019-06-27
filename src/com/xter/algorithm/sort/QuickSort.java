package com.xter.algorithm.sort;

/**
 * @author XTER
 * @date 2019/6/24
 */
public class QuickSort implements ISortStrategy {

	@Override
	public void sort(int[] origin) {
		part(origin, 0, origin.length - 1);
	}

	private void part(int[] array, int left, int right) {
		if (left >= right) {
			return;
		}
		int pivot = left;
		int index = pivot + 1;
		for (int i = index; i <= right; i++) {
			if (array[i] < array[pivot]) {
				int temp = array[i];
				array[i] = array[index];
				array[index] = temp;
				index++;
			}
		}
		int temp = array[pivot];
		array[pivot] = array[index - 1];
		array[index - 1] = temp;

		part(array, left, index - 2);
		part(array, index, right);
	}
}
