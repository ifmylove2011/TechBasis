package com.xter.io;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import sun.misc.Unsafe;

/**
 * @author XTER
 * @date 2019/6/4
 */
public class LogFileWriter {

	private ExecutorService executors;

	private ReentrantReadWriteLock lock;

	//	private Vector<String> cache;
	private BlockingQueue<String> cache;

	private FileWriter fw;

	private static class Holder {
		private static LogFileWriter INSTANCE = new LogFileWriter();
	}

	private LogFileWriter() {
		executors = new ThreadPoolExecutor(5, 10, 0L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));

		lock = new ReentrantReadWriteLock(true);

//		cache = new Vector<>(50);
		cache = new ArrayBlockingQueue<>(60);
		flushForever();
	}

	public static LogFileWriter getInstance() {
		return Holder.INSTANCE;
	}

	public void write(String content) {
		try {
			cache.put(content);
			if (cache.size() > 50) {
				System.out.println("flush");
//				flush();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private void flushForever() {
		executors.execute(new Runnable() {
			@Override
			public void run() {
				while (true) {
					if(cache.size()>50){
						try {
							fw = new FileWriter("20190604.log", true);
							for (int i = 0; i < 50; i++) {
								fw.write(cache.take());
							}
							fw.flush();
							fw.close();
						} catch (IOException | InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
	}

	private void flush() {
		executors.execute(new Runnable() {
			@Override
			public void run() {
				try {
					lock.writeLock().lock();
					fw = new FileWriter("20190604.log", true);
					for (int i = 0; i < 50; i++) {
//						fw.write(cache.take());
//						cache.removeElementAt(i);
					}
					fw.flush();
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					lock.writeLock().unlock();
				}
			}
		});
	}
}
