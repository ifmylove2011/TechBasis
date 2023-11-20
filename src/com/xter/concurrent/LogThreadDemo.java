package com.xter.concurrent;

import com.xter.util.L;

import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class LogThreadDemo {

	public static void main(String[] args) {
		DomainA a = new DomainA();
		DomainB b = new DomainB();
		ScheduledThreadPoolExecutor ses = new ScheduledThreadPoolExecutor(2);
		RunnableScheduledFuture<?> futureA = (RunnableScheduledFuture<?>) ses.scheduleAtFixedRate(a,1,7,TimeUnit.SECONDS);
		RunnableScheduledFuture<?> futureB = (RunnableScheduledFuture<?>) ses.scheduleAtFixedRate(b,1,5,TimeUnit.SECONDS);
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ses.remove(futureB);
		L.d("remove b");
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ses.remove(futureA);
		L.d("remove A");
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		futureB = (RunnableScheduledFuture<?>) ses.scheduleAtFixedRate(b,1,8,TimeUnit.SECONDS);
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		futureA = (RunnableScheduledFuture<?>) ses.scheduleAtFixedRate(a,1,5,TimeUnit.SECONDS);
//		RunnableScheduledFuture<?> futureC = (RunnableScheduledFuture<?>) ses.scheduleAtFixedRate(b,1,5,TimeUnit.SECONDS);
	}

	static class DomainB implements Runnable{

		@Override
		public void run() {
			L.d("DomainB running,"+Thread.currentThread().getName());
			L.logThreadSequence();
		}
	}

	static class DomainA implements Runnable{

		@Override
		public void run() {
			L.d("DomainA running,"+Thread.currentThread().getName());
			L.logThreadSequence();
		}
	}
}
