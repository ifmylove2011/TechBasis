package com.xter.algorithm.exercise;

import java.util.Arrays;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/9/22
 * 描述:n*n的数组旋转45度打印；
 */
public class ArrayRotate {

	public static void main(String[] args) {
		int[][] array = new int[3][3];
		for (int i = 1; i < 10; i++) {
			array[(i - 1) / 3][(i - 1) % 3] = i;
		}
		for (int i = 0; i < array.length; i++) {
			System.out.println(Arrays.toString(array[i]));
		}
		rotate(array);
	}


	private static void rotate(int[][] array) {
		int row, column;
		int size = array.length;
		for (int i = size - 1; i > 0; i--) {
			row = 0;
			column = i;
			while (column < size) {
				System.out.print(array[row++][column++] + " ");
			}
			System.out.println();
		}
		for (int i = 0; i < size; i++) {
			row = i;
			column = 0;
			while (row < size) {
				System.out.print(array[row++][column++] + " ");
			}
			System.out.println();
		}
	}
}
