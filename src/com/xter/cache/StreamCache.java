package com.xter.cache;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author XTER
 * @date 2019/5/30
 * 数据包格式为 head(1byte),cmd(1byte),len(2byte),....content(...byte),checkSum(1byte),tail(1byte)
 */
public class StreamCache {

	public byte[] bytesPack = new byte[2 * 1024];

	private int offset = 0;

	public boolean readStream(InputStream stream) throws IOException {
		int len = stream.available();
//		byte[] temp = new byte[len];
//		stream.read(temp);
		stream.read(bytesPack, offset, len);
		offset = len;
		checkPack();
		return false;
	}

	public void checkPack() {
		byte start = bytesPack[0];
		byte cmd = bytesPack[1];
		byte[] len = new byte[2];
		System.arraycopy(bytesPack, 0, len, 0, 2);
		int length = bytesToInt(len);
		//判断内容是否符合长度
		if (length > offset - 5) {
			//断包，即不够一个完整包，继续等下一个数据流

		} else {
			//粘包，即有一个或多个完整包和一个残缺包，重置索引，将完整包分离

			//整包，进行后面的处理逻辑
			byte[] whole = new byte[length + 5];
			System.arraycopy(bytesPack, 0, whole, 0, whole.length);
			offset = offset - whole.length;
		}
	}

	public static int bytesToInt(byte[] bytes) {
		int temp = 0;
		for (int i = 0; i < bytes.length; i++) {
			temp += (bytes[i] & 0xFF) << ((bytes.length - 1 - i) * 8);
		}
		return temp;

	}

	private void reset() {
		offset = 0;
	}

	private void clear() {
		for (int i = 0; i < bytesPack.length; i++) {
			bytesPack[i] = 0;
		}
	}
}