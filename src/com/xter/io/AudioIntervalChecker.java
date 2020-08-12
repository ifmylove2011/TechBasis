package com.xter.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/8/5
 * 描述:
 */
public class AudioIntervalChecker {

	private static final int MAX_SECONDS = 5;

	/**
	 * 48k采样率，位深16bit，单声道，录60秒的数据大小
	 */
	private static final int MAX_SIZE = 48 * 1024 * 60 * 2;
	/**
	 * 16kl采样率，位深16bit，单声道，录10秒的数据大小
	 */
	private static final int MIN_SIZE = 16 * 1024 * 10 * 2;

	private static final int THRESOLD = 80;

	/**
	 * 累积数据
	 */
	private ByteBuffer mByteBuffer;

	private int mFrameIndex = 1;
	private ArrayList<Integer> mDbArray;

	private static class Holder {
		private static AudioIntervalChecker INSTANCE = new AudioIntervalChecker();
	}

	public static AudioIntervalChecker getInstance() {
		return Holder.INSTANCE;
	}

	private AudioIntervalChecker() {
		mByteBuffer = ByteBuffer.allocate(MAX_SIZE);
		mDbArray = new ArrayList<>();
	}

	public static void main(String[] args) {
		System.out.println("最大标准db:" + (20 * Math.log10(Short.MAX_VALUE)));
//		File file = new File("E:\\studying\\log\\893.pcm");
		File file = new File("E:\\studying\\log\\533.pcm");
		try {
			FileInputStream fis = new FileInputStream(file);
			byte[] buffer = new byte[32 * 1024 / 2];
			while (fis.read(buffer) != -1) {
				System.out.println(AudioIntervalChecker.getInstance().calculateVolume(buffer, 16));
//				if (AudioIntervalChecker.getInstance().checkFrame(buffer, 2)) {
//					System.out.println("~~~~~~~");
//				}
//				System.out.println(AudioIntervalChecker.getInstance().calculateVolume(buffer, 16));
//				System.out.println(AudioIntervalChecker.getInstance().checkFrame(buffer, 16 * 1024, 1, 16, 2, 2));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		byte a = 0x0f;
//		byte b = 0x23;
//		short result = bytesToShort(a,b);
//		System.out.println(Integer.toHexString(result));
	}


	public synchronized boolean checkFrame(byte[] data, int maxDelay) {
		return checkFrame(data, 16 * 1024, 1, 16, maxDelay, 2);
	}

	/**
	 * 检测当前是否已经满足maxDelay未达到阈值
	 *
	 * @param data       输入pcm音频数据
	 * @param rate       采样频率，这里传1024的倍数，以便与文件大小符合
	 * @param channelNum 通道数，单声道为1，立体声为2
	 * @param bit        位深，pcm一般为8或16
	 * @param maxDelay   最大延时，单位为秒
	 * @param freq    频率，一秒数据分为几段
	 * @return bool
	 */
	public synchronized boolean checkFrame(byte[] data, int rate, int channelNum, int bit, int maxDelay, int freq) {
		boolean result = false;
		int frameSize = rate * channelNum * bit / 8;
		int bufferSize = Math.max(frameSize / freq, data.length);
		mByteBuffer.put(data);
		if (mByteBuffer.position() >= bufferSize * mFrameIndex) {
			int position = mByteBuffer.position();
			mByteBuffer.flip();
			byte[] buffer = new byte[bufferSize];
			mByteBuffer.position(bufferSize * (mFrameIndex - 1));
			mByteBuffer.get(buffer);
			int db = calculateVolume(buffer, bit);
			System.out.println("db:" + db);
			mDbArray.add(db);
			result = checkContinous(maxDelay, freq);
			mByteBuffer.position(position);
			mByteBuffer.limit(mByteBuffer.capacity());
			mFrameIndex++;
		}
		//当前若数据满足，则复位
		if (result) {
			mByteBuffer.position(0);
			mDbArray.clear();
			mFrameIndex = 1;
		}
		return result;
	}

	/**
	 * 是否连续
	 *
	 * @param maxDelay 最大延时，单位为秒
	 * @param freq     频率，每秒几次
	 * @return bool
	 */
	private synchronized boolean checkContinous(int maxDelay, int freq) {
		int thresold = maxDelay * freq;
		if (mDbArray.size() < thresold) {
			return false;
		}
		int counter = 0;
		for (int db : mDbArray) {
			if (db < THRESOLD) {
				counter++;
			} else {
				counter = 0;
			}
		}
		return counter >= thresold;
	}

	/**
	 * 计算输入数据段的db值，按公式应该为20*Math.log10(当前振幅值/最大振幅值)；
	 * 位深为16bit，则代表两个字节表示一个音量采集单位；
	 * 此处用平方和平均值进行计算；
	 *
	 * @param data 输入pcm音频数据
	 * @param bit  位深，8或16
	 * @return 当前分贝值
	 */
	private int calculateVolume(byte[] data, int bit) {
		int[] newBuffer = null;
		int len = data.length;
		int index;

		//排列
		if (bit == 8) {
			newBuffer = new int[len];
			for (index = 0; index < len; ++index) {
				newBuffer[index] = data[index];
			}
		} else if (bit == 16) {
			newBuffer = new int[len / 2];
			for (index = 0; index < len / 2; ++index) {
				byte byteH= data[index * 2];
				byte byteL = data[index * 2 + 1];
				newBuffer[index] = bytesToShort(byteH, byteL);
			}
		}
		//平方和求平均值
		if (newBuffer != null && newBuffer.length != 0) {
			float avg = 0.0F;
			for (int i = 0; i < newBuffer.length; ++i) {
				avg += (float) (newBuffer[i] * newBuffer[i]);
			}
			avg /= (float) newBuffer.length;
			int db = (int) (10.0D * Math.log10(avg + 1));
			return db;
		} else {
			return 0;
		}
	}

	private static short bytesToShort(byte byteH, byte byteL) {
		short temp = 0;
		temp += (byteL & 0xFF);
		temp += (byteH & 0xFF) << 8;
		return temp;
	}
}
