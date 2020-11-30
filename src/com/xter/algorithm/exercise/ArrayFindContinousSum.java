package com.xter.algorithm.exercise;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/9/16
 * 描述:一个有n个元素的数组，这n个元素即可以是正数也可以是负数，数组中连续的一个或多个元素组成一个子数组，求子数组最大和；
 */
public class ArrayFindContinousSum {

	public static void main(String[] args) {
		int[] array = {1, -2, 4, 8, -4, 7, -1, -5};
		System.out.println(getMaxSum(array));
		System.out.println(dynamicProgram(array));
	}

	private static int getMaxSum(int[] array) {
		int size = array.length;
		int indexStart;
		int indexEnd;
		int maxSum = Integer.MIN_VALUE;
		int tmp = 0;
		for (indexStart = 0; indexStart < size; indexStart++) {
			tmp = 0;
			for (indexEnd = indexStart; indexEnd < size; indexEnd++) {
				tmp += array[indexEnd];
				if (tmp > maxSum) {
					maxSum = tmp;
				}
			}
		}
		return maxSum;
	}

	/**
	 * 动态规划
	 * @param array
	 * @return
	 */
	private static int dynamicProgram(int[] array){
		int result = array[0];
		int cache = array[0];
		int size = array.length;
		for (int i = 1; i < size; i++) {
			cache = Math.max(cache+array[i],array[i]);
			result = Math.max(cache,result);
		}
		return result;
	}
}
