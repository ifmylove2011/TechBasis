package com.xter.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CountDownLatchDemo {

	static CountDownLatch downLatch;
	public static void main(String[] args) {
		for (int i = 0; i < 3; i++) {
			test();
		}
	}

	public static void test(){
		downLatch = new CountDownLatch(10);
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 10; i++) {
			int finalI = i;
			executorService.execute(() -> {
				try {
					TimeUnit.SECONDS.sleep(finalI);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				downLatch.countDown();
				System.out.println("remaing:"+downLatch.getCount());
			});
		}

		try {
//			downLatch.await();
			downLatch.await(5,TimeUnit.SECONDS);
			if(downLatch.getCount()==0){
				System.out.println("fire!!");
			}else{
				System.out.println("time out!!");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
