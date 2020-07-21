package com.xter.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchDemo {
	public static void main(String[] args) {
		CountDownLatch downLatch = new CountDownLatch(10);

		ExecutorService executorService = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 10; i++) {
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					downLatch.countDown();
					System.out.println("remaing:"+downLatch.getCount());
				}
			});
		}

		try {
			downLatch.await();
			System.out.println("fire!!");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
