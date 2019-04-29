package com.xter.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {

	private ReentrantLock lock = new ReentrantLock(false);

	public static void main(String[] args) {
		ReentrantLockDemo demo = new ReentrantLockDemo();

//		Thread one = new Thread(demo.getTask(), "one");
//		Thread two = new Thread(demo.getTask(), "two");

		Thread one = new Thread(demo.getTask1(), "one");
		Thread two = new Thread(demo.getTask1(), "two");


		one.start();
		two.start();

		try {
			TimeUnit.MILLISECONDS.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		two.interrupt();
	}

	private Runnable getTask() {
		return new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						if (lock.tryLock(500, TimeUnit.MILLISECONDS)) {
//						if (lock.tryLock()) {
							try {
								System.out.println("locked on " + Thread.currentThread().getName());

								TimeUnit.SECONDS.sleep(1);
							} finally {
								lock.unlock();
								System.out.println("unlocked on " + Thread.currentThread().getName());
							}

							break;
						} else {
							System.out.println("unable to lock on " + Thread.currentThread().getName());
						}
					} catch (InterruptedException e) {
						System.out.println(Thread.currentThread().getName() + " is interrupted");
					}
				}
			}
		};
	}

	private Runnable getTask1() {
		return new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
//						lock.lock();
						lock.lockInterruptibly();
						try {
							System.out.println("locked on " + Thread.currentThread().getName());

							TimeUnit.SECONDS.sleep(1);
						} finally {
							lock.unlock();
							System.out.println("unlocked on " + Thread.currentThread().getName());
						}

						break;
					} catch (InterruptedException e) {
						System.out.println(Thread.currentThread().getName() + " is interrupted");
					}
				}
			}
		};
	}
}
