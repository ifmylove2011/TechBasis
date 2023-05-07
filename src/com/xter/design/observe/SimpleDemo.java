package com.xter.design.observe;

import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2021/6/2
 * 描述:
 */
public class SimpleDemo {

	public static void main(String[] args) {
		Watcher watcher = new Watcher();
		watcher.addObserver(new Observer() {
			@Override
			public void update(Observable o, Object arg) {
				System.out.println("add "+arg);
			}
		});

		watcher.add("a");
		watcher.add("a");
		watcher.add("b");
		watcher.add("b");
		watcher.add("a");
		watcher.add("c");
	}

	private static class Watcher extends Observable{

		private Set<String> data;

		public Watcher() {
			data = new HashSet<>();
		}

		public void add(String newValue){
			if(data.add(newValue)){
				setChanged();
				notifyObservers(newValue);
				clearChanged();
			}
		}
	}
}
