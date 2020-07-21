package com.xter.demo;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author XTER
 * @desc 问题：对一组部分定时上报的数据进行分段，输出每段数据的开始与结束标志
 * @date 2019/10/12
 */
public class EndDemo {

	public static void main(String[] args) {

//		Determiner determiner = new Determiner("task1");
		Blocker blocker = new Blocker("task2");
		try {
			for (int i = 0; i < 20; i++) {
				if (i % 4 == 0) {
					TimeUnit.MILLISECONDS.sleep(200);
				} else {
					TimeUnit.MILLISECONDS.sleep(50);
				}
//				determiner.check(i);
				blocker.check(i);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 利用粒度更小的时间去轮询
	 */
	private static class Determiner {

		private long trackTime = 0;
		private Thread trackThread;
		private volatile boolean startFlag;
		private volatile int count;

		Determiner(String taskName) {
			trackThread = new Thread(() -> {
				try {
					while (true) {
						if (startFlag) {
							TimeUnit.MILLISECONDS.sleep(30);
							if (System.currentTimeMillis() - trackTime > 100) {
								System.out.println("end:" + count);
								startFlag = false;
							}
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}, taskName);
		}

		void check(int counter) {
			if (!trackThread.isAlive()) {
				trackThread.start();
			}
			count = counter;
			if (!startFlag && System.currentTimeMillis() - trackTime > 100) {
				System.out.println("start:" + counter);
				startFlag = true;
			}
			trackTime = System.currentTimeMillis();
		}
	}

	/**
	 * 利用阻塞队列的特性
	 */
	private static class Blocker {
		private SynchronousQueue<Integer> queue;
		private Thread trackThread;
		private volatile boolean startFlag;

		Blocker(String taskName) {
			queue = new SynchronousQueue<>(true);
			trackThread = new Thread(() -> {
				try {
					Integer temp;
					int end = 0;
					while (true) {
						if (startFlag) {
							temp = queue.poll(150, TimeUnit.MILLISECONDS);
							if (temp == null) {
								System.out.println("end:" + end);
								startFlag = false;
							} else {
								end = temp;
							}
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}, taskName);
		}

		void check(int counter) {
			if (!trackThread.isAlive()) {
				trackThread.start();
			}
			if (!startFlag && queue.isEmpty()) {
				System.out.println("start:"+counter);
				startFlag = true;
			}
			queue.offer(counter);
		}
	}
}
