package com.xter.algorithm.sort;

import java.util.Arrays;

/**
 * @author XTER
 * @date 2019/6/24
 */
public class Main {
	public static void main(String[] args) {
		int[] origin = {3,2,5,7,12,1,0};
		ISortStrategy sortStrategy = new BubbleSort();
		sortStrategy.sort(origin);
		System.out.println(Arrays.toString(origin));
	}
}
