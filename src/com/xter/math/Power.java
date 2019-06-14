package com.xter.math;

import java.util.Arrays;
import java.util.Locale;

/**
 * @author XTER
 * @date 2019/6/13
 */
public class Power {
	public static void main(String[] args) {
		System.out.println(pack(Integer.toBinaryString(2),32));
		System.out.println(pack(Integer.toBinaryString(-2),32));
		System.out.println(pack(Integer.toBinaryString(3),32));
		System.out.println(pack(Integer.toBinaryString(-3),32));
	}

	private static String pack(String s, int len) {
		if(s.length()==len){
			return s;
		}
		return String.format(Locale.CHINA, "%0" + (len - s.length()) + "d", 0) + s;
	}

	private static byte[] intToBytes(int n, int len) {
		byte[] b = new byte[len];

		for (int i = 0; i < len; i++) {
			b[i] = (byte) (n >> (((len - 1 - i) * 8)) & 0xFF);
		}
		return b;
	}

}
