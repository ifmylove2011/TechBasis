package com.xter.algorithm.other;

import java.util.Arrays;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/9/16
 * 描述:7*3的格子，每次只能向下或向右移动一步，问从左上走到右下有多少种路径；
 */
public class DynamicProgram {

	public static void main(String[] args) {
		System.out.println(countPath(7, 3));
	}

	private static int countPath(int m, int n) {
		int[][] result = new int[m][n];
		for (int[] ints : result) {
			Arrays.fill(ints, 1);
		}

		for (int i = 1; i < m; i++) {
			for (int j = 1; j < n; j++) {
				result[i][j] = result[i - 1][j] + result[i][j - 1];
			}
		}
		return result[m - 1][n - 1];
	}
}
