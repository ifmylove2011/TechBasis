package com.xter.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/4/27
 * 描述:
 */
public class RemoveTaskDemo {

	public static void main(String[] args) {
		testNormalRemove();
//		testInterrupt();
	}

	private static void testNormalRemove() {
		ThreadPoolExecutor tpe = new ThreadPoolExecutor(1, 3, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(3));
//		ThreadPoolExecutor tpe = new ThreadPoolExecutor(2, 3, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(3));

		Thread task1 = new Thread(new Runnable() {
			@Override
			public void run() {
					while (true) {
						try {
							TimeUnit.SECONDS.sleep(2);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println("normal1," + Thread.currentThread().getId());
					}
				}
		});
		Thread task2 = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						TimeUnit.SECONDS.sleep(3);
					} catch (InterruptedException e) {
						e.printStackTrace();
						return;
					}
					System.out.println("normal2," + Thread.currentThread().getId());
				}
			}
		});
		tpe.execute(task1);
		tpe.execute(task2);

		try {
			TimeUnit.SECONDS.sleep(6);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(tpe.getQueue().size());
		System.out.println("remove task1," + tpe.remove(task1));
		System.out.println("remove task2," + tpe.remove(task2));

		try {
			TimeUnit.SECONDS.sleep(6);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private static void testInterrupt(){
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true){
					try {
						TimeUnit.SECONDS.sleep(2);
					} catch (InterruptedException e) {
						e.printStackTrace();
						return;
					}
				}
			}
		});
		thread.start();

		try {
			TimeUnit.SECONDS.sleep(4);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		thread.interrupt();
	}
}
