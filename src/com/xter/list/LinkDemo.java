package com.xter.list;

import java.util.LinkedList;
import java.util.regex.Pattern;

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
//		System.out.println(linkedList.get(4));

		String p= "\\{.*?\"chn\":(.*?),\"type\":8450,\"status\":([0,1])}";
		String pp = "\\{.*?\"chn\":(.*?),\"type\":8450,\"status\":([0,1])}";
		String s = "{\"chn\":0,\"type\":8450,\"status\":0}";
		try{
			System.out.println(Pattern.matches(pp,s));
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
