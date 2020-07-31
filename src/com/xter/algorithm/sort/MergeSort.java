package com.xter.algorithm.sort;

import java.util.Arrays;

public class MergeSort implements ISortStrategy {
	private int[] aux;

	@Override
	public void sort(int[] origin) {
		int size = origin.length;
		aux = new int[size];
		for (int i = 0; i < size; i++) {
			aux[i] = origin[i];
		}

		mergeSort(origin, 0, size - 1);
//		mergeSortB(origin);
//		merge(new int[]{2,4,5,7,1,3,8},0,3,6);
	}

	private void mergeSort(int[] src, int low, int high) {
		if (high <= low) {
			return;
		}
		System.out.println("low:" + low + ",high:" + high);
		int mid = low + (high - low) / 2;
		mergeSort(src, low, mid);
		mergeSort(src, mid + 1, high);
		merge(src, low, mid, high);
	}

	private void mergeSortB(int[] src) {
		int size = src.length;
		for (int sz = 1; sz < size; sz = sz + sz) {
//			System.out.println("sz="+sz);
			for (int low = 0; low < size - sz; low += sz + sz) {
//				System.out.println("low="+low);
				merge(src, low, low + sz - 1, Math.min(low + sz + sz - 1, size - 1));
			}
		}
	}

	private void merge(int[] src, int low, int mid, int high) {
		System.out.println("low:" + low + ",mid:" + mid + ",high:" + high);
		System.out.println("前:" + Arrays.toString(src));
		for (int i = low; i <= high; i++) {
			aux[i] = src[i];
		}
		int i = low, j = mid + 1;
		for (int k = low; k <= high; k++) {
			if (i > mid) {
				src[k] = aux[j++];
			} else if (j > high) {
				src[k] = aux[i++];
			} else if (aux[j] < aux[i]) {
				src[k] = aux[j++];
			} else {
				src[k] = aux[i++];
			}
		}
		System.out.println("后:" + Arrays.toString(src));
	}

}
