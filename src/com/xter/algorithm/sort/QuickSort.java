package com.xter.algorithm.sort;

import java.util.Arrays;

/**
 * @author XTER
 * @date 2019/6/24
 */
public class QuickSort implements ISortStrategy {

	@Override
	public void sort(int[] origin) {
//		part(origin, 0, origin.length - 1);
		part2(origin, 0, origin.length - 1);
//		quick3sort(origin, 0, origin.length - 1);
	}

	private void part(int[] array, int left, int right) {
//		System.out.println(Arrays.toString(array));
		if (left >= right) {
			return;
		}
		System.out.println("left=" + left + ",right=" + right);
		int index = partition(array, left, right);
		System.out.println("index=" + index);
		part(array, left, index - 1);
		part(array, index + 1, right);
	}

	private void part2(int[] array,int left,int right){
		System.out.println("left="+left+",right="+right);
		if(left>right){
			return;
		}
		int i = left;
		int j= right;
		int temp = array[left];
		while (i!=j){
			while (array[j]>=temp&&i<j){
				j--;
			}
			while (array[i]<=temp&&i<j){
				i++;
			}
			if(i<j){
				int t = array[i];
				array[i] = array[j];
				array[j] = t;
			}
			System.out.println("i="+i+",j="+j);
			System.out.println(Arrays.toString(array));
		}
		array[left] = array[i];
		array[i] = temp;
		System.out.println(Arrays.toString(array));
		System.out.println("-------------");
		part2(array,left,i-1);
		part2(array,i+1,right);
	}

	private int partition(int[] array, int left, int right) {
		int pivot = left;
		int index = pivot + 1;
		System.out.println(Arrays.toString(array));
		for (int i = index; i <= right; i++) {
			if (array[i] < array[pivot]) {
				if (i != index) {
					System.out.println("exch:" + array[i] + "," + array[index]);
					int temp = array[i];
					array[i] = array[index];
					array[index] = temp;
					System.out.println(Arrays.toString(array));
				}
				index++;
			}
		}
		if (pivot == index - 1) {
			return pivot;
		}
		int temp = array[pivot];
		array[pivot] = array[index - 1];
		array[index - 1] = temp;
		System.out.println("exch--:" + array[pivot] + "," + array[index - 1]);
		System.out.println(Arrays.toString(array));

		return index - 1;
	}

	public static void quick3sort(int[] a, int lo, int hi) {
		if (hi <= lo) return;
		int lt = lo, i = lo + 1, gt = hi;
		int v = a[lo];
		while (i <= gt) {
			if (a[i] < v) {
				int tmp = a[lt];
				a[lt] = a[i];
				a[i] = tmp;
				lt++;
				i++;
			} else if (a[i] > v) {
				int tmp = a[i];
				a[i] = a[gt];
				a[gt] = tmp;
				gt--;
			} else {
				i++;
			}
		}
		quick3sort(a, lo, lt - 1);
		quick3sort(a, gt + 1, hi);
	}
}
