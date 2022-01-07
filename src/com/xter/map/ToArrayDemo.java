package com.xter.map;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2021/6/2
 * 描述:
 */
public class ToArrayDemo {

	public static void main(String[] args) {
		Map<String,String> map = new HashMap<>();
		map.put("1","a");
		map.put("2","b");
		map.put("3","c");

		Object[] keys =map.keySet().toArray();
		System.out.println(Arrays.toString(keys));

		String[] keyss = new String[map.size()];
		 map.keySet().toArray(keyss);
		System.out.println(Arrays.toString(keyss));

	}
}
