package com.xter.concurrent;

public class SyncDemo {
	public static void main(String[] args) {

		Item item = new Item();

		for (int i = 0; i < 4; i++) {
			Thread testThread = new Thread(new TestRunnable(0, item), "thread-" + i);
//			Thread testThread = new Thread(new TestRunnable(1, new Item()), "thread-" + i);
			testThread.start();
//			if (i % 2 == 0) {
//				Thread testThread = new Thread(new TestRunnable(6, new Item(), 4), "thread-" + i);
//				testThread.start();
//			} else {
//				Thread testThread = new Thread(new TestRunnable(6, new Item(), 2), "thread-" + i);
//				testThread.start();
//			}

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
