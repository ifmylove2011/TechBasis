package com.xter.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * 问题：为一组定时上报的消息加上开始与结束标志，即当一个消息不再定时上报时，作为其结束标志
 */
public class EndDemo {

	public static void main(String[] args) {
		EndDemo demo = new EndDemo();

		try {
			for (int i = 0; i < 20; i++) {
				if (i % 4 == 0) {
					TimeUnit.MILLISECONDS.sleep(200);
				} else {
					TimeUnit.MILLISECONDS.sleep(50);
				}
				demo.check2(i);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private long trackTime = 0;
	private Thread trackThread;
	private volatile boolean flag;
	private volatile int count;

	public void check1(int counter) {
		count = counter;
		if (trackThread == null) {
			trackThread = new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						try {
							TimeUnit.MILLISECONDS.sleep(30);
							if (flag&& System.currentTimeMillis() - trackTime > 100) {
								System.out.println("end:"+count);
								flag = false;
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			});
			trackThread.start();
		}
		if (!flag && System.currentTimeMillis() - trackTime > 100) {
			System.out.println("start:" + counter);
			flag = true;
		}
		trackTime = System.currentTimeMillis();
	}

	SynchronousQueue<Integer> queue = new SynchronousQueue<>(true);

	public void check2(int counter) {
		queue.offer(counter);

		if (trackThread == null) {
			trackThread = new Thread(new Runnable() {
				@Override
				public void run() {
					Integer temp = 0;
					int end = 0;
					while (true) {
						try {
							temp = queue.poll(100,TimeUnit.MILLISECONDS);
							if(temp==null){
								System.out.println("end:"+end);
							}else{
								end = temp.intValue();
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			});
			trackThread.start();
		}
		if ( System.currentTimeMillis() - trackTime > 100) {
			System.out.println("start:" + counter);
		}
		trackTime = System.currentTimeMillis();
	}
}
