package com.xter.design.observe;

import java.util.Random;

public class Main {
	public static void main(String[] args) {
		ConcreteObservable observable = new ConcreteObservable();
		observable.attach(new IObserver() {
			public void update(String content) {
				System.out.println("receive :" + content);
			}
		});
		observable.submit("~I'm working~");
	}
}
