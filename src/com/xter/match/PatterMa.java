package com.xter.match;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class PatterMa {


	public static void main(String[] args) {

		Map<String, String> map = getContent();
		Pattern p = Pattern.compile("\\D+\\d+\\D+");
		long start = System.currentTimeMillis();
		for (Map.Entry<String, String> entry : map.entrySet()) {
//			System.out.print(entry.getKey());
			if(Pattern.matches("\\D+\\d+\\D+",entry.getValue())){
				System.out.println(entry.getKey());
//				break;
			}
		}
		System.out.println();
		System.out.println(System.currentTimeMillis() - start);
	}


	public static Map<String, String> getContent() {
		Map<String, String> contentMap = new HashMap<>(1000);
		for (int i = 0; i < 100000; i++) {
			contentMap.put("c" + i, "content" + i);
		}
		contentMap.put("cc", "abcd123abcd");
		return contentMap;
	}
}
