package com.xter.algorithm.exercise;

import java.util.Arrays;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/9/14
 * 描述:给定一个数组，数组含有重复元素，给定两个数字num1和num2，求这两个数字在数组中出现的最小 距离；
 */
public class ArrayFindMinDistance {

	public static void main(String[] args) {
		System.out.println(findDistance(getArray(), 2, 0));
		System.out.println(findNear(getArray(), 2, 0));
	}

	private static int findNear(int[] array, int num1, int num2) {
		if (array == null || array.length == 0 || num1 == num2) {
			return 0;
		}
		int size = array.length;
		int index1 = -1;
		int index2 = -1;
		int minDistance = Integer.MAX_VALUE;
		for (int i = 0; i < size; i++) {
			if (num1 == array[i]) {
				index1 = i;
				if (index2 > 0) {
					minDistance = Math.min(minDistance, index1 - index2);
				}
			}
			if (num2 == array[i]) {
				index2 = i;
				if (index1 > 0) {
					minDistance = Math.min(minDistance, index2 - index1);
				}
			}
		}
		return minDistance;
	}

	private static int findDistance(int[] array, int num1, int num2) {
		if (array == null || array.length == 0 || num1 == num2) {
			return 0;
		}
		int size = array.length;
		int distance = 0;
		int minDistance = Integer.MAX_VALUE;
		for (int i = 0; i < size; i++) {
			if (num1 == array[i]) {
				for (int j = 0; j < size; j++) {
					if (num2 == array[j]) {
						distance = Math.abs(i - j);
						if (minDistance > distance) {
							minDistance = distance;
						}
					}
				}
			}
		}
		return minDistance;
	}

	private static int[] getArray() {
		return new int[]{3, 2, 1, 0, 8, 7, 2, 1, 4, 8, 5};
	}
}
