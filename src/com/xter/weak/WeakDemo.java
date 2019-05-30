package com.xter.weak;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author XTER
 * @date 2019/5/20
 */
public class WeakDemo {
	public static void main(String[] args) {
		Map<String, Map<String,String>> map =new HashMap<>();
		Map<String,String> itemMap = new WeakHashMap<>();

		System.out.println(itemMap.put("a","1111"));
		System.out.println(itemMap.put("a","1111"));
		System.out.println(itemMap.put("a","222"));
		System.out.println(itemMap.put("a","22332"));

//		System.out.println(map.put("1",itemMap));
//		System.out.println(map.put("1",itemMap));
//		System.out.println(itemMap.toString());
//		System.gc();
//		System.out.println(itemMap.toString());
	}
}
