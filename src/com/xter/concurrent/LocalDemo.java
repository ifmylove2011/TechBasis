package com.xter.concurrent;

import java.util.concurrent.TimeUnit;

public class LocalDemo {
	public static void main(String[] args) {
		ThreadLocal<String> threadLocal = new ThreadLocal<>();
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName());
				threadLocal.set("1");
				try {
					TimeUnit.SECONDS.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(threadLocal.get());
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName());
				threadLocal.set("2");
				try {
					TimeUnit.SECONDS.sleep(4);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(threadLocal.get());
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName());
				threadLocal.set("3");
				try {
					TimeUnit.SECONDS.sleep(3);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(threadLocal.get());
			}
		}).start();
	}
}
