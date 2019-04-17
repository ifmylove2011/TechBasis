package com.xter.design.observe;

public interface IObservable {
	void attach(IObserver observer);

	void detach(IObserver observer);

	void submit(String content);
}
