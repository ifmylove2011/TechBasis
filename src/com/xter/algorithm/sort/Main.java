package com.xter.algorithm.sort;

import java.util.Arrays;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author XTER
 * @date 2019/6/24
 */
public class Main {
	public static void main(String[] args) {
//		int[] origin = {5, 3, 2, 12, 7, 1, 0, 13, 7, 9};
//		ISortStrategy sortStrategy = new BubbleSort();
//		ISortStrategy sortStrategy = new SelectSort();
//		ISortStrategy sortStrategy = new InsertSort();
//		ISortStrategy sortStrategy = new QuickSort();
//		ISortStrategy sortStrategy = new ShellSort();
//		ISortStrategy sortStrategy = new HeapSort();
//		ISortStrategy sortStrategy = new MergeSort();
//		sortStrategy.sort(origin);
//		System.out.println(Arrays.toString(origin));
//		Stack<String> stack = getStack();
//		System.out.println(stack);
//		move(stack, null);
//		System.out.println(stack);
		String host = "/192.168.21.105:14000";
		System.out.println(host.substring(1,host.indexOf(":")));
	}

	private static void reverse(Stack<String> stack) {
		if (stack.isEmpty()) {
			return;
		}
		String top = stack.pop();
		System.out.println("top:" + top);
		if (stack.isEmpty()) {
			stack.push(top);
		} else {
			reverse(stack);
			System.out.println(stack);
			String topR = stack.pop();
//			System.out.println("topR:"+topR);
			stack.push(top);
			stack.push(topR);
		}
	}

	private static void move(Stack<String> stack, String p) {
		if (stack.isEmpty()) {
			return;
		}
		String top = stack.pop();
		System.out.println("top:" + top);
		if (stack.isEmpty()) {
			stack.push(p);
			return;
		}
		move(stack, top);
		System.out.println("p:"+p);
		if (p != null)
			stack.push(p);
	}

	public static Stack<String> getStack() {
		Stack<String> stack = new Stack<>();
		for (int i = 97; i < 102; i++) {
			stack.push(String.valueOf((char) i));
		}
		return stack;
	}

	public static void exch(int i, int j) {
		System.out.println(i + "," + j);
	}
}
