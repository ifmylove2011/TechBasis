package com.xter.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author XTER
 * @date 2019/7/2
 */
public class FileOperator {

	public static void copyfile(String url1, String url2) throws IOException {
		File srcF = new File(url1);
		if (!srcF.exists()) {
			return;
		}
		File dstF = new File(url2);

		if (srcF.isFile()) {
			//src为文件时，dst可为文件，也可为目录，为目录时追加文件名即可
			String dstUrl = url2;
			if (dstF.exists()) {
				if (dstF.isDirectory()) {
					dstUrl = dstUrl + srcF.getName();
				}
			} else {
				if (url2.endsWith(File.separator)) {
					dstF.mkdirs();
					dstUrl = dstUrl + srcF.getName();
				} else {
					dstF.getParentFile().mkdirs();
				}
			}

			FileInputStream fis = new FileInputStream(srcF);
			BufferedInputStream bis = new BufferedInputStream(fis);

			FileOutputStream fos = new FileOutputStream(dstUrl);
			BufferedOutputStream bos = new BufferedOutputStream(fos);

			int len = 0;
			while ((len = bis.read()) != -1) {
				bos.write(len);
			}
			bis.close();
			bos.close();
			return;
		}
		//src为目录时，dst不可为文件，无论时否有目录符，均需自动转换为目录
		dstF.mkdirs();
		File[] files = srcF.listFiles();
		if (files != null) {
			for (File f : files) {
				File dstFile = new File(dstF.getAbsolutePath(), f.getName());
				if (f.isDirectory()) {
					if (!dstFile.exists()) {
						dstFile.mkdir();
					}
				}
				copyfile(f.getAbsolutePath(), dstFile.getAbsolutePath());
			}
		}
	}

	public static void copyFile(File sourceFile, File targetFile)
			throws IOException {
		// 新建文件输入流并对它进行缓冲
		FileInputStream input = new FileInputStream(sourceFile);
		BufferedInputStream inBuff = new BufferedInputStream(input);

		// 新建文件输出流并对它进行缓冲
		FileOutputStream output = new FileOutputStream(targetFile);
		BufferedOutputStream outBuff = new BufferedOutputStream(output);

		// 缓冲数组
		byte[] b = new byte[1024];
		int len;
		while ((len = inBuff.read(b)) != -1) {
			outBuff.write(b, 0, len);
		}
		// 刷新此缓冲的输出流
		outBuff.flush();

		//关闭流
		inBuff.close();
		outBuff.close();
		output.close();
		input.close();
	}
}
