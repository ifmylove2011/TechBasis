package com.xter.concurrent;

import com.xter.demo.InitDemo;

import java.util.concurrent.TimeUnit;

public class SyncDemo {
	public static void main(String[] args) {

//		Item item = new Item();
//
//		for (int i = 0; i < 4; i++) {
//			Thread testThread = new Thread(new TestRunnable(0, item), "thread-" + i);
////			Thread testThread = new Thread(new TestRunnable(1, new Item()), "thread-" + i);
//			testThread.start();
////			if (i % 2 == 0) {
////				Thread testThread = new Thread(new TestRunnable(6, new Item(), 4), "thread-" + i);
////				testThread.start();
////			} else {
////				Thread testThread = new Thread(new TestRunnable(6, new Item(), 2), "thread-" + i);
////				testThread.start();
////			}
//
//		}
		for (int i = 0; i < 4; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					Rhino rhino = new Rhino();
//					rhino.setup();
					rhino.close();
				}
			}).start();
		}
	}

	static class Rhino {
		private static Object target = new Object();

		public void setup() {
//			synchronized (InitDemo.class) {
//			synchronized (this){
			synchronized (target){
//			synchronized (new Object()){
				try {
					System.out.println(this.hashCode()+","+System.currentTimeMillis());
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		public synchronized void close(){
			try {
				System.out.println(System.currentTimeMillis());
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


	static class TestRunnable implements Runnable {

		Item item;
		int code;
		Object object;

		public TestRunnable(int code, Item item) {
			this.code = code;
			this.item = item;
		}

		public TestRunnable(int code, Item item, Object object) {
			this.code = code;
			this.item = item;
			this.object = object;
		}

		@Override
		public void run() {

			switch (code) {
				case 0:
					item.action0();
					break;
				case 1:
					item.action1();
					break;
				case 2:
					item.action2();
					break;
				case 3:
					item.action3();
					break;
				case 4:
					item.action4();
					break;
				case 5:
					item.action5(object);
					break;
				case 6:
					item.action6(object);
					break;
				default:
					item.action1();
					break;
			}
		}
	}
}
