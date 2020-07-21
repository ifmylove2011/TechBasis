package com.xter.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/4/24
 * 描述:
 */
public class ScheduleDemo {
	public static void main(String[] args) throws InterruptedException {
//		testRemove2();
		testScheduleTimeout();
	}

	/**
	 * 无法正常移除任务
	 */
	public static void testRemove1() throws InterruptedException {
		Runnable runnable = new Task();
		ScheduledThreadPoolExecutor scheduledExecutorService = new ScheduledThreadPoolExecutor(2);
		scheduledExecutorService.scheduleWithFixedDelay(runnable, 0, 1, TimeUnit.SECONDS);
		TimeUnit.SECONDS.sleep(5);
		System.out.println("start");
		System.out.println(scheduledExecutorService.remove(runnable));
		System.out.println("end");
	}

	/**
	 * 源码中是封装为RunnableScheduledFuture添加进任务队列的，所以删除也得这么干
	 *         RunnableScheduledFuture<Void> t = decorateTask(command, sft);
	 *         sft.outerTask = t;
	 *         delayedExecute(t);
	 */
	public static void testRemove2() throws InterruptedException {
		Runnable runnable = new Task();
		ScheduledThreadPoolExecutor scheduledExecutorService = new ScheduledThreadPoolExecutor(2);
		RunnableScheduledFuture<?> task1 = (RunnableScheduledFuture<?>) scheduledExecutorService.scheduleWithFixedDelay(runnable, 0, 1, TimeUnit.SECONDS);
		TimeUnit.SECONDS.sleep(5);
		System.out.println("start");
		System.out.println(scheduledExecutorService.remove(task1));
		System.out.println("end");
	}

	public static void testScheduleTimeout(){
		ScheduledThreadPoolExecutor scheduledExecutorService = new ScheduledThreadPoolExecutor(2);
		Runnable runnable = new TaskA();
		RunnableScheduledFuture<?> task1 = (RunnableScheduledFuture<?>) scheduledExecutorService.scheduleWithFixedDelay(runnable, 0, 1, TimeUnit.SECONDS);
//		Callable<String> callable = new TaskB();
//		Future<String> task1 = scheduledExecutorService.submit(callable);
		try {
			task1.get(3,TimeUnit.SECONDS);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			System.out.println();
		}
		System.out.println("end");
	}

	static class Task implements Runnable {

		@Override
		public void run() {
			System.out.println(Thread.currentThread().getId());
		}
	}

	static class TaskA implements Runnable {

		@Override
		public void run() {

			try {
				TimeUnit.SECONDS.sleep(4);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getId());
		}
	}

	static class TaskB implements Callable<String> {

		@Override
		public String call() throws Exception {
			TimeUnit.SECONDS.sleep(2);
			return Thread.currentThread().getName();
		}
	}
}
