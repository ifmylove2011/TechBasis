package com.xter.algorithm.exercise;

import java.util.Arrays;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/9/24
 * 描述:在不排序的情况下，查找一个数组的中位数；
 */
public class ArrayFindMid {

	private static int pos = 0;

	public static void main(String[] args) {
		int[] array = {7, 4, 5, 6, 0, 1, 2};
		System.out.println(getMid(array));
	}

	private static int getMid(int[] array) {
		int low = 0;
		int size = array.length;
		int high = size - 1;
		int mid = (low + high) / 2;
		while (true) {
			part(array, low, high);
			System.out.println(Arrays.toString(array));
			if (pos == mid) {
				break;
			} else if (pos > mid) {
				high = pos - 1;
			} else {
				low = pos + 1;
			}
		}
		return (size % 2) != 0 ? array[mid] : (array[mid] + array[mid + 1]) / 2;
	}

	private static void part(int[] array, int low, int high) {
		int key = array[low];
		while (low < high) {
			while (low < high && array[high] > key) {
				high--;
			}
			array[low] = array[high];
			while (low < high && array[low] < key) {
				low++;
			}
			array[high] = array[low];
		}
		array[low] = key;
		pos = low;
	}
}
