package com.xter.design.observe;

public class ObserverB implements IObserver {
	@Override
	public void update(String content) {
		System.out.println("B receive submit content: " + content);
	}
}
