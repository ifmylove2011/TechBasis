package com.xter.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomDemo {
	static AtomicInteger counter = new AtomicInteger(0);

	public static void main(String[] args) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						TimeUnit.SECONDS.sleep(2);
						if (counter.addAndGet(2) > 10) {
							System.out.println("enter in stable");
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
					try {
						TimeUnit.SECONDS.sleep(5);
						counter.set(0);
						System.out.println("_------:");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true){
					try {
						TimeUnit.MILLISECONDS.sleep(500);
						System.out.println(counter.get());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
