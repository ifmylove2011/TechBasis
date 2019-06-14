package com.xter.io;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author XTER
 * @date 2019/6/11
 */
public class NoResponse {

	public static void main(String[] args) {
		try {
			OutputStream outputStream = new FileOutputStream("1.txt");
			ExecutorService executor = Executors.newFixedThreadPool(4);
			NoResponse n = new NoResponse();
			for (int i = 0; i < 10; i++) {
				int finalI = i;
				executor.execute(new Runnable() {
					@Override
					public void run() {
						try {
							n.sendCmd(outputStream, "c" + finalI);
						} catch (IOException | InterruptedException e) {
							e.printStackTrace();
						}
					}
				});
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);

	private List<String> cmdBuffer = new ArrayList<>();

	private void sendCmd(OutputStream outputStream, String content) throws IOException, InterruptedException {
		try {
			lock.writeLock().lock();
			outputStream.write(content.getBytes());
			outputStream.flush();
			cmdBuffer.add(content);
			System.out.println("send cmd:"+content);
			TimeUnit.MILLISECONDS.sleep(200);
		} finally {
			lock.writeLock().unlock();
			if (check(content)) {
				sendCmd(outputStream, content);
			}
		}

	}

	private boolean check(String content) {
		return cmdBuffer.contains(content);
	}
}
