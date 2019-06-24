package com.xter.io;

import java.nio.ByteBuffer;

/**
 * @author XTER
 * @date 2019/6/24
 */
public class ByteBufferDemo {
	public static void main(String[] args) {
		byte[] bytes = new byte[2];
		bytes[0] = 0;
		bytes[1] = 1;

		ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
		System.out.println(byteBuffer.get());
	}
}
