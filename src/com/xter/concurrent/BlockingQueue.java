package com.xter.concurrent;

import java.util.ArrayList;
import java.util.List;

public class BlockingQueue<T> {
	private List<T> list = new ArrayList<>();

	public synchronized T take() throws InterruptedException {
		while (list.size() == 0) {
			this.wait();
		}
		return list.remove(0);
	}

	public synchronized void put(T item) {
		list.add(item);
		this.notify();
	}

	public boolean isEmpty() {
		return list.size() == 0;
	}
}
