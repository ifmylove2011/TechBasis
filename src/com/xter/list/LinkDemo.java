package com.xter.list;

import java.util.LinkedList;

/**
 * @author XTER
 * @date 2019/6/14
 */
public class LinkDemo {
	public static void main(String[] args) {
		LinkedList<String> linkedList = new LinkedList<>();
		linkedList.add("a");
		linkedList.add("b");
		linkedList.add("c");
		linkedList.add("d");
		linkedList.add("e");

//		System.out.println(linkedList.peek());
//		System.out.println(linkedList.peek());
//		System.out.println(linkedList.peek());
//		System.out.println(linkedList.peek());
//		System.out.println(linkedList.peek());
//		System.out.println(linkedList.peek()==null);
		System.out.println(linkedList.get(4));
	}

}
