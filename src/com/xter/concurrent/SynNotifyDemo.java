package com.xter.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SynNotifyDemo {
	public static void main(String[] args) {
		test2();
	}

	public static void test1(){
		BlockingQueue<String> blockingQueue = new BlockingQueue<>();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					for (int i = 0; i < 40; i++) {
						TimeUnit.SECONDS.sleep(2);
						blockingQueue.put(i + "");
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

		while (blockingQueue.isEmpty()){
			try {
				String item = blockingQueue.take();
				System.out.println(item);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("Done");
	}

	public static void test2(){
		BlockingQueueX<String> blockingQueue = new BlockingQueueX<>(2);

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					for (int i = 0; i < 40; i++) {
						TimeUnit.SECONDS.sleep(1);
						blockingQueue.put(i + "");
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

		while (true){
			try {
				TimeUnit.SECONDS.sleep(2);
				String item = blockingQueue.take();
				System.out.println(item);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
