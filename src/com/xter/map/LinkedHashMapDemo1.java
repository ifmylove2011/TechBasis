package com.xter.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LinkedHashMapDemo1 {

	private static final int NUM = 5;

	public static void main(String[] args) {
		LinkedHashMap<Integer,String> map = new LinkedHashMap<>(1,0.75f,true);

		for (int i = 0; i < NUM; i++) {
			map.put(i,i+"");
		}
		System.out.println(map.toString());

		map.put(3,"3");
		System.out.println(map.toString());
		System.out.println(map.keySet().toString());

		map.get(2);
		System.out.println(map.toString());
		System.out.println(map.keySet().toString());
	}

	public static void showMap(Map<Integer, String> map) {
		for (Map.Entry<Integer, String> entry : map.entrySet()) {
			System.out.println(entry.getValue() + "," + entry.getValue());
		}
	}

}
