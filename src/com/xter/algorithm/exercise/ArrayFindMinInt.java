package com.xter.algorithm.exercise;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/9/16
 * 描述:有一个升序排列的数组，数组中可能有正数、负数、0，求数组中元素绝对值最小的值；
 */
public class ArrayFindMinInt {

	public static void main(String[] args) {
		int[] array = new int[]{-10, -5, -2, 7, 15, 50};
		System.out.println(getMin(array));
	}

	private static int getMin(int[] array) {
		if (array == null || array.length == 0) {
			return 0;
		}
		int size = array.length;
		if (array[0] >= 0) {
			return array[0];
		}
		if (array[size - 1] < 0) {
			return array[size - 1];
		}
		for (int i = 0; i < size; i++) {
			if (array[i] > 0) {
				if (Math.abs(array[i]) < Math.abs(array[i - 1])) {
					return array[i];
				} else {
					return array[i - 1];
				}
			}
		}
		return 0;
	}
}
