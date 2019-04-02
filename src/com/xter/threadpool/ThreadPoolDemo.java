package com.xter.threadpool;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolDemo {

	public static void main(String[] args) {
		Set<Integer> threadSet = new ConcurrentSkipListSet<>();

		int n = 30;
		Runnable[] tasks = new Runnable[n];
		for (int i = 0; i < tasks.length; i++) {
			final String taskExecutionFlag = "task" + (i + 1);
			tasks[i] = new Runnable() {

				@Override
				public String toString() {
					return taskExecutionFlag;
				}

				@Override
				public void run() {
					int threadHashCode = Thread.currentThread().hashCode();
					threadSet.add(threadHashCode);
					System.out.println("threadHashCode = " + threadHashCode + ", task =" + taskExecutionFlag + ", threadSize :" + threadSet.size());
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
		}

		testCustomThreadPool(tasks);
	}

	public static void testCustomThreadPool(Runnable[] tasks) {
		ExecutorService executor = new ThreadPoolExecutor(1, 5, 20, TimeUnit.MILLISECONDS, new SynchronousQueue<>(), Executors.defaultThreadFactory(), new CustomRejectHandler());
		for (int i = 0; i < tasks.length; i++) {
			executor.execute(tasks[i]);
		}
	}

	static class CustomRejectHandler implements RejectedExecutionHandler {
		@Override
		public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
			System.out.println(r.toString() + " is rejected");
		}
	}
}