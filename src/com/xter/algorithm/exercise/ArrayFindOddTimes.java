package com.xter.algorithm.exercise;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/9/10
 * 描述:找出数组中的出现奇数次的数；
 */
public class ArrayFindOddTimes {

	public static void main(String[] args) {
		int[] array = {1, 1, 2, 2, 3, 4, 4, 5, 5, 6};
		findXor(array);
	}

	private static void findXor(int[] array) {
		int result = 0;
		for (int a : array) {
			result ^= a;
		}
		System.out.println("result=" + result);
		int tmp = result;
		int pos = 0;
		for (int i = result; (i & 1) == 0; i = i >> 1) {
			pos++;
		}
		System.out.println("pos=" + pos);
		//将另一个数字排除了出来，因为只有另一个数字才会在这一位上与他不同，其他数字会自行相互抵消
		for (int a : array) {
			if (((a >> pos) & 1) == 1) {
				result = result ^ a;
			}
		}
		System.out.println(result);
		System.out.println(result ^ tmp);
	}
}
