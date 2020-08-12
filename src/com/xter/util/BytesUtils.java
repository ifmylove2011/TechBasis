/**
 * All rights Reserved, Designed By www.beonelot.com
 *
 * @Package: com.beonelot.bcca.common.bcca.DTO.util
 * @Filename: BytesUtils.java
 * @Description: 字节数组工具类
 * @author: 金鑫智慧
 * @date: 2018年8月21日 上午11:27:02
 * @version: V1.0
 * @Copyright: 2018 www.beonelot.com Inc. All rights reserved.
 * 注意：本内容仅限于重庆金鑫智慧科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.xter.util;

import java.util.List;

/**
 * 字节数组工具类
 */
public class BytesUtils {

	/**
	 * int转byte[]
	 *
	 * @param n   数
	 * @param len 字节长度
	 * @return byte[]
	 */
	public static byte[] intToBytes(int n, int len) {
		byte[] b = new byte[len];

		for (int i = 0; i < len; i++) {
			b[i] = (byte) (n >> (((len - 1 - i) * 8)) & 0xFF);
		}
		return b;
	}

	/**
	 * int转byte[1]
	 *
	 * @param num 数
	 * @return byte[]
	 */
	public static byte[] intTo1Bytes(int num) {
		return intToBytes(num, 1);
	}

	/**
	 * int转byte[2]
	 *
	 * @param num 数
	 * @return byte[]
	 */
	public static byte[] intTo2Bytes(int num) {
		return intToBytes(num, 2);
	}

	/**
	 * int转byte[3]
	 *
	 * @param num 数
	 * @return byte[]
	 */
	public static byte[] intTo3Bytes(int num) {
		return intToBytes(num, 3);
	}

	/**
	 * int转byte[4]
	 *
	 * @param num 数
	 * @return byte[]
	 */
	public static byte[] intTo4Bytes(int num) {
		return intToBytes(num, 4);
	}

	/**
	 * byte[]转int
	 *
	 * @param bytes 字节数组
	 * @return int
	 */
	public static int bytesToInt(byte[] bytes) {
		int temp = 0;
		for (int i = 0; i < bytes.length; i++) {
			temp += (bytes[i] & 0xFF) << ((bytes.length - 1 - i) * 8);
		}
		return temp;
	}

	/**
	 * long转byte[]
	 *
	 * @param num 数
	 * @param len 字节长度
	 * @return byte[]
	 */
	public static byte[] longToBytes(long num, int len) {
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

	/**
	 * byte[1]转int
	 *
	 * @param oneBytes 字节
	 * @return int
	 */
	public static int oneBytesToInt(byte[] oneBytes) {
		return bytesToInt(oneBytes);
	}

	/**
	 * byte[2]转int
	 *
	 * @param twoBytes 字节
	 * @return int
	 */
	public static int twoBytesToInt(byte[] twoBytes) {
		return bytesToInt(twoBytes);
	}

	/**
	 * byte[3]转int
	 *
	 * @param threeBytes 字节
	 * @return int
	 */
	public static int threeBytesToInt(byte[] threeBytes) {
		return bytesToInt(threeBytes);
	}

	/**
	 * byte[4]转int
	 *
	 * @param fourBytes 字节
	 * @return int
	 */
	public static int fourBytesToInt(byte[] fourBytes) {
		return bytesToInt(fourBytes);
	}

	/**
	 * byte[]转long
	 *
	 * @param bytes 字节
	 * @return long
	 */
	public static long bytesToLong(byte[] bytes) {
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
	 * int转hex字符串
	 *
	 * @param hexNum 数
	 * @return string
	 */
	public static String hexInt2String(int hexNum) {
		return bytesToHexString(intTo4Bytes(hexNum));
	}

	/**
	 * hex字符串转byte[]
	 *
	 * @param hexNum 字符串
	 * @return byte[]
	 */
	public static byte[] toHexBytes(String hexNum) {
		// 奇数,前补零
		hexNum = (hexNum.length() & 0x1) == 1 ? "0" + hexNum : hexNum;
		byte[] numBytes = new byte[hexNum.length() / 2];
		for (int i = 0; i < numBytes.length; i++) {
			byte high = toHexByte((byte) hexNum.charAt(i * 2));
			byte low = toHexByte((byte) hexNum.charAt(i * 2 + 1));
			// 遮罩低四位
			numBytes[i] = (byte) ((high << 4) | low);
		}
		return numBytes;
	}

	/**
	 * hex字符串转int
	 *
	 * @param hexNum 字符
	 * @param num    长度
	 * @return byte[]
	 */
	public static byte[] toHexBytes(String hexNum, int num) {
		byte[] numBytes = toHexBytes(hexNum);
		byte[] reBytes = new byte[num];

		if (numBytes.length == num) {
			return numBytes;
		} else if (numBytes.length > num) {
			for (int i = 0; i < num; i++) {
				reBytes[i] = numBytes[i];
			}
		} else {
			int cha = num - numBytes.length;
			for (int i = 0; i < cha; i++) {
				reBytes[i] = 0;
			}
			for (int j = 0; j < numBytes.length; j++) {
				reBytes[cha + j] = numBytes[j];
			}
		}

		return reBytes;
	}

	/**
	 * char转byte
	 *
	 * @param charNum byte
	 * @return byte
	 */
	public static byte toHexByte(byte charNum) {
		if ((charNum >= '0') && (charNum <= '9')) {
			return (byte) (charNum - '0');
		} else if ((charNum >= 'A') && (charNum <= 'F')) {
			return (byte) (charNum - 'A' + 10);
		} else if ((charNum >= 'a') && (charNum <= 'f')) {
			return (byte) (charNum - 'a' + 10);
		} else {
			return (byte) (charNum - 48);
		}
	}


	/**
	 * 合并byte[]
	 *
	 * @param bytesList list
	 * @return byte[]
	 */
	public static byte[] mergerBytes(List<byte[]> bytesList) {
		int len = 0;
		for (byte[] bytes : bytesList) {
			len += bytes.length;
		}
		byte[] bytesArr = new byte[len];
		len = 0;
		for (byte[] bytes : bytesList) {
			System.arraycopy(bytes, 0, bytesArr, len, bytes.length);
			len += bytes.length;
		}
		return bytesArr;
	}

	/**
	 * byte[]转字符串
	 *
	 * @param bytes byte[]
	 * @return 字符串
	 */
	public static String bytesToHexString(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for (byte b : bytes) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString().toLowerCase();
	}

	/**
	 * 长度不够，使用byte'0'补齐
	 *
	 * @param content 内容
	 * @param len     总长度
	 * @return byte[]
	 */
	public static byte[] fillZeroBytes(String content, int len) {
		byte[] bytes = new byte[len];
		if (content == null) {
			content = "";
		}
		byte[] contentBytes = content.getBytes();
		System.arraycopy(contentBytes, 0, bytes, 0, contentBytes.length);
		return bytes;
	}

	/**
	 * 由于byte[]中会有byte0存在，会影响转为String，所以在转后过滤掉
	 *
	 * @param bytes 内容
	 * @return string
	 */
	public static String getStringByFillZeroBytes(byte[] bytes) {
		return new String(bytes).replaceAll("[^\\x01-\\xFF]", "");
	}

	/**
	 * 合并两个byte[]
	 *
	 * @param data1 byte[]
	 * @param data2 byte[]
	 * @return data1 与 data2拼接的结果
	 */
	public static byte[] addBytes(byte[] data1, byte[] data2) {
		byte[] data3 = new byte[data1.length + data2.length];
		System.arraycopy(data1, 0, data3, 0, data1.length);
		System.arraycopy(data2, 0, data3, data1.length, data2.length);
		return data3;

	}

}
