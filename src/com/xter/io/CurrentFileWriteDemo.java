package com.xter.io;

/**
 * @author XTER
 * @date 2019/6/4
 */
public class CurrentFileWriteDemo {

	public static void main(String[] args) {
		for (int i = 0; i < 5; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (int j = 0; j < 10000; j++) {
						LogFileWriter.getInstance().write(System.currentTimeMillis() + "---------" + Thread.currentThread().getName() + "------" + j + "\r\n");
					}
				}
			}).start();
		}
	}
}
