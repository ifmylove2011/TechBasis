package com.xter;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;

public class Main {

	public static void main(String[] args) throws InterruptedException {
//		System.out.println("Hi Tech Company");
//
//		System.out.println(isSuccess());
//
//		System.out.println(isPowerOfTwo(1));
//		System.out.println(isPowerOfTwo(2));
//		System.out.println(isPowerOfTwo(8));
//
//		System.out.println(5+2<<2);
//		int value = Integer.parseInt("14");
//		System.out.println(value);
//		Integer a = 1000, b = 1000;
//		System.out.println(a == b);
//		Integer c = 100, d = 100;
//		System.out.println(c == d);
//
//		System.out.println(0x0001 ^ 0x0020);
//		System.out.println(0x0001 & 0x0020);
//		System.out.println(0x0001 | 0x0020);
//		System.out.println(0x0001 | 0x0020);
//
//		byte[] bytes = new byte[2];
//		bytes[0] = 1;
//		bytes[1] = 2;
////		System.out.println(Arrays.toString(reverse(bytes)));
//
//		System.out.println(bytesToHexString(bytes));
//		System.out.println(bytesToHexString(transmute(bytes)));
//
//		byte head = 0;
//
//		ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
//		byteBuffer.get(head);
//
//		System.out.println(byteBuffer.array().length);
		C c = new C();
		c.start();
//		c.join();
		System.out.println("main end");
	}

	static class C extends Thread{
		@Override
		public void run() {
			try {
				System.out.println("child start");
				sleep(2000);
				System.out.println("child end");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static byte[] reverse(byte[] bytes){
		byte[] byteReverse = new byte[bytes.length];
		for (int i = 0; i < bytes.length; i++) {
			byteReverse[i] = bytes[bytes.length-i-1];
		}
		return byteReverse;
	}

	public static byte[] transmute(byte[] bytes){
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) (bytes[i]>>(bytes.length-i-1));
		}
		return bytes;
	}

	public static String bytesToHexString(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for (byte b : bytes) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString().toLowerCase();
	}

	public static boolean isSuccess(){
		try{
			return true;
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			System.out.println("finish");
		}

		System.out.println("other");
		return false;
	}

	private static boolean isPowerOfTwo(int val) {
		return (val & -val) == val;
	}

	public void binary(){
		List<String> list = new ArrayList<>();
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");
		if(list.contains("c")){
			Collections.binarySearch(list,"c");
		}
	}


}
