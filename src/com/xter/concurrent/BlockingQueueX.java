package com.xter.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueueX<T> {

	private Lock lock = new ReentrantLock();
	private Condition conditionPush = lock.newCondition();
	private Condition conditionPop = lock.newCondition();

	private int max;

	private List<T> list = new ArrayList<>();

	public BlockingQueueX(int max) {
		this.max = max;
	}

	public T take() throws InterruptedException {
		lock.lock();
		try {
			while (list.size() == 0) {
				conditionPop.await();
			}
			System.out.println("--signal--");
			//此时可以取数据了，通知push可以继续添加了
			conditionPush.signal();
			return list.remove(0);
		} catch (InterruptedException e) {
			conditionPop.signal();
			throw e;
		} finally {
			lock.unlock();
		}
	}

	public void put(T item) {
		lock.lock();
		try {
			while (list.size() == max) {
				conditionPush.await();
			}
			list.add(item);
			//此时新添加了item，通知pop可以取数据了
			conditionPop.signal();
		} catch (InterruptedException e) {
			conditionPush.signal();
		} finally {
			lock.unlock();
		}
	}

	public boolean isEmpty() {
		return list.size() == 0;
	}
}
