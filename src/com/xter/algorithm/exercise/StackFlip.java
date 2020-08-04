package com.xter.algorithm.exercise;

import java.util.Stack;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/8/3
 * 描述:翻转栈中的元素，如进栈{1,2,3,4,5}，5在栈顶，现翻转，1在栈顶。
 */
public class StackFlip {

	public static void main(String[] args) {
		Stack<String> stack = getStack();
		System.out.println(stack);
		reverse(stack);
		System.out.println(stack);
	}

	private static void move(Stack<String> stack){
		if(stack.isEmpty()){
			return;
		}
		String top1 = stack.pop();
		if(stack.isEmpty()){
			stack.push(top1);
		}else{
			System.out.println("move1:"+stack);
			move(stack);
			String top2 = stack.pop();
			stack.push(top1);
			stack.push(top2);
			System.out.println("move2:"+stack);
		}
	}

	private static void reverse(Stack<String> stack){
		if(stack.isEmpty()){
			return;
		}
		move(stack);
		String top = stack.pop();
		reverse(stack);
		stack.push(top);
	}

	public static Stack<String> getStack() {
		Stack<String> stack = new Stack<>();
		for (int i = 97; i < 104; i++) {
			stack.push(String.valueOf((char) i));
		}
		return stack;
	}
}
