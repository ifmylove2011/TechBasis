package com.xter.map;

import java.util.LinkedHashMap;

public class LinkedHashMapX<K,V> extends LinkedHashMap<K,V> {

	final boolean accessOrder;

	public LinkedHashMapX(int initialCapacity,
	                     float loadFactor,
	                     boolean accessOrder) {
		super(initialCapacity, loadFactor);
		this.accessOrder = accessOrder;
	}

}
