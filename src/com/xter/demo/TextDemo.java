package com.xter.demo;

import com.xter.util.TextUtil;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/6/15
 * 描述:
 */
public class TextDemo {
	public static void main(String[] args) {
		String hex = "00 00 00 01 00 01";
		String regex = "[0-9a-zA-Z\\s]{6}[0-9a-zA-Z]{2}\\s([0-9a-zA-Z]{2})\\s[0-9a-zA-Z]{2}\\s([0-9a-zA-Z]{2})";

		System.out.println(TextUtil.replaceMatcherContent(hex, regex, new int[]{1, 2}, new String[]{"1234", "2345"}));
	}
}
