package com.xter.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author XTER
 * @date 2019/6/4
 */
public class LogFileWriter {

	/**
	 * 写文件专用线程
	 */
	private ExecutorService ioThread;

	/**
	 * 缓冲区，使用BlockingQueue就无需另外加锁了；
	 * 越大则写入效率越高，但内存开销就越大，反之效率越低，内存开销越小
	 */
	private BlockingQueue<String> buffer;

	/**
	 * 缓冲区大小
	 */
	private static final int BUFFER_SIZE = 100;

	/**
	 * 单个文件大小限制
	 */
	private static final int MAX_FILE_SIZE = 2 * 1024 * 1024;

	/**
	 * 分别记录上一次IO时间，以及需要变更文件名的时间点
	 */
	private long recent, tomorrow;

	/**
	 * 用于文件命名
	 */
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);

	/**
	 * 调用是在多线程环境下，所以使用ThreadLocal避免线程安全问题
	 */
	private static final ThreadLocal<SimpleDateFormat> TIME_FORMAT = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("HH:mm:ss.SSS", Locale.CHINA);
		}
	};

	/**
	 * 文件名，会随着一些条件进行变更
	 */
	private String fileName;

	/**
	 * 用于文件名命名，主要是超出定制大小后索引自增，从1开始
	 */
	private AtomicInteger fileIndex;

	private static class Holder {
		private static LogFileWriter INSTANCE = new LogFileWriter();
	}

	public static LogFileWriter getInstance() {
		return Holder.INSTANCE;
	}

	private LogFileWriter() {
		ioThread = new ThreadPoolExecutor(1, 1,
				0L, TimeUnit.MILLISECONDS,
				new ArrayBlockingQueue<Runnable>(2));
		buffer = new ArrayBlockingQueue<>(BUFFER_SIZE);
		fileIndex = new AtomicInteger(1);
		recent = System.currentTimeMillis();
		fileName = String.format(Locale.CHINA, "%s_%02d.log", DATE_FORMAT.format(recent), fileIndex.get());
		tomorrow = checkTomorrow();
		flushForever();
	}


	public void write(String content) {
		try {
			buffer.put(TIME_FORMAT.get().format(System.currentTimeMillis()) + " /" + content);
//			buffer.put(content);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 永远监听写入时机
	 */
	private void flushForever() {
		ioThread.execute(new Runnable() {
			@Override
			public void run() {
				while (true) {
					if (buffer.size() > BUFFER_SIZE - 1 || (buffer.size() > 0 && System.currentTimeMillis() - recent > 10 * 1000)) {
						flush();
					}
				}
			}
		});
	}

	/**
	 * 写入，使用BufferedWriter再做一次缓冲
	 */
	private void flush() {
		try {
			BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getFileName(), true), StandardCharsets.UTF_8));
			int size = buffer.size();
			for (int i = 0; i < size; i++) {
				fw.write(buffer.take());
			}
			fw.flush();
			fw.close();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		} finally {
			recent = System.currentTimeMillis();
			System.out.println("flush at " + recent);
		}
	}

	/**
	 * 在两种情况下变更文件名，一是文件大小超过限制，二是时间已非当天
	 *
	 * @return filename
	 */
	private String getFileName() {
		if (System.currentTimeMillis() > tomorrow) {
			//判断时间是否已经过了凌晨0点，到了第二天
			tomorrow = checkTomorrow();
			fileIndex.set(1);
			fileName = String.format(Locale.CHINA, "%s_%02d.log", DATE_FORMAT.format(System.currentTimeMillis()), fileIndex.get());
		} else {
			//判断文件是否超过大小
			File file = new File(fileName);
			if (file.exists() && file.length() > MAX_FILE_SIZE) {
				fileName = String.format(Locale.CHINA, "%s_%02d.log", DATE_FORMAT.format(System.currentTimeMillis()), fileIndex.incrementAndGet());
			}
		}
		return fileName;
	}

	/**
	 * 得到时间临界点，这里设为两天之交凌晨0点，系统所提供的System.currentTimeMillis()是从1970年1月1日8点从开始算起
	 *
	 * @return TimeMills
	 */
	private long checkTomorrow() {
		return System.currentTimeMillis() - 8 * 3600 * 1000 + (24 * 3600 * 1000 - System.currentTimeMillis() % (24 * 3600 * 1000));
	}
}
