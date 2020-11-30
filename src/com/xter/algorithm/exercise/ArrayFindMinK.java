package com.xter.algorithm.exercise;

import java.util.Arrays;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/9/10
 * 描述:找出数组中第k小的数；
 */
public class ArrayFindMinK {

	public static void main(String[] args) {
		int[] array = {3, 0, 2, 1, 0};
		System.out.println(findSortPartial(array, 3));
	}

	private static int findSortPartial(int[] array, int k) {
		int size = array.length;
		for (int i = 0; i < size; i++) {
			int min = array[i];
			int v = i;
			for (int j = i + 1; j < size; j++) {
				if (array[j] < min) {
					v = j;
					min = array[j];
				}
			}
			int tmp = array[i];
			array[i] = array[v];
			array[v] = tmp;
			if (i == k - 1) {
				return array[i];
			}
		}
		return 0;
	}
}
