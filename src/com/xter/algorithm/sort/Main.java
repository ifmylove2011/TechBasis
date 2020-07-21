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
		int[] origin = {3,2,5,7,12,1,0};
//		ISortStrategy sortStrategy = new BubbleSort();
//		ISortStrategy sortStrategy = new SelectSort();
//		ISortStrategy sortStrategy = new InsertSort();
		String s = Pattern.compile("e".toString(), Pattern.LITERAL).matcher(
				"abcdeeef").replaceAll(Matcher.quoteReplacement("1".toString()));
//		ISortStrategy sortStrategy = new QuickSort();
//		sortStrategy.sort(origin);
//		System.out.println(Arrays.toString(origin));
		System.out.println(s);
	}
}
