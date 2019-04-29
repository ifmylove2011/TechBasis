package com.xter.concurrent;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class WriteReadLockDemo {
	public static void main(String[] args) {
		ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
		ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
		ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
	}
}
