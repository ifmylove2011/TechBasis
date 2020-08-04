package com.xter.algorithm.exercise;

import java.util.Stack;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/8/4
 * 描述:输入两个整数序列，其中一个序列表示入栈顺序，判断另一个序列有没有可能是对应的出栈顺序；
 * 如入栈序列为1-2-3-4-5，那么3-2-5-4-1有可能是对应的出栈序列，而5-3-4-1-2不可能是；
 */
public class StackPop {

	public static void main(String[] args) {
		int[] array1 = {1, 2, 3, 4, 5};
//		int[] array2 = {3, 4, 5, 2, 1};
		int[] array2 = {5, 3, 4, 1, 2};
		System.out.println(isPop(array1,array2));
	}

	private static boolean isPop(int[] array1, int[] array2) {
		if (array1 == null || array2 == null) {
			return false;
		}
		int size1 = array1.length;
		int size2 = array2.length;
		if (size1 != size2) {
			return false;
		}
		Stack<Integer> stack = new Stack<>();
		int curIndex = 0;
		for (int i = 0; i < size1; i++) {
			int cur = array1[i];
			if(cur==array2[curIndex]){
				while (!stack.isEmpty()&&stack.peek()==array2[++curIndex]){
					System.out.println(stack.pop());
				}
			}else{
				stack.push(cur);
			}
		}
		return stack.isEmpty();
	}

	public static Stack<Integer> getStack(int[] array) {
		Stack<Integer> stack = new Stack<>();
		int size = array.length;
		for (int i = 0; i < size; i++) {
			stack.push(array[i]);
		}
		return stack;
	}
}
