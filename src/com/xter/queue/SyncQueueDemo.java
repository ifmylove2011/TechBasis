package com.xter.queue;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class SyncQueueDemo {


	public static void main(String[] args) {
		testSyncQueue();
	}

	public static void testSyncQueue() {
		SynchronousQueue<Integer> queue = new SynchronousQueue<>(true);
		new Thread(() -> {
			while (true) {
				try {
					System.out.println(queue.poll(1500,TimeUnit.MILLISECONDS));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

		new Thread(() -> {
			try {
				for (int i = 0; i < 20; i++) {
					TimeUnit.SECONDS.sleep(1);
					queue.put(i);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}



}
