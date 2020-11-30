package com.xter.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/8/5
 * 描述:检测一段音频的无输入时间段是否超过某个阈值
 */
public class AudioIntervalChecker {

	private int mSilentSeconds;

	private static final int MAX_SECONDS = 5;

	/**
	 * 48k采样率，位深16bit，单声道，录60秒的数据大小
	 */
	private static final int MAX_SIZE = 48 * 1024 * 60 * 2;
	/**
	 * 16kl采样率，位深16bit，单声道，录10秒的数据大小
	 */
	private static final int MIN_SIZE = 16 * 1024 * 10 * 2;

	private ByteBuffer mByteBuffer;

	private static class Holder {
		private static AudioIntervalChecker INSTANCE = new AudioIntervalChecker();
	}

	public static AudioIntervalChecker getInstance() {
		return Holder.INSTANCE;
	}

	private AudioIntervalChecker() {
		mByteBuffer = ByteBuffer.allocate(MAX_SIZE);
	}

	public static void main(String[] args) {
//		File file = new File("E:\\studying\\log\\bbb.pcm");
		File file = new File("E:\\studying\\test\\1234.m4a");
		try {
			FileInputStream fis = new FileInputStream(file);
			byte[] buffer = new byte[24*1024];
			while (fis.read(buffer) != -1) {
//				System.out.println(AudioIntervalChecker.getInstance().calculateVolume(buffer, 16));
				System.out.println(AudioIntervalChecker.getInstance().checkSegment(buffer, 12000, 1, 16, 5));
			}
//			System.out.println(Arrays.toString(buffer));
//			System.out.println(AudioIntervalChecker.getInstance().checkEntire(buffer, 44100, 1, 16, MAX_SECONDS));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 累积数据，检查累积的数据是否可视为录入完成
	 *
	 * @param data       音频数据
	 * @param rate       采样率
	 * @param channelNum 声道数
	 * @param bit        位深
	 * @param maxDelay   最大无输入时间
	 * @return 是否视为录入完成
	 */
	public synchronized boolean checkSegment(byte[] data, int rate, int channelNum, int bit, int maxDelay) {
		mByteBuffer.put(data);
		mByteBuffer.flip();
//		mByteBuffer.mark();
		int size = mByteBuffer.remaining();
		System.out.println("size:"+size);
		byte[] buffer = new byte[size];
		mByteBuffer.get(buffer);
		boolean should = checkEntire(buffer, rate, channelNum, bit, maxDelay);
		if (!should) {
			mByteBuffer.limit(mByteBuffer.capacity());
			mByteBuffer.position(size);
		}
		return should;
	}

	/**
	 * 检查整段音频，若超出一定时间音频无输入，则视为已经录入完成
	 *
	 * @param data       音频数据
	 * @param rate       采样率
	 * @param channelNum 声道数
	 * @param bit        位深
	 * @param maxDelay   最大无输入时间
	 * @return 是否视为录入完成
	 */
	public synchronized boolean checkEntire(byte[] data, int rate, int channelNum, int bit, int maxDelay) {
		ByteBuffer byteBuffer = ByteBuffer.wrap(data);
		int bufferSize = rate * channelNum * bit / 8;
		if (bufferSize * maxDelay > data.length) {
			return false;
		}
		byte[] buffer = new byte[bufferSize];
		byteBuffer.get(buffer);
		if (calculateVolume(buffer, bit) == 0) {
			mSilentSeconds++;
			if (mSilentSeconds >= maxDelay) {
				mSilentSeconds = 0;
				return true;
			}
		} else {
			mSilentSeconds = 0;
		}
		return false;
	}

	private int calculateVolume(byte[] data, int bit) {
		int[] var3 = null;
		int var4 = data.length;
		int var2;
		if (bit == 8) {
			var3 = new int[var4];
			for (var2 = 0; var2 < var4; ++var2) {
				var3[var2] = data[var2];
			}
		} else if (bit == 16) {
			var3 = new int[var4 / 2];
			for (var2 = 0; var2 < var4 / 2; ++var2) {
				byte var5 = data[var2 * 2];
				byte var6 = data[var2 * 2 + 1];
				int var13;
				if (var5 < 0) {
					var13 = var5 + 256;
				} else {
					var13 = var5;
				}
				short var7 = (short) (var13 + 0);
				if (var6 < 0) {
					var13 = var6 + 256;
				} else {
					var13 = var6;
				}
				var3[var2] = (short) (var7 + (var13 << 8));
			}
		}

		int[] var8 = var3;
		if (var3 != null && var3.length != 0) {
			float var10 = 0.0F;
			for (int var11 = 0; var11 < var8.length; ++var11) {
				var10 += (float) (var8[var11] * var8[var11]);
			}
			var10 /= (float) var8.length;
			float var12 = 0.0F;
			for (var4 = 0; var4 < var8.length; ++var4) {
				var12 += (float) var8[var4];
			}
			var12 /= (float) var8.length;
			var4 = (int) (Math.pow(2.0D, (double) (bit - 1)) - 1.0D);
			double var14 = Math.sqrt((double) (var10 - var12 * var12));
			int var9;
			if ((var9 = (int) (10.0D * Math.log10(var14 * 10.0D * Math.sqrt(2.0D) / (double) var4 + 1.0D))) < 0) {
				var9 = 0;
			}
			if (var9 > 10) {
				var9 = 10;
			}
			System.out.println(var9);
			return var9;
		} else {
			System.out.println("0");
			return 0;
		}
	}
}
