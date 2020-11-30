package com.xter.map;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class TreeMapDemo {
	public static void main(String[] args) {
//		Map<Item,String> map = new TreeMap<>();
//
//		map.put(new Item(5,"ee"),"5");
//		map.put(new Item(1,"aa"),"1");
//		map.put(new Item(4,"dd"),"4");
//		map.put(new Item(3,"cc"),"3");
//		map.put(new Item(2,"bb"),"2");
//
//		System.out.println(map.toString());
//
//		TreeMap<String,String> map1 = new TreeMap<>(Comparator.reverseOrder());
//
//		map1.put("aa","1");
//		map1.put("bb","2");
//		map1.put("cc","3");
//		map1.put("dd","4");
//		map1.put("ee","5");
//
//		System.out.println(map1.toString());

		TreeExMap<String,String> map2 = new TreeExMap<>();

		map2.put("aa","2");
		map2.put("bb","3");
		map2.put("cc","1");
		map2.put("dd","4");
		map2.put("ee","5");

		System.out.println(map2.toString());
		System.out.println(map2.getFirstEntry());
		System.out.println(map2.getLastEntry());


	}

	static class Item implements Comparable<Item> {
		public int id;
		public String content;

		public Item(int id, String content) {
			this.id = id;
			this.content = content;
		}

		@Override
		public int compareTo(Item o) {
			return o.id - this.id;
		}

		@Override
		public String toString() {
			return "Item{" +
					"id=" + id +
					", content='" + content + '\'' +
					'}';
		}
	}
}
