package com.xter.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LinkedHashMapDemo {

	private static final int NUM = 100000;

	public static void main(String[] args) {

		HashMap<Integer, String> hashMap1 = new HashMap<>(NUM);

		LinkedHashMap<Integer, String> linkedMap1 = new LinkedHashMap<Integer, String>(NUM, 0.75f, true);

		LinkedHashMap<Integer, String> linkedMap2 = new LinkedHashMap<Integer, String>(NUM, 0.75f, false);

		List<String> list1 = new ArrayList<>(NUM);

		for (int i = 0; i < NUM; i++) {
			hashMap1.put(i, i + "");
			linkedMap1.put(i, i + "");
			linkedMap2.put(i, i + "");
			list1.add(i + "");
		}


		linkedMap1.get(1);
		linkedMap2.get(1);

//		showMap(map1);
//		System.out.println("-----------------");
//		showMap(map2);

		System.out.println("".getClass().getTypeName());
		System.out.println(linkedMap2.getClass().getTypeName());

		System.out.println("--------hashMap---------");
		findValue("59990", hashMap1);
//		findValue("1", hashMap1);

		System.out.println("--------linkedMap true---------");
		findValue("59990", linkedMap1);
//		findValue("1", linkedMap1);

		System.out.println("--------linkedMap false---------");
		findValue("59990", linkedMap2);
//		findValue("1", linkedMap2);

		System.out.println("--------list---------");
		findValue("59990", list1);
		findValue("1", list1);

		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("-----------------");
		findValue("59990", hashMap1);
//		findValue("1", hashMap1);

		System.out.println("-----------------");
		findValue("59990", linkedMap1);
//		findValue("1", linkedMap1);

		System.out.println("-----------------");
		findValue("59990", linkedMap2);
//		findValue("1", linkedMap2);
	}

	public static void showMap(Map<Integer, String> map) {
		for (Map.Entry<Integer, String> entry : map.entrySet()) {
			System.out.println(entry.getValue() + "," + entry.getValue());
		}
	}

	public static void findValue(String value, Map<Integer, String> map) {
		long start = System.currentTimeMillis();
		int counter = 0;
		for (Map.Entry<Integer, String> entry : map.entrySet()) {
			counter++;
			if (value.equals(entry.getValue())) {
				System.out.println("找到匹配值");
				break;
			}
		}

//		Iterator<Map.Entry<Integer, String>> entryIterator = map.entrySet().iterator();
//		while (entryIterator.hasNext()) {
//			Map.Entry<Integer, String> entry = entryIterator.next();
//			counter++;
//			if (entry.getValue().equals(value)) {
//				System.out.println("找到匹配值" + map.get(entry.getKey()));
//				break;
//			}
//		}

//		ListIterator<Map.Entry<Integer,String>> entryIterator= new ArrayList<>(map.entrySet()).listIterator(map.size());
//		while (entryIterator.hasPrevious()){
//			Map.Entry<Integer,String> entry = entryIterator.previous();
//			counter ++;
//			if(entry.getValue().equals(value)){
//				System.out.println("找到匹配值" + map.get(entry.getKey()));
//				break;
//			}
//		}

		System.out.println(counter);
		System.out.println((System.currentTimeMillis() - start) + "ms");
	}

	public static void findValue(String value, List<String> list) {
		long start = System.currentTimeMillis();
		int counter = 0;
		for (String s : list) {
			counter++;
			if (s.equals(value)) {
				System.out.println("找到匹配值");
				break;
			}
		}
		System.out.println(counter);
		System.out.println((System.currentTimeMillis() - start) + "ms");


	}
}
