package com.xter.map;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SynchronizedMap {
	public static void main(String[] args) {
		Map<String,String> origin = new HashMap<>();
		HashMap<String,String> map = (HashMap<String, String>) Collections.synchronizedMap(origin);
	}
}
