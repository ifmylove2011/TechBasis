package com.xter.util;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class TimeGetter {
	public static final ThreadLocal<SimpleDateFormat> time1 = new ThreadLocal<SimpleDateFormat>(){
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("HH:mm:ss.sss", Locale.CHINA);
		}
	};

	public static String getCurrentTime(){
		return time1.get().format(System.currentTimeMillis());
	}

}
