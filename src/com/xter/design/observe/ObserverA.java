package com.xter.design.observe;

public class ObserverA implements IObserver {
	@Override
	public void update(String content) {
		System.out.println("A receive submit content: " + content);
	}
}
