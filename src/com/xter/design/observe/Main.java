package com.xter.design.observe;


public class Main {
	public static void main(String[] args) {
		ConcreteObservable observable = new ConcreteObservable();
		observable.attach(new ObserverA());
		observable.attach(new ObserverB());
		observable.submit("~I'm working~");

		observable.attach(new IObserver() {
			@Override
			public void update(String content) {
				System.out.println("I'm working");
			}
		});
	}
}
