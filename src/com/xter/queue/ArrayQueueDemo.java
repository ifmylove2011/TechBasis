package com.xter.queue;

import com.xter.util.TimeGetter;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;


public class ArrayQueueDemo {
	private ExecutorService executors = Executors.newSingleThreadExecutor();
	private ArrayBlockingQueue<String> speakQueue = new ArrayBlockingQueue<>(10);
	private ArrayBlockingQueue<String> cmdQueue = new ArrayBlockingQueue<>(30,true);
	private ReentrantLock lock = new ReentrantLock(true);

	public static void main(String[] args) {
//		testAction();
//		System.out.println(536830910&536830911);
		testCmd();
	}

	public static void testAction() {
		ArrayQueueDemo demo = new ArrayQueueDemo();
//		for (int i = 0; i < 40; i++) {
//			demo.speak(i + "");
//		}
		demo.test();
	}

	public void test() {
		for (int i = 0; i < 10; i++) {
			if (speakQueue.isEmpty())
				speakQueue.add(i + "");
		}
		System.out.println(speakQueue.size());
	}

	public void speak(String content) {
		executors.execute(() -> {
			try {
				String content1 = speakQueue.take();
				System.out.println(Thread.currentThread().hashCode() + "," + content1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		try {
			speakQueue.put(content);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void testCmd() {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(15, 20, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
		ArrayQueueDemo demo = new ArrayQueueDemo();
		demo.writeCommandSuspend();
		for (int i = 0; i < 1000; i++) {
			String command = String.format(Locale.CHINA, "%020d", i);
			executor.execute(new Runnable() {
				@Override
				public void run() {
					demo.sendCommand(command);
//					System.out.println(command);
				}
			});
		}
	}

	public void sendCommand(String command) {
		try {
			lock.lock();
			System.out.println("-------------"+Thread.currentThread().getId()+"-----------------");
//			TimeUnit.MILLISECONDS.sleep(500);
			cmdQueue.put(command);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}

	public void writeCommandSuspend() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (true){
						String command = cmdQueue.take();
						System.out.println(TimeGetter.getCurrentTime() + "\n" + command);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
