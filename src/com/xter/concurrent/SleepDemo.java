package com.xter.concurrent;

import java.util.concurrent.TimeUnit;

/**
 * @author XTER
 * @date 2019/6/13
 */
public class SleepDemo {
	public static void main(String[] args) {
		SleepDemo demo = new SleepDemo();
		Thread[] threads = new Thread[10];
		for (int i = 0; i < 10; i++) {
			int finalI = i;
			threads[i]= new Thread(new Runnable() {
				@Override
				public void run() {
					demo.sendMessage(finalI+"------");
				}
			});
		}
		for (int i = 0; i < 10; i++) {
			threads[i].start();
			try {
				TimeUnit.MILLISECONDS.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized void sendMessage(String s){
		System.out.println(s);
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
