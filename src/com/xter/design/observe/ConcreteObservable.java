package com.xter.design.observe;

import java.util.Iterator;
import java.util.Vector;

public class ConcreteObservable implements IObservable {
	Vector<IObserver> vector = new Vector();

	@Override
	public void attach(IObserver observer) {
		this.vector.addElement(observer);
	}

	@Override
	public void detach(IObserver observer) {
		this.vector.removeElement(observer);
	}

	@Override
	public void submit(String content) {
		Iterator var2 = this.vector.iterator();

		while(var2.hasNext()) {
			IObserver observer = (IObserver)var2.next();
			observer.update(content);
		}
	}
}
