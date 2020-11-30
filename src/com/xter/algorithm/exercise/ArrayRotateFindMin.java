package com.xter.algorithm.exercise;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/9/8
 * 描述:将一个有序数组的最开始的n个元素搬到数组末尾，查找最小值；
 * 如{3,4,5,1,2}最小值为2；
 */
public class ArrayRotateFindMin {

	public static void main(String[] args) {
		int[] array = {3,4,5,6,0,1,2};
		System.out.println(getMin(array,0,array.length-1));
	}

	private static int getMin(int[] array, int start, int end) {
		if (end < start) {
			return array[0];
		}
		if (start == end) {
			return array[start];
		}
		int mid = (start + end) / 2;
		if (array[mid] < array[mid - 1]) {
			return array[mid];
		} else if (array[mid] > array[mid + 1]) {
			return array[mid + 1];
		} else if (array[end] > array[mid]) {
			return getMin(array, start, mid - 1);
		} else if (array[start] < array[mid]) {
			return getMin(array, mid + 1, end);
		} else {
			return Math.min(getMin(array, start, mid - 1), getMin(array, mid + 1, end));
		}
	}
}
