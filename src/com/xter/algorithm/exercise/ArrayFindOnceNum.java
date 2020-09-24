package com.xter.algorithm.exercise;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/9/22
 * 描述:找出数组中出现一次的数；
 * 一个数组中，除了3个数是唯一出现，其余的数都出现了偶数次，找出3个数中的任意一个；
 */
public class ArrayFindOnceNum {

	public static void main(String[] args) {
		int[] array = {6, 3, 4, 5, 9, 4, 3};
		System.out.println(findOnce(array));
	}

	private static int findOnce(int[] array) {
		int size = array.length;
		int count0, count1;
		int result0, result1;
		for (int i = 0; i < 32; i++) {
			result0 = result1 = count1 = count0 = 0;
			for (int j = 0; j < size; j++) {
				if ((array[j] & (1 << i)) == 1) {
					result1 ^= array[j];
					count1++;
				} else {
					result0 ^= array[j];
					count0++;
				}
			}
			if (count1 % 2 == 1 && result0 != 0) {
				return result1;
			}
			if (count0 % 2 == 1 && result1 != 0) {
				return result0;
			}
		}
		return -1;
	}
}
