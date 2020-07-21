package com.xter.util;

import java.nio.ByteOrder;

import static io.netty.util.internal.shaded.org.jctools.util.UnsafeAccess.UNSAFE;

public class BytesUtil {

	public static void main(String[] args) {
//		int a = 0x11223344;
//		System.out.println(Integer.toHexString(reverseInt(a,4)));

		long a = UNSAFE.allocateMemory(8);
		ByteOrder byteOrder  = null;
		try {
			UNSAFE.putLong(a, 0x0102030405060708L);
			//存放此long类型数据，实际存放占8个字节，01,02,03，04,05,06,07,08
			byte b = UNSAFE.getByte(a);
			//通过getByte方法获取刚才存放的long，取第一个字节
			//如果是大端，long类型顺序存放—》01,02,03,04,05,06,07,08  ，取第一位便是0x01
			//如果是小端，long类型顺序存放—》08,07,06,05,04,03,02,01  ，取第一位便是0x08
			switch (b) {
				case 0x01:
					 byteOrder = ByteOrder.BIG_ENDIAN;
					break;
				case 0x08:
					byteOrder = ByteOrder.LITTLE_ENDIAN;
					break;
				default:
					assert false;
					byteOrder = null;
			}
		}finally {
			System.out.println(byteOrder);
		}
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
