package com.xter.io;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author XTER
 * @date 2019/6/4
 */
public class CurrentFileWriteDemo {

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (int j = 0; j < 50000; j++) {
						LogFileWriter.getInstance().write(System.currentTimeMillis() + "---------" + Thread.currentThread().getName() + "------" + j + "\r\n");
					}
				}
			}).start();
		}

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
