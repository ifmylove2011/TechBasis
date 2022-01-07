package com.xter.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Objects;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2021/7/30
 * 描述:
 */
public class ReadFromFile {

	public static void main(String[] args) {
		String srcJson = getSrcJson();

	}

	private static String getSrcJson(){
		File file = new File("E:\\WorkSchedule\\CloudLink\\attendeeList.json");
		StringBuilder sb = new StringBuilder();
		try {
			InputStream stream = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(stream);
			char[] buf = new char[1024];
			int remain = 1024;
			while ((remain = isr.read(buf, 0, 1024)) != -1) {
				if (remain < buf.length) {
					char[] tailBuf = new char[remain];
					System.arraycopy(buf, 0, tailBuf, 0, remain);
					sb.append(new String(tailBuf));
				} else {
					sb.append(new String(buf));
				}
				System.out.println(remain);
			}
			System.out.println(sb.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}
