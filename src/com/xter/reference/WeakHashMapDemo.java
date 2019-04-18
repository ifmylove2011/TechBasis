package com.xter.reference;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class WeakHashMapDemo {

	public static void main(String[] args) {
		Map<Integer,Byte[]> map1 =new HashMap<>();
//		use(map1);
		Map<Integer,Byte[]> map2 =new WeakHashMap<>();
		use(map2);
	}

	public static void use(Map<Integer,Byte[]> map){
		for (int i = 0; i < 10000; i++) {
			map.put(i,new Byte[i]);
		}
	}
}
