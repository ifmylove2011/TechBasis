package com.xter.algorithm.exercise;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/9/3
 * 描述:数字1-1000存放在容量为1001的数组中，且其中有一个元素重复，请找出；
 */
public class ArrayFindDuplicate {


	public static void main(String[] args) {
		int[] src = generateArray(1001);
		src[1000] = 234;

//		System.out.println(sumFind(src));
//		System.out.println(xorFind(src));
		System.out.println(nodeFind(src));
//		System.out.println(nodeFind(new int[]{1,3,4,3,5,2}));
	}


	public static int[] generateArray(int size) {
		int[] array = new int[size];
		for (int i = 1; i <= size; i++) {
			array[i - 1] = i;
		}
		return array;
	}

	private static int sumFind(int[] array) {
		int size = array.length;
		int sum = 0;
		for (int i = 0; i < size; i++) {
			sum += array[i];
		}
		int sumStandard = 0;
		for (int i = 1; i <= 1000; i++) {
			sumStandard += i;
		}
		return sum - sumStandard;
	}

	private static int xorFind(int[] array) {
		int size = array.length;
		int xor = 0;
		for (int i = 0; i < size; i++) {
			xor ^= array[i];
		}
		int xorStandard = 0;
		for (int i = 1; i <= 1000; i++) {
			xorStandard ^= i;
		}
		return xor ^ xorStandard;
	}

	private static int nodeFind(int[] array) {
		int index = 0;
		while (index >= 0) {
			int next = array[index];
			if(next<0){
				return index;
			}
			array[index] = -next;
			index = next;
		}
		return index;
	}
}
