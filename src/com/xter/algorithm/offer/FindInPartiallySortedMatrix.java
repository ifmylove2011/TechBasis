package com.xter.algorithm.offer;

/**
 * 经过排序的二维数组中查找某数是否存在
 * 1 2 3 5
 * 2 4 5 7
 * 5 6 7 9
 * 7 10 11 13
 */
public class FindInPartiallySortedMatrix {

	public static void main(String[] args) {

		int[][] src = {{1, 2, 3, 5}, {2, 4, 5, 7}, {5, 6, 7, 9}, {7, 10, 11, 13}};
		System.out.println(isExisted(src, 14));
	}

	private static boolean isExisted(int[][] array, int pointNum) {
		boolean isExisted = false;
		if (array == null) {
			return false;
		}
		int row = 0;
		int column = array[0].length - 1;

		while (row < array.length) {
			System.out.println(array[row][column]);
			if (array[row][column] == pointNum) {
				isExisted = true;
				break;
			} else if (array[row][column] > pointNum) {
				column--;
			} else {
				row++;
			}

		}
		return isExisted;
	}
}
