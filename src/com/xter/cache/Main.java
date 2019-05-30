package com.xter.cache;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Main {
	public static void main(String[] args) {
		LruCache<Integer,String> lruCache = new LruCache<Integer,String>(8){
			@Override
			protected int sizeOf(Integer key, String value) {
				return value.length();
			}
		};

		for (int i = 0; i < 4; i++) {
			lruCache.put(i,i+"-");
			System.out.println(lruCache.snapshot().toString());

		}

		lruCache.get(1);
		lruCache.get(1);
		lruCache.get(3);

		System.out.println(lruCache.snapshot().toString());

		lruCache.put(4,"4+");

		System.out.println(lruCache.snapshot().toString());

		lruCache.put(1,"1-");

		System.out.println(lruCache.snapshot().toString());

	}
}
