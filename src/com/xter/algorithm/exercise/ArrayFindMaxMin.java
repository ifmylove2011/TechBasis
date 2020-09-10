package com.xter.algorithm.exercise;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/9/8
 * 描述:如何查找数组中元素的最大值和最小值，假设各元素值不同；
 */
public class ArrayFindMaxMin {

	static int max = Integer.MAX_VALUE;
	static int min = Integer.MIN_VALUE;

	public static void main(String[] args) {
		int[] src = {3, 2, 5, 1, 6, 4, 9, 8};
		find(src);
		System.out.println("max="+max+",min="+min);
	}

	private static void find(int[] array) {
		int size = array.length;
		for (int i = 0; i < size - 1; i += 2) {
			if (array[i] > array[i + 1]) {
				int tmp = array[i + 1];
				array[i + 1] = array[i];
				array[i] = tmp;
			}
		}
		min = array[0];
		for (int i = 2; i < size; i += 2) {
			if (array[i] < min) {
				min = array[i];
			}
		}
		max = array[1];
		for (int i = 3; i < size; i += 2) {
			if (array[i] > max) {
				max = array[i];
			}
		}
		if(size%2==1){
			if(max<array[size-1]){
				max = array[size-1];
			}
			if(min>array[size-1]){
				min = array[size-1];
			}
		}
	}
}
