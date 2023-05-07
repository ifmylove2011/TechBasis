package com.xter.algorithm.exercise;

import java.util.Arrays;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2021/4/23
 * 描述:给定以非递减顺序排序的三个数组，找出这三个数组中的所有公共元素。
 * 例如，给出下面三个数组：
 * ar1[] = {2,5,12,20,45,85}
 * ar2[] = {16,19,20,85,200}
 * ar3[] = {3,4,15,20,39,72,85,190}
 * 那么这三个数组的公共元素为{20,85}
 */
public class ArrayFindCommon {

	public static void main(String[] args) {
		int[][] arrays = new int[3][];
		arrays[0] = new int[]{2, 5, 12, 20, 45, 85};
		arrays[1] = new int[]{16, 19, 20, 85, 200};
		arrays[2] = new int[]{3, 4, 15, 20, 39, 72, 85, 190};
		findCommon2(arrays[0], arrays[1], arrays[2]);
	}

	public static void findCommon(int[][] arrays) {
		if (arrays.length < 2) {
			return;
		}
		int[] array1 = arrays[0];
		int min = array1[0];
		int max = array1[array1.length - 1];
		for (int i = 1; i < arrays.length; i++) {
			int[] array = arrays[i];
			if (array[0] > min) {
				min = array[0];
			}
			if (array[array.length - 1] < max) {
				max = array[array.length - 1];
			}
		}
		System.out.println("min:" + min + ",max:" + max);
		int[] startIndices = new int[arrays.length];
		for (int i = 0; i < arrays.length; i++) {
			startIndices[i] = 0;
			int[] array = arrays[i];
			while (array[startIndices[i]] < min) {
				startIndices[i]++;
			}
		}
		System.out.println(Arrays.toString(startIndices));
		//emmmm，多了难以判断
	}

	public static void findCommon2(int[] array1, int[] array2, int[] array3) {
		int l1 = array1.length;
		int l2 = array2.length;
		int l3 = array3.length;
		int i = 0, j = 0, k = 0;
		while (i < l1 && j < l2 && k < l3) {
			if (array1[i] == array2[j] && array2[j] == array3[k]) {
				System.out.println(array1[i]);
				i++;
				j++;
				k++;
			} else if (array1[i] < array2[j]) {
				i++;
			} else if (array2[j] < array3[k]) {
				j++;
			} else {
				k++;
			}
		}
	}
}
