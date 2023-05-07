package com.xter.algorithm.exercise;

import java.util.Arrays;
import java.util.Random;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2021/4/22
 * 描述:坐标轴上从左到右有点a[0].a[1].a[2]...a[n-1]，设一杆长度为L，问L最多能覆盖几个点？
 */
public class ArrayCoverMax {

	public static void main(String[] args) {
		int[] array = new int[10];
		Random random = new Random();
		array[0] = random.nextInt(10);
		for (int i = 1; i < 10; i++) {
			array[i] = array[i - 1] + random.nextInt(10) + 1;
		}
		System.out.println(Arrays.toString(array));
		findCover(array,10);
	}

	public static void findCover(int[] array, int maxLen) {
		int len = array.length;
		int start = 0, end = 1;
		int realStart = 0;
		int count = 1;
		while (start < len && end < len) {
			while (end<len&&array[end] - array[start] <= maxLen) {
				end++;
			}

			int tempCount = end - start;
			if (tempCount > count) {
				count = tempCount;
				realStart = start;
			}
			start++;
			end = start + 1;
		}
		System.out.println(count);
		for (int i = 0; i < count; i++) {
			System.out.print(array[realStart+i]+" ");
		}
	}
}
