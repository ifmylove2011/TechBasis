package com.xter.secure;

/**
 * @author XTER
 * @desc
 * @date 2019/10/23
 */
public class TEADemo {
	public static void main(String[] args) {
		long[] key = {0x11111111L, 0x22222222L, 0x33333333L, 0x44444444L};

		byte[] bytes = toHexBytes("0123456789abcdef");


		byte[] enResult = TEA.encryptByTea(bytes,key,32);
		System.out.println(bytesToHexString(enResult));
		byte[] deResult = TEA.decryteByTea(enResult,key,32);
		System.out.println(bytesToHexString(deResult));
	}

	public static String bytesToHexString(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for (byte b : bytes) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString().toLowerCase();
	}

	public static byte[] toHexBytes(String hexNum) {
		hexNum = hexNum.replaceAll("\\s", "").trim();
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
}
