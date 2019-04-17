package com.xter.list;

import com.xter.util.DataGen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CompareLinkedAndArray {

	public static void main(String[] args) {
		LinkedList<String> linkedList = (LinkedList<String>) DataGen.getInstance().getList(100000,10,1);
		ArrayList<String> arrayList = new ArrayList<>(linkedList);

		foreach(linkedList);
		foreach(arrayList);

		iter(linkedList);
		iter(arrayList);

		forsize(linkedList);//因为链表访问索引需要遍历，耗时严重
		forsize(arrayList);
	}

	public static void foreach(List<String> list){
		long start = System.currentTimeMillis();
		String temp;
		for(String s:list){
			temp = s;
		}
		System.out.println();
		System.out.println("foreach   delay = "+(System.currentTimeMillis()-start));
	}

	public static void iter(List<String> list){
		long start = System.currentTimeMillis();
		String temp;
		for(Iterator<String> iterator = list.iterator();iterator.hasNext();){
			temp = iterator.next();
		}
		System.out.println();
		System.out.println("iter   delay = "+(System.currentTimeMillis()-start));
	}

	public static void forsize(List<String> list){
		long start = System.currentTimeMillis();
		int size = list.size();
		String temp;
		for (int i = 0; i < size; i++) {
			temp = list.get(i);
		}
		System.out.println();
		System.out.println("forsize    delay = "+(System.currentTimeMillis()-start));
	}
}
