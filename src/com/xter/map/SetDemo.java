package com.xter.map;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class SetDemo {

	public static void main(String[] args) {
		Set<Item> set1 = new HashSet<>();
		Set<Item> set2 = new TreeSet<>();
		Set<Item> set3 = new LinkedHashSet<>();

		putValue(set1);
		putValue(set2);
		putValue(set3);

		System.out.println(set1.toString());
		System.out.println(set2.toString());
		System.out.println(set3.toString());
	}

	public static void putValue(Set<Item> set){
		set.add(new Item(5,"ee"));
		set.add(new Item(1,"aa"));
		set.add(new Item(4,"dd"));
		set.add(new Item(3,"cc"));
		set.add(new Item(2,"bb"));
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
