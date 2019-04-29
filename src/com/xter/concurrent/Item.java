package com.xter.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Item {

	private ReentrantLock lock = new ReentrantLock(true);

	public void action0() {
		try {
			lock.lock();
			System.out.println("action0 start " + Thread.currentThread().getName());
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
		System.out.println("action0 end " + Thread.currentThread().getName());
	}

	public synchronized void action1() {
		System.out.println("action1 start " + Thread.currentThread().getName());
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("action1 end " + Thread.currentThread().getName());
	}

	public static synchronized void action2() {
		System.out.println("action2 start " + Thread.currentThread().getName());
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("action2 end " + Thread.currentThread().getName());
	}

	public void action3() {
		synchronized (this) {
			System.out.println("action3 start " + Thread.currentThread().getName());
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("action3 end " + Thread.currentThread().getName());
		}
	}

	public void action4() {
		synchronized (this.getClass()) {
			System.out.println("action4 start " + Thread.currentThread().getName());
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("action4 end " + Thread.currentThread().getName());
		}
	}

	public void action5(Object object) {
		synchronized (object) {
			System.out.println("obj=" + object + " action5 start " + Thread.currentThread().getName());
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("obj=" + object + " action5 end " + Thread.currentThread().getName());
		}
	}

	public void action6(Object object) {
		synchronized (object.getClass()) {
			System.out.println("obj=" + object + " action6 start " + Thread.currentThread().getName());
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("obj=" + object + " action6 end " + Thread.currentThread().getName());
		}
	}
}
