package com.xter.concurrent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class WriteReadLockDemo {
	public static void main(String[] args) {
		ChangeLog changeLog = new ChangeLog();
		changeLog.startWrite();
		changeLog.startRead(new ChangeLog.Listener() {
			@Override
			public void onFinish() {
				System.out.println("onFinish:"+Thread.currentThread().getName());
			}
		});
	}

	private static class ChangeLog {
		ReentrantReadWriteLock lock;
		ReentrantReadWriteLock lock1;

		public ChangeLog() {
			lock = new ReentrantReadWriteLock(true);
			lock1 = new ReentrantReadWriteLock(true);
		}

		public void startWrite() {
			new Thread(new Runnable() {
				@Override
				public void run() {
					while (true){
						File file = new File("change_log.txt");
						try {
							lock.writeLock().lock();
							if (!file.exists()) {
								file.createNewFile();
							}
							FileWriter fw = new FileWriter(file,true);
							System.out.println("正在写入...");
							fw.write(System.currentTimeMillis()+"");
							fw.flush();
							fw.close();
							lock.writeLock().unlock();
							TimeUnit.SECONDS.sleep(1);
						} catch (IOException | InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
		}

		public void startRead(Listener listener) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					while (true){
						File file = new File("change_log.txt");
						try {
							lock.readLock().lock();
							System.out.println(Thread.currentThread().getName());
							System.out.println("正在读取1...");
							FileInputStream fis = new FileInputStream(file);
							System.out.println(fis.available());
							TimeUnit.SECONDS.sleep(2);
							System.out.println("正在读取2...");
							FileInputStream fis1 = new FileInputStream(file);
							System.out.println(fis1.available());
							lock.readLock().unlock();
							TimeUnit.SECONDS.sleep(2);
							listener.onFinish();
						} catch (IOException | InterruptedException e) {
							e.printStackTrace();
						}finally {
//							lock.readLock().unlock();
						}
					}

				}
			}).start();
		}

		public interface Listener{
			void onFinish();
		}
	}

}
