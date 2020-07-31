package com.xter.algorithm.exercise;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/7/31
 * 描述:队列实现
 */
public class QueueImpl {

	public static void main(String[] args) {
		Queue<String> queueA = new QueueA<String>(5);
		Queue<String> queueN = new QueueN<String>();
		testQueue(queueA);
//		testQueue(queueN);
	}

	public static void testQueue(Queue queue) {
		queue.offer("a");
		queue.offer("b");
		System.out.println(queue.isEmpty());
		System.out.println(queue.size());
		queue.offer("c");
		System.out.println(queue.poll());
		System.out.println(queue.peak());
		queue.offer("d");
		queue.offer("e");
		queue.offer("f");
		System.out.println(queue.poll());
		System.out.println(queue.poll());
		System.out.println(queue.poll());
		System.out.println(queue.poll());
		System.out.println(queue.poll());
		System.out.println(queue.poll());
		queue.offer("g");
		queue.offer("h");
		queue.offer("i");
		System.out.println(queue.poll());
		System.out.println(queue.poll());
		System.out.println(queue.poll());
		System.out.println(queue.poll());
		queue.offer("j");
		queue.offer("k");
		queue.offer("l");
		System.out.println(queue.isEmpty());
		System.out.println(queue.size());
		System.out.println(queue.tail());
	}

	static class QueueN<T> implements Queue<T> {

		private StackImpl.LNode<T> mHead;
		private StackImpl.LNode<T> mTail;

		public QueueN() {
			mHead = new StackImpl.LNode<>();
			mTail = new StackImpl.LNode<>();
		}

		@Override
		public void offer(T element) {
			StackImpl.LNode<T> node = new StackImpl.LNode<>();
			node.data = element;
			if (mTail.next != null) {
				mTail.next.next = node;
			}
			mTail.next = node;
			if (mHead.next == null) {
				mHead.next = node;
			}
		}

		@Override
		public T poll() {
			StackImpl.LNode node = mHead.next;
			if (node != null) {
				mHead.next = node.next;
				node.next = null;
				return (T) node.data;
			}else{
				return null;
			}
		}

		@Override
		public T peak() {
			return mHead.next == null ? null : mHead.next.data;
		}

		@Override
		public T tail() {
			return mTail.next == null ? null : mTail.next.data;
		}

		@Override
		public int size() {
			StackImpl.LNode tmp = mHead.next;
			int counter = 0;
			while (tmp != null) {
				tmp = tmp.next;
				counter++;
			}
			return counter;
		}

		@Override
		public boolean isEmpty() {
			return mTail.next == null;
		}
	}

	static class QueueA<T> implements Queue<T> {

		private Object[] mArray;
		private int head;
		private int tail;
		private int capacity;

		public QueueA(int size) {
			capacity = size;
			mArray = new Object[capacity];
		}

		@Override
		public void offer(T element) {
			if (size() >= capacity) {
				System.out.println("queue is enough");
				return;
			}
			if (tail < capacity) {
				mArray[tail++] = element;
			} else {
				tail = tail - capacity;
				mArray[tail++] = element;
			}
		}

		@Override
		public T poll() {
			if (size() > 0) {
				if (head < capacity) {
					return (T) mArray[head++];
				} else {
					head = head - capacity;
					return (T) mArray[head++];
				}
			}
			System.out.println("queue is empty");
			return null;
		}

		@Override
		public T peak() {
			if (size() > 0) {
				return (T) mArray[head];
			}
			return null;
		}

		@Override
		public T tail() {
			if (size() > 0) {
				return (T) mArray[tail - 1];
			}
			return null;
		}

		@Override
		public int size() {
			if (tail >= head) {
				return tail - head;
			} else {
				return tail - head + capacity;
			}
		}

		@Override
		public boolean isEmpty() {
			return size() == 0;
		}
	}

	interface Queue<T> {
		void offer(T element);

		T poll();

		T peak();

		T tail();

		int size();

		boolean isEmpty();
	}
}
