package com.xter.concurrent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class TimeDemo {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd HH:mm:ss", Locale.CHINA);
	private static final long DAY_MILLS = 24 * 3600 * 1000;
	private static final long ZONE_MILLS_OFFSET = 8 * 3600 * 1000;
	private volatile long tomorrow;
	private int index;

	private long current;

	private int counter = 0;

	public static void main(String[] args) {
		TimeDemo demo = new TimeDemo();
		demo.timeGo();
//		demo.getTomorrowMills();
//		System.out.println(DATE_FORMAT.format(demo.getTomorrowMills(System.currentTimeMillis())));
	}

	private void timeGo() {
		current = current();
		tomorrow = getTomorrowMills(current);
		System.out.println(DATE_FORMAT.format(current()));
		System.out.println(DATE_FORMAT.format(tomorrow));
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					flush();
					try {
						TimeUnit.SECONDS.sleep(2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					current = current+2*1000;
//					counter ++;
//					if(counter%5==0){
//						current = current+24*3600*1000;
//					}
				}
			}
		}).start();
	}

	private long getTomorrowMills(long time) {
		return time + DAY_MILLS - (time % DAY_MILLS + ZONE_MILLS_OFFSET) % DAY_MILLS;
//		return time- (time% (24 * 3600 * 1000) + 8 * 3600 * 1000) % (24 * 3600 * 1000) + 16 * 3600 * 1000+4*60*1000;//0点后多出的mill值，包括时区影响
	}

	private void flush() {
		System.out.println(getIndex());
	}

	private long current() {
//		return System.currentTimeMillis();
		return System.currentTimeMillis()+ 7 * 3600 * 1000 + 48 * 60 * 1000;
	}

	private int getIndex() {
		if (current() > tomorrow) {
			index = 1;
			tomorrow = getTomorrowMills(current());
			System.out.println("change");
			System.out.println(tomorrow);
			System.out.println(DATE_FORMAT.format(tomorrow));
		} else {
			index++;
//			System.out.println(tomorrow);
//			System.out.println(System.currentTimeMillis());
		}
		return index;
	}
}
