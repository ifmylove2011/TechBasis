package com.xter.queue;

import com.xter.concurrent.TaskDelayer;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class SyncQueueDemo {


	public static void main(String[] args) {
//		testSyncQueue();
		testDelayTask();
	}

	public static void testDelayTask() {
		Runnable task1 = new Runnable() {
			@Override
			public void run() {
				System.out.println("我是任务一");
			}
		};
		Runnable task2 = new Runnable() {
			@Override
			public void run() {
				System.out.println("我是任务二");
			}
		};
		new Thread(new Runnable() {
			@Override
			public void run() {
				int i=0;
				while (true){
					System.out.println(i++);
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 30; i++) {
					System.out.println("------"+i);
					TaskDelayer.get().throttleLastestTask("1", task1);
//					try {
//						TimeUnit.MILLISECONDS.sleep(200);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
				}
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i <10; i++) {
					System.out.println("+++++++++"+i);
					TaskDelayer.get().throttleLastestTask("2", task2);
//					try {
//						TimeUnit.MILLISECONDS.sleep(500);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
				}
			}
		}).start();
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					TimeUnit.SECONDS.sleep(10);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				for (int i = 0; i <10; i++) {
//					TaskDelayer.get().throttleLastestTask("2", task2);
//					try {
//						TimeUnit.MILLISECONDS.sleep(500);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}).start();
	}

	public static void testSyncQueue() {
		SynchronousQueue<Integer> queue = new SynchronousQueue<>(true);
//		new Thread(() -> {
//			while (true) {
//				try {
//					System.out.println(queue.poll(500,TimeUnit.MILLISECONDS));
////					System.out.println(queue.take());
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						if ((queue.poll(2, TimeUnit.SECONDS) == null)) break;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("-------");
			}
		}).start();
		new Thread(() -> {
			try {
				for (int i = 0; i < 20; i++) {
//					TimeUnit.SECONDS.sleep(1);
					queue.put(i);
//					queue.offer(i,200,TimeUnit.MILLISECONDS);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}


}
