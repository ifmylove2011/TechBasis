package com.xter.queue;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author XTER
 * @desc
 * @date 2019/12/6
 */
public class LinkedQueueDemo {

	public static void main(String[] args) {
		new LinkedQueueDemo().testLinked();
	}

	LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>(10);

	public  void testLinked() {
		//放的线程
		new Thread(new Runnable() {
			@Override
			public void run() {
				int count = 0;
				while (true){
					try {
						queue.put(count);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
//					try {
//						TimeUnit.MILLISECONDS.sleep(200);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}finally {
						count++;
//					}
				}
			}
		}).start();

		//出的线程
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true){
					try {
						//一直去拿消息，拿不到就阻塞
						System.out.println(queue.take());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}


}
