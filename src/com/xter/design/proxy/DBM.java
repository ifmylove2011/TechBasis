package com.xter.design.proxy;

import java.util.concurrent.TimeUnit;

public class DBM implements IDBM {
	public DBM() {
		try {
			TimeUnit.MILLISECONDS.sleep(100L);
		} catch (InterruptedException var2) {
			var2.printStackTrace();
		}

	}

	@Override
	public String quest() {
		System.out.println("quest");
		return "quest";
	}
}
