package com.xter.algorithm.sort;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author XTER
 * @date 2019/6/24
 */
public class Main {
	public static void main(String[] args) {
		int[] origin = {5, 3, 2, 12, 7, 1, 0, 13, 7, 9};
//		ISortStrategy sortStrategy = new BubbleSort();
//		ISortStrategy sortStrategy = new SelectSort();
//		ISortStrategy sortStrategy = new InsertSort();
//		ISortStrategy sortStrategy = new QuickSort();
//		ISortStrategy sortStrategy = new ShellSort();
//		ISortStrategy sortStrategy = new HeapSort();
		ISortStrategy sortStrategy = new MergeSort();
		sortStrategy.sort(origin);
		System.out.println(Arrays.toString(origin));
	}

	public static void exch(int i,int j){
		System.out.println(i+","+j);
	}
}
