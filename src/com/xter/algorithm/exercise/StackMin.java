package com.xter.algorithm.exercise;

import java.util.Stack;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/8/4
 * 描述:用O(1)的时间复杂度求栈中最小元素；
 */
public class  StackMin {

	public static void main(String[] args) {
		MinStack<Integer> minStack = new MinStack<>();
		minStack.push(5);
		System.out.println(minStack.getMin());
		minStack.push(2);
		System.out.println(minStack.getMin());
		minStack.push(5);
		System.out.println(minStack.getMin());
		minStack.push(2);
		System.out.println(minStack.getMin());
		minStack.push(1);
		System.out.println(minStack.getMin());
		minStack.pop();
		System.out.println(minStack.getMin());
		minStack.pop();
		minStack.pop();
		System.out.println(minStack.getMin());
		minStack.pop();
		System.out.println(minStack.getMin());
	}

	static class MinStack<T extends Comparable<T>> implements StackImpl.Stack<T> {
		private Stack<T> stackOrigin;
		private Stack<T> stackMin;

		MinStack() {
			stackOrigin = new Stack<>();
			stackMin = new Stack<>();
		}

		@Override
		public void push(T item) {
			stackOrigin.push(item);
			if (stackMin.isEmpty()) {
				stackMin.push(item);
			} else {
				if (item.compareTo(stackMin.peek()) <= 0) {
					stackMin.push(item);
				}
			}
		}

		@Override
		public T pop() {
			if (stackOrigin.peek().compareTo(stackMin.peek()) == 0) {
				stackMin.pop();
			}
			return stackOrigin.pop();
		}

		@Override
		public T top() {
			return stackOrigin.peek();
		}

		@Override
		public int size() {
			return stackOrigin.size();
		}

		@Override
		public boolean isEmpty() {
			return stackOrigin.isEmpty();
		}

		public T getMin() {
			return stackMin.peek();
		}
	}
}
