package com.xter.algorithm.exercise;

import java.util.Stack;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/8/4
 * 描述:用两个栈模拟队列
 */
public class StackSimulateQueue {

	public static void main(String[] args) {
		QueueS<String> queueS = new QueueS<>();
		queueS.offer("a");
		queueS.offer("b");
		queueS.offer("c");
		System.out.println(queueS.poll());
		queueS.offer("d");
		queueS.offer("e");
		System.out.println(queueS.poll());
		System.out.println(queueS.poll());
		System.out.println(queueS.poll());
		System.out.println(queueS.poll());
		System.out.println(queueS.poll());
	}

	static class QueueS<T> implements QueueImpl.Queue<T> {

		private Stack<T> pushStack;
		private Stack<T> popStack;

		QueueS() {
			pushStack = new Stack<>();
			popStack = new Stack<>();
		}

		@Override
		public void offer(T element) {
			pushStack.push(element);
		}

		@Override
		public T poll() {
			if (popStack.isEmpty()) {
				while (!pushStack.isEmpty()) {
					popStack.push(pushStack.pop());
				}
			}
			return popStack.isEmpty() ? null : popStack.pop();
		}

		@Override
		public T peak() {
			return popStack.isEmpty() ? null : popStack.peek();
		}

		@Override
		public T tail() {
			return pushStack.isEmpty() ? null : pushStack.peek();
		}

		@Override
		public int size() {
			return pushStack.size() + popStack.size();
		}

		@Override
		public boolean isEmpty() {
			return pushStack.isEmpty() && popStack.isEmpty();
		}
	}
}
