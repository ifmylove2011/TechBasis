package com.xter.io;

import java.io.File;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/8/5
 * 描述:
 */
public class FileIndex {
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);

	public static void main(String[] args) {
//		System.out.println(checkFileStartIndex());
		checkClear();
	}
	/**
	 * 当日日志文件索引最大值，接续
	 * @return 索引
	 */
	private static int checkFileStartIndex() {
		File logDir = new File("E:\\studying\\log\\");
		final String filePrefix = DATE_FORMAT.format(System.currentTimeMillis());
		String[] fileNames = logDir.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if (name.startsWith(filePrefix))
					return true;
				return false;
			}
		});
		return checkFileMaxIndex(fileNames);
	}

	private static int checkFileMaxIndex(String[] names) {
		int maxIndex = 1;
		if (names != null && names.length > 0) {
			int len = names.length;
			String maxFileIndex = names[0];
			for (int i = 1; i < len; i++) {
				if (names[i].compareTo(maxFileIndex) > 0) {
					maxFileIndex = names[i];
				}
			}
			maxIndex = Integer.parseInt(maxFileIndex.substring(maxFileIndex.length() - 6, maxFileIndex.length() - 4));
		}
		return maxIndex;
	}

	/**
	 * 清理日志文件；
	 * 一，当天清理；
	 * 二，定期清理；
	 * 三，低剩余清理；
	 */
	private static void checkClear() {
		File logDir = new File("E:\\studying\\log\\");

		final String filePrefix = DATE_FORMAT.format(System.currentTimeMillis()-7*24*3600*1000);
		File[] all = logDir.listFiles();
		for(File f:all){
			System.out.println("file:"+f.getName()+",size:"+f.length());
		}
		File[] files = logDir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				System.out.println("file:"+dir.getName()+",size:"+dir.length());
				if (name.compareTo(filePrefix)<0)
					return true;
				return false;
			}
		});
//		String[] fileNames = logDir.list(new FilenameFilter() {
//			@Override
//			public boolean accept(File dir, String name) {
//				if (name.compareTo(filePrefix)<0)
//					return true;
//				return false;
//			}
//		});
		deleteFiles(files);
		System.out.println(Arrays.toString(files));
	}

	public static void deleteFiles(File... files) {
		for (File f : files) {
			if (f.exists()) {
				f.delete();
			}
		}
	}


}
