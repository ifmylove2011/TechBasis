package com.xter.queue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;

public class SyncQueueDemo {


	public static void main(String[] args) {
//		testSyncQueue();
	}

	public static void testSyncQueue() {
		SynchronousQueue<Integer> queue = new SynchronousQueue<>(true);
		new Thread(() -> {
			while (queue.isEmpty()) {
				try {
					System.out.println(queue.take());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

		new Thread(() -> {
			try {
				for (int i = 0; i < 20; i++) {
					queue.put(i);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}



}
