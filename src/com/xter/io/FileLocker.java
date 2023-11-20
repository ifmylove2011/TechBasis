package com.xter.io;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author XTER
 * 项目名称: Gateway3
 * 创建时间: 2021/11/1
 * 描述:针对文件的读写锁，默认为公平锁；
 * 目前仅日志文件使用
 */
public class FileLocker {

	private static class Holder {
		static FileLocker INSTANCE = new FileLocker();
	}

	public static FileLocker get() {
		return Holder.INSTANCE;
	}

	private static final String DEFAULT = "FileLock";

	private Map<String, ReentrantReadWriteLock> lockMap;

	private FileLocker() {
		lockMap = new HashMap<>(4);
	}

	public ReentrantReadWriteLock getLock(String tag) {
		ReentrantReadWriteLock lock = lockMap.get(tag);
		if (lock == null) {
			lock = new ReentrantReadWriteLock(true);
			lockMap.put(tag, lock);
		}
		return lock;
	}

	public ReentrantReadWriteLock getDefaultLock() {
		ReentrantReadWriteLock lock = lockMap.get(DEFAULT);
		if (lock == null) {
			lock = new ReentrantReadWriteLock(true);
			lockMap.put(DEFAULT, lock);
		}
		return lock;
	}
}
