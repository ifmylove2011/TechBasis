package com.xter.io;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author XTER
 * @date 2019/6/4
 */
public class CurrentFileWriteDemo {

	public static void main(String[] args) throws IOException {
//		FileOperator.copyfile("E:\\studying\\StudioProjects\\TechBasis\\src\\com\\xter\\design\\proxy\\DBM.java","E:\\study\\ssss\\11");
//		System.out.println(dir.isDirectory());
//		System.out.println(dir.getName());
//		System.out.println(dir.getPath());
		System.out.println(LogFileWriter.getInstance().createMessage("%s,%d","hhahahah",3));
//		for (int i = 0; i < 10; i++) {
//			new Thread(new Runnable() {
//				@Override
//				public void run() {
//					for (int j = 0; j < 5000; j++) {
//						try {
//							TimeUnit.MILLISECONDS.sleep(100);
//							LogFileWriter.getInstance().write(System.currentTimeMillis() + "---------" + Thread.currentThread().getName() + "------" + j + "\r\n");
//							LogFileWriter.getInstance().write(System.currentTimeMillis() + "---------" + Thread.currentThread().getName() + "------" + j + "\r\n",LogFileWriter.MODE_EXTRA);
//
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
//					}
//				}
//			}).start();
//		}
//		for (int i = 0; i < 5; i++) {
//			new Thread(new Runnable() {
//				@Override
//				public void run() {
//					for (int j = 0; j < 5000; j++) {
//						try {
//							TimeUnit.MILLISECONDS.sleep(200);
//							LogFileWriter.getInstance().write(System.currentTimeMillis() + "---------" + Thread.currentThread().getName() + "------" + j + "\r\n",LogFileWriter.MODE_EXTRA);
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
//					}
//				}
//			}).start();
//		}

//		String logPath = "/jinxingateway/gateway_log/20230406_01.log";
//		String logPathTemp = "/jinxingateway/gateway_log/temp.log";
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					while (true){
//						FileLocker.get().getDefaultLock().readLock().lock();
//						FileOperator.copyfile(logPath,logPathTemp);
//						FileLocker.get().getDefaultLock().readLock().unlock();
//						TimeUnit.SECONDS.sleep(5);
//					}
//				} catch (IOException | InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//		}).start();

//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
//		try {
//			System.out.println(System.currentTimeMillis());
//			System.out.println(dateFormat.parse(dateFormat.format(System.currentTimeMillis())).getTime());
//			System.out.println(System.currentTimeMillis() - dateFormat.parse(dateFormat.format(System.currentTimeMillis())).getTime());
//			System.out.println(System.currentTimeMillis()-8*3600*1000+(24 * 3600 * 1000 - System.currentTimeMillis() % (24 * 3600 * 1000)));
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
	}
}
