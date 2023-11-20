package com.xter.io;


import com.xter.util.L;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;


/**
 * @author XTER
 * @date 2019/6/4
 * 日志写入工具
 */
public class LogFileWriter {

	private ScheduledThreadPoolExecutor ioThread;

	private BlockingQueue<String> buffer;
	private BlockingQueue<String> bufferE;

	private long recent, tomorrow;

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);

	private static final ThreadLocal<SimpleDateFormat> TIME_FORMAT = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("HH:mm:ss.SSS", Locale.CHINA);
		}
	};

	private String fileName;
	private String extraFileName;

	private AtomicInteger fileIndex;

	private static final int BUFFER_SIZE = 200;

	public static final String FILE_PREFIX = "/jinxingateway/gateway_log/";

	private static final String LOG_FORMAT = "%s%s_%02d.log";
	private static final String LOG_FORMAT_REGEX = "\\d{8}_\\d{2}.log";
	/**
	 * 日志文件总占大小预设，单位为M
	 */
	private long maxFileSize = 400;

	private static final int DAYS = 14;

	private static final int maxUnitNum = 99;
	private static final int maxUnitSize = 2 * 1024 * 1024;

	private static final long DAY_MILLS = 24 * 3600 * 1000;
	private static final long ZONE_MILLS_OFFSET = 8 * 3600 * 1000;

	static final int MODE_BUFFER = 1;
	static final int MODE_EXTRA = 2;

	private static class Holder {
		private static final LogFileWriter INSTANCE = new LogFileWriter();
	}

	private LogFileWriter() {
		maxFileSize = 10*maxUnitSize;
		ioThread = new ScheduledThreadPoolExecutor(2);
		buffer = new ArrayBlockingQueue<>(BUFFER_SIZE);
		bufferE = new ArrayBlockingQueue<>(BUFFER_SIZE);
		recent = System.currentTimeMillis();
		fileIndex = new AtomicInteger(checkFileStartIndex());
		fileName = String.format(Locale.CHINA, LOG_FORMAT, FILE_PREFIX, DATE_FORMAT.format(recent), fileIndex.get());
		extraFileName = String.format(Locale.CHINA, LOG_FORMAT, FILE_PREFIX, DATE_FORMAT.format(recent), 0);
		System.out.println(fileName+","+Thread.currentThread().getName());
		if (!new File(fileName).exists()) {
			try {
				FileOperator.createFile(fileName);
				FileOperator.createFile(extraFileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			checkClear();
		}
		tomorrow = getTomorrowMills();
		flushForever();
	}

	private int checkFileStartIndex() {
		File logDir = new File(FILE_PREFIX);
		final String filePrefix = DATE_FORMAT.format(recent);
		String[] fileNames = logDir.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if (name.startsWith(filePrefix))
					return true;
				return false;
			}
		});
		return checkFileMaxIndex(fileNames);
	}

	private int checkFileMaxIndex(String[] names) {
		int maxIndex = 1;
		if (names != null && names.length > 1) {
			int len = names.length;
			String maxFileIndex = names[0];
			for (int i = 1; i < len; i++) {
				if (names[i].compareTo(maxFileIndex) > 0) {
					maxFileIndex = names[i];
				}
			}
			maxIndex = Integer.parseInt(maxFileIndex.substring(maxFileIndex.length() - 6, maxFileIndex.length() - 4));
		}
		if (maxIndex >= maxUnitNum) {
			maxIndex = maxUnitNum;
		}
		return maxIndex;
	}

	public static LogFileWriter getInstance() {
		return Holder.INSTANCE;
	}

	public void write(String content) {
		write(content, MODE_BUFFER);
	}

	public void write(String content, int mode) {
		if (MODE_BUFFER == mode) {
			buffer.offer(TIME_FORMAT.get().format(System.currentTimeMillis()) + " /" + content);
		} else if (MODE_EXTRA == mode) {
			bufferE.offer(TIME_FORMAT.get().format(System.currentTimeMillis()) + " /" + content);
		}
	}

	private void flushForever() {
		ioThread.execute(new Runnable() {
			@Override
			public void run() {
				while (true) {
					//缓存条数超过阈值或距离上次刷新已经超过10s，那么就进行写入
					if (buffer.size() > BUFFER_SIZE * 0.75 || (buffer.size() > 0 && System.currentTimeMillis() - recent > 10 * 1000)) {
						flush();
					}
					try {
						TimeUnit.SECONDS.sleep(2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		ioThread.execute(new Runnable() {
			@Override
			public void run() {
				while (true) {
					//缓存条数超过阈值或距离上次刷新已经超过10s，那么就进行写入
					if (bufferE.size() > BUFFER_SIZE * 0.4 || (buffer.size() > 0 && System.currentTimeMillis() - recent > 20 * 1000)) {
						flushExtra();
					}
					try {
						TimeUnit.SECONDS.sleep(2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	public String createMessage(String msg, Object... args) {
		return (args == null || args.length == 0) ? (msg == null ? "" : msg) : String.format(msg, args);
	}

	private void flush() {
		try {
			FileLocker.get().getDefaultLock().writeLock().lock();
			System.out.println("1");
			BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getFileName(), true), StandardCharsets.UTF_8));
			int size = buffer.size();
			for (int i = 0; i < size; i++) {
				fw.write(buffer.take());
			}
			fw.flush();
			fw.close();
		} catch (FileNotFoundException e) {
			File file = new File(getFileName());
			if (!file.exists()) {
				try {
					FileOperator.createFile(getFileName());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		} finally {
			System.out.println("1");
			recent = System.currentTimeMillis();
			FileLocker.get().getDefaultLock().writeLock().unlock();
		}
	}

	private void flushExtra(){
		try {
			FileLocker.get().getLock("2").writeLock().lock();
			System.out.println("2");
			BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getExtraFileName(), true), StandardCharsets.UTF_8));
			int size = bufferE.size();
			for (int i = 0; i < size; i++) {
				fw.write(bufferE.take());
			}
			fw.flush();
			fw.close();
		} catch (FileNotFoundException e) {
			File file = new File(getExtraFileName());
			if (!file.exists()) {
				try {
					FileOperator.createFile(getExtraFileName());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		} finally {
			System.out.println("2");
			recent = System.currentTimeMillis();
			FileLocker.get().getLock("2").writeLock().unlock();
		}
	}


	private String getExtraFileName() {
		if (System.currentTimeMillis() > tomorrow) {
			extraFileName = String.format(Locale.CHINA, LOG_FORMAT, FILE_PREFIX, DATE_FORMAT.format(tomorrow), 0);
		} else {
			if (extraFileName == null) {
				extraFileName = String.format(Locale.CHINA, LOG_FORMAT, FILE_PREFIX, DATE_FORMAT.format(System.currentTimeMillis()), 0);
			}
		}
		System.out.println(extraFileName+","+Thread.currentThread().getName());
		return extraFileName;
	}

	private String getFileName() {
		//在两种情况下变更文件，一是文件大小超过限制，二是时间已非当天
		if (System.currentTimeMillis() > tomorrow) {
			fileIndex.set(1);
			fileName = String.format(Locale.CHINA, LOG_FORMAT, FILE_PREFIX, DATE_FORMAT.format(tomorrow), fileIndex.get());
			tomorrow = getTomorrowMills();
		} else {
			File file = new File(fileName);
			if (file.exists() && file.length() > maxUnitSize) {
				if (fileIndex.get() >= maxUnitNum) {
					fileIndex.set(0);
				}
				fileName = String.format(Locale.CHINA, LOG_FORMAT, FILE_PREFIX, DATE_FORMAT.format(System.currentTimeMillis()), fileIndex.incrementAndGet());
				checkMax();
			}
		}
		System.out.println(fileName+","+Thread.currentThread().getName());
		return fileName;
	}

	private void checkMax() {
		//索引到99后,重新归1,此时1的大小也已经超过2M,需要删除重建
		File fileNew = new File(fileName);
		if (fileNew.exists()) {
			if (fileNew.length() > maxUnitSize) {
				try {
					fileNew.delete();
					fileNew.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			try {
				fileNew.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private long getTomorrowMills() {
		return System.currentTimeMillis() + DAY_MILLS - (System.currentTimeMillis() % DAY_MILLS + ZONE_MILLS_OFFSET) % DAY_MILLS;
	}

	/**
	 * 清理日志文件；
	 * 一，当天清理；
	 * 二，定期清理；
	 * 三，低剩余清理；
	 */
	private void checkClear() {
		File logDir = new File(FILE_PREFIX);
		long logSize = dirSize(logDir);
		System.out.println("check log size:" + logSize + "M");
		if (logSize > maxFileSize) {
			int days = (int) (maxFileSize / 200);
			clearDays(logDir, days);
		} else {
			clearDays(logDir, DAYS);
		}
	}


	/**
	 * 清理X天之前的日志
	 *
	 * @param logDir 目录
	 * @param days   时隔天数
	 */
	private void clearDays(File logDir, int days) {
		final String filePrefix = DATE_FORMAT.format(recent - (long) days * 24 * 3600 * 1000);
		File[] files = logDir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.compareTo(filePrefix) < 0 || !Pattern.matches(LOG_FORMAT_REGEX, name);
			}
		});
		if (files != null && files.length > 0) {
			L.d("清理" + Arrays.toString(files));
			deleteFiles(files);
		}
	}


	private long dirSize(File dir) {
		File[] all = dir.listFiles();
		long size = 0;
		for (File f : all) {
			size += f.length();
		}
		return size / 1024 / 1024;
	}

	public synchronized void deleteFiles(File... files) {
		for (File f : files) {
			if (f.exists()) {
				f.delete();
			}
		}
	}
}
