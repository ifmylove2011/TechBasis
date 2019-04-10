package com.xter.map;

import java.util.LinkedHashMap;
import java.util.Map;

public class LinkedHashMapDemo {

	public static void main(String[] args) {
		LinkedHashMap<Integer, String> map1 = new LinkedHashMap<Integer, String>(10, 0.75f, true);

		LinkedHashMap<Integer, String> map2 = new LinkedHashMap<Integer, String>(10, 0.75f, false);

		for (int i = 0; i < 10; i++) {
			map1.put(i, i+"");
			map2.put(i, i+"");
		}

		map1.get(1);
		map2.get(1);

		showMap(map1);
		System.out.println("-----------------");
		showMap(map2);

		System.out.println("".getClass().getTypeName());
		System.out.println(map2.getClass().getTypeName());
	}

	public static void showMap(Map<Integer, String> map) {
		for (Map.Entry<Integer, String> entry : map.entrySet()) {
			System.out.println(entry.getValue() + "," + entry.getValue());
		}
	}
}
