package com.xter.algorithm.exercise;

import java.util.Stack;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/7/30
 * 描述:实现栈
 */
public class StackImpl {

	public static void main(String[] args) {
//		StackA<String> stackA = new StackA<>(4);
//		test(stackA);
				StackN<String> stackN = new StackN<>();
		test(stackN);
	}

	public static void test(Stack stack){
		stack.push("a");
		stack.push("b");
		stack.push("c");
		stack.push("c");
		stack.push("c");
		stack.push("c");
		System.out.println(stack.size());
		System.out.println(stack.pop());
		System.out.println(stack.pop());
		System.out.println(stack.isEmpty());
		System.out.println(stack.size());
		String tmp = (String) stack.top();
		System.out.println(tmp);
		System.out.println(stack.pop());
		System.out.println(stack.pop());
		System.out.println(stack.pop());
		System.out.println(stack.pop());
		System.out.println(stack.pop());
		System.out.println(stack.pop());
	}

	interface Stack<T> {
		void push(T item);

		T pop();

		T top();

		int size();

		boolean isEmpty();
	}

	static class StackA<T> implements Stack<T> {

		private T[] mArray;
		private int mIndex;

		public StackA(int size) {
			mArray = (T[]) new Object[size];
		}

		@Override
		public void push(T item) {
			if (mIndex > mArray.length - 1) {
				mIndex = mArray.length - 1;
				System.out.println("stack is enough");
				return;
			}
			mArray[mIndex++] = item;
		}

		@Override
		public T pop() {
			if (mIndex == 0) {
				return null;
			}
			return mArray[--mIndex];
		}

		@Override
		public T top() {
			return mArray[mIndex - 1];
		}

		@Override
		public int size() {
			return mIndex;
		}

		@Override
		public boolean isEmpty() {
			return mIndex == 0;
		}
	}

	static class StackN<T> implements Stack<T> {

		private LNode<T> mHead;
		private int mSize;

		public StackN() {
			mHead = new LNode<>();
			mHead.data = null;
			mHead.next = null;
		}

		@Override
		public void push(T item) {
			LNode<T> node = new LNode<>();
			node.data = item;
			node.next = mHead.next;
			mHead.next = node;
			mSize++;
		}

		@Override
		public T pop() {
			LNode<T> tmp = mHead.next;
			if (tmp != null) {
				mHead.next = tmp.next;
				mSize--;
				return tmp.data;
			}
			return null;
		}

		@Override
		public T top() {
			if (mHead.next != null) {
				return mHead.next.data;
			}
			return null;
		}

		@Override
		public int size() {
			return mSize;
		}

		@Override
		public boolean isEmpty() {
			return mSize == 0;
		}
	}

	static class LNode<T> {
		T data;
		LNode<T> next;

		@Override
		public String toString() {
			return "LNode{" +
					"data=" + data +
					'}';
		}
	}
}
