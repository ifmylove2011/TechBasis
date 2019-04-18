package com.xter.reference;

import java.lang.ref.Reference;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class CheckReferenceTask<T> implements Runnable {

	private Queue<Reference<T>> referenceQueue;

	public CheckReferenceTask() {
		referenceQueue = new ArrayDeque<>(10);
	}

	@Override
	public void run() {

	}
}
