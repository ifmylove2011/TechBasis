package com.xter.util;

public class BytesUtil {

	public static void main(String[] args) {
//		int a = 0x11223344;
//		System.out.println(Integer.toHexString(reverseInt(a,4)));

	}


	public static int reverseInt(int num) {
		return byteToInt_HL(intToBytes(num, 4), 0);
	}

	private static int reverseInt(int srcInt, int len) {
		byte[] bytes = new byte[len];

		for (int i = 0; i < len; i++) {
			bytes[i] = (byte) (srcInt >> (i * 8) & 0xFF);
		}

		int result = 0;
		for (int i = 0; i < bytes.length; i++) {
			result += (bytes[i] & 0xFF) << ((bytes.length - 1 - i) * 8);
		}
		return result;
	}

	private static int byteToInt_HL(byte[] b, int offset) {
		int result;
		result = (((b[3 + offset] & 0x00ff) << 24) & 0xff000000)
				| (((b[2 + offset] & 0x00ff) << 16) & 0x00ff0000)
				| (((b[1 + offset] & 0x00ff) << 8) & 0x0000ff00)
				| ((b[0 + offset] & 0x00ff));
		return result;
	}

	private static byte[] intToBytes(int n, int len) {
		byte[] b = new byte[len];

		for (int i = 0; i < len; i++) {
			b[i] = (byte) (n >> (((len - 1 - i) * 8)) & 0xFF);
		}
		return b;
	}
}
