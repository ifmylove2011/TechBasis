package com.xter.concurrent;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author XTER
 * @date 2019/5/23
 */
public class ConMapDemo {
	public static void main(String[] args) {
		ConcurrentHashMap<String,String> map = new ConcurrentHashMap<>();
		map.put("1","00001");

		System.out.println(map.get("2")==null);
	}
}
