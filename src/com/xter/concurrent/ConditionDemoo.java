package com.xter.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionDemoo {

	ReentrantLock lock = new ReentrantLock();
	Condition condition = lock.newCondition();

	public static void main(String[] args){
		new ConditionDemoo().test();
	}

	public void test(){
		new CustomerThread().start();
		new ProducerThread().start();
	}

	public class CustomerThread extends Thread{
		@Override
		public void run() {
			try{
				lock.lock();
				condition.await();
				System.out.println("~~~~~");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
	}

	public class ProducerThread extends Thread{
		@Override
		public void run() {
			try{
				lock.lock();
				System.out.println("*****");
				TimeUnit.SECONDS.sleep(2);
				condition.signal();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
	}

}
