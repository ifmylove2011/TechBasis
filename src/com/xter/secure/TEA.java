package com.xter.secure;

/**
 * @author XTER
 * @desc java中的tea加密算法
 * @date 2019/10/21
 */
public class TEA {

	private static final long DELTA = 0x9e3779b9;

	/* ----------------------  加密方法  ---------------------- */

	/**
	 * 加密，每次支持64bit，即两个无符号整数，改用long代替int
	 *
	 * @param content 原文
	 * @param key     密钥
	 * @param times   加密轮数
	 * @return 加密后
	 */
	private static byte[] encrypt(byte[] content, long[] key, int times) {
		long[] pendingData = byte2longArray(content);
		long y = pendingData[0], z = pendingData[1];
		long sum = 0;
		long key1 = key[0], key2 = key[1], key3 = key[2], key4 = key[3];
		for (int i = 0; i < times; i++) {
			sum += DELTA;
			sum = long2longInt(sum);
			y += long2longInt((z << 4) + key1) ^ long2longInt(z + sum) ^ long2longInt((z >> 5) + key2);
			y = long2longInt(y);
			z += long2longInt((y << 4) + key3) ^ long2longInt(y + sum) ^ long2longInt((y >> 5) + key4);
			z = long2longInt(z);
		}
		pendingData[0] = y;
		pendingData[1] = z;

		return longArray2Byte(pendingData);
	}

	/**
	 * 解密，每次支持64bit，即两个无符号整数，改用long代替int
	 *
	 * @param encryptContent 加密内容
	 * @param key            密钥
	 * @param times          加密轮数
	 * @return 加密前
	 */
	private static byte[] decrypt(byte[] encryptContent, long[] key, int times) {
		long[] pendingData = byte2longArray(encryptContent);
		long y = pendingData[0], z = pendingData[1];
		long sum = DELTA * times;
		long key1 = key[0], key2 = key[1], key3 = key[2], key4 = key[3];

		for (int i = 0; i < times; i++) {
			z -= long2longInt((y << 4) + key3) ^ long2longInt(y + sum) ^ long2longInt((y >> 5) + key4);
			z = long2longInt(z);
			y -= long2longInt((z << 4) + key1) ^ long2longInt(z + sum) ^ long2longInt((z >> 5) + key2);
			y = long2longInt(y);
			sum -= DELTA;
			sum = long2longInt(sum);
		}
		pendingData[0] = y;
		pendingData[1] = z;

		return longArray2Byte(pendingData);
	}

	/* ----------------------  工具方法  ---------------------- */

	/**
	 * 去掉long整形的高32位
	 *
	 * @param num 一个可能超出32位有效值的数值
	 * @return 32位有效位的数值
	 */
	private static long long2longInt(long num) {
		return Long.parseLong(Integer.toHexString((int) num), 16);
	}

	/**
	 * 分割byte数组，传n组长度数值，就分为n组；
	 * 如传入[4,8]，则原数组分为一个4byte的数组与8byte的数组
	 *
	 * @param srcBytes 原数据
	 * @param lens     长度数组
	 * @return byte数组
	 */
	private static byte[][] splitBytes(byte[] srcBytes, int... lens) {
		byte[][] bytess = new byte[lens.length][];
		int index = 0;
		for (int i = 0; i < lens.length; i++) {
			int len = lens[i];
			bytess[i] = new byte[len];
			System.arraycopy(srcBytes, index, bytess[i], 0, len);
			index += lens[i];
		}
		return bytess;
	}

	/**
	 * long[]转byte[]，两个长整为64位bit即8byte数组
	 *
	 * @param content 数据
	 * @return int[]
	 */
	private static byte[] longArray2Byte(long[] content) {
		return mergerBytes(longToBytes(content[0], 4), longToBytes(content[1], 4));
	}

	/**
	 * byte[]转long[]，64bit转为两个long型
	 *
	 * @param content 原文
	 * @return int[]
	 */
	private static long[] byte2longArray(byte[] content) {
		long[] result = new long[2];
		byte[][] longBytes = splitBytes(content, 4, 4);
		result[0] = bytesToLong(longBytes[0]);
		result[1] = bytesToLong(longBytes[1]);
		result[0] = long2longInt(result[0]);
		result[1] = long2longInt(result[1]);
		return result;
	}

	/**
	 * 将两个数组拼装成一个
	 *
	 * @param bytesArray byte数据
	 * @return byte[]
	 */
	private static byte[] mergerBytes(byte[]... bytesArray) {
		int len = 0;
		for (byte[] bytes : bytesArray) {
			len += bytes.length;
		}
		byte[] bytesArr = new byte[len];
		len = 0;
		for (byte[] bytes : bytesArray) {
			System.arraycopy(bytes, 0, bytesArr, len, bytes.length);
			len += bytes.length;
		}
		return bytesArr;
	}

	/**
	 * byte[]转长整
	 *
	 * @param bytes 数据
	 * @return 长整
	 */
	private static long bytesToLong(byte[] bytes) {
		long longNum = 0;
		int len = bytes.length;
		for (int i = 0, temp; i < len; i++) {
			temp = (len - 1 - i) * 8;
			if (temp == 0) {
				longNum += (bytes[i] & 0x0ff);
			} else {
				longNum += (bytes[i] & 0x0ff) << temp;
			}
		}
		return longNum;
	}

	/**
	 * 长整转byte[]
	 *
	 * @param num 长整
	 * @param len byte长度
	 * @return byte数组
	 */
	private static byte[] longToBytes(long num, int len) {
		byte[] longLenBytes = new byte[len];
		for (int i = 0, temp; i < len; i++) {
			temp = (len - 1 - i) * 8;
			if (temp == 0) {
				longLenBytes[i] += (num & 0x0ff);
			} else {
				longLenBytes[i] += (num >>> temp) & 0x0ff;
			}
		}
		return longLenBytes;
	}

	/* ----------------------  公开方法  ---------------------- */

	public static byte[] encryptByTea(byte[] content, long[] key, int times) {
		int len = content.length;
		if (len % 8 != 0) {
			System.out.println("加密数据不满足条件");
			return content;
		}
		byte[] result = new byte[len];

		for (int i = 0; i < len; i += 8) {
			byte[] temp = new byte[8];
			System.arraycopy(content, i, temp, 0, 8);
			byte[] tempEncrypted = encrypt(temp, key, times);
			System.arraycopy(tempEncrypted, 0, result, i, 8);
		}

		return result;
	}

	public static byte[] decryteByTea(byte[] content, long[] key, int times) {
		int len = content.length;
		if (len % 8 != 0) {
			System.out.println("解密数据不满足条件");
			return content;
		}
		byte[] result = new byte[len];

		for (int i = 0; i < len; i += 8) {
			byte[] temp = new byte[8];
			System.arraycopy(content, i, temp, 0, 8);
			byte[] tempdecrypted = decrypt(temp, key, times);
			System.arraycopy(tempdecrypted, 0, result, i, 8);
		}

		return result;
	}
}
