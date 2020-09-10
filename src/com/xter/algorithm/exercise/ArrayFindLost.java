package com.xter.algorithm.exercise;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/9/9
 * 描述:n-1个整数组成的未排序的数组序列，元素值为1...n的不同整数，找出缺失的整数；
 */
public class ArrayFindLost {

	public static void main(String[] args) {
		int[] array = {1, 2, 3, 4, 5, 7, 8};
		System.out.println(findLost(array));
		System.out.println(findLostByXor(array));
	}

	private static int findLost(int[] array) {
		int size = array.length;
		int sumLost = 0;
		for (int a : array) {
			sumLost += a;
		}
		int sumComp = 0;
		for (int i = 1; i <= size + 1; i++) {
			sumComp += i;
		}
		return sumComp - sumLost;
	}

	private static int findLostByXor(int[] array) {
		int size = array.length;
		int xor = 0;
		for (int i = 0; i < size; i++) {
			xor ^= array[i];
		}
		int xorStandard = 0;
		for (int i = 1; i <= size + 1; i++) {
			xorStandard ^= i;
		}
		return xor ^ xorStandard;
	}
}
