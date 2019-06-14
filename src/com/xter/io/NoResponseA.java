package com.xter.io;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author XTER
 * @date 2019/6/11
 */
public class NoResponseA {

	public static void main(String[] args) {
		try {
			NoResponseA noResponseA = new NoResponseA();
			noResponseA.outputStream = new FileOutputStream("1.txt");
			noResponseA.startCheckThread();
			for (int i = 0; i < 20; i++) {
				noResponseA.sendCmd(noResponseA.outputStream, "C" + i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private ReentrantLock lock = new ReentrantLock(true);

	OutputStream outputStream = null;

	/**
	 * 需要重新发送的指令列表
	 */
	private List<String> resendCmdList = new ArrayList<>();

	/**
	 * 已经发送过的指令及发送时间
	 */
	private Map<String, Long> cmdBuffer = new LinkedHashMap<>();

	private void sendCmd(OutputStream outputStream, String content) throws IOException {
		outputStream.write(content.getBytes());
		outputStream.flush();
		System.out.println("send cmd:" + content);
		cmdBuffer.put(content, System.currentTimeMillis());
	}

	private void startCheckThread() {

		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						TimeUnit.MILLISECONDS.sleep(200);
						check();
					} catch (InterruptedException | IOException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		//模拟指令有应答需要删除的情况
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					Random random = new Random();
					long delay = (random.nextInt(4) + 2) * 100;
					try {
						TimeUnit.MILLISECONDS.sleep(delay);
						randomRemove();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	/**
	 * 检查当前需要重新发送的指令
	 * @throws IOException
	 */
	private void check() throws IOException {
		lock.lock();
		for (Map.Entry<String, Long> entry : cmdBuffer.entrySet()) {
			String cmd = entry.getKey();
			if (System.currentTimeMillis() - entry.getValue() > 2000) {
				System.out.println("add timeout cmd:" + cmd);
				resendCmdList.add(cmd);
			}
		}
		lock.unlock();
		repeat();
	}

	/**
	 * 发送一些超时的指令
	 * @throws IOException
	 */
	private void repeat() throws IOException {
		System.out.println("需要重发的指令："+resendCmdList);
		for (String cmd : resendCmdList) {
			sendCmd(outputStream, cmd);
		}
		resendCmdList.clear();
	}

	/**
	 * 随机删除其中一个
	 */
	private void randomRemove() {
		lock.lock();
		int size = cmdBuffer.size();
		if (size == 0) {
			return;
		}
		int index = new Random().nextInt(size);
		int count = 0;
		Iterator<Map.Entry<String, Long>> entryIterator = cmdBuffer.entrySet().iterator();
		while (entryIterator.hasNext()) {
			if (count == index) {
				System.out.println("remove:" + entryIterator.next().getKey());
				entryIterator.remove();
				break;
			}
			count++;
		}
		lock.unlock();
	}

}
