package com.xter.algorithm.exercise;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2021/4/25
 * 描述:实现一个方法，当一个字符串时，要求输出这个字符串的所有排列。例如，输入字符串abc，要求输出由字符a.b.c所能排列出来的所有字符串:abc.acb.bac.bca.cab.cba
 */
public class StringPermutation {

	public static void main(String[] args) {
		String src = "abc";

		permutate(src.toCharArray(),0);
	}

	private static void permutate(char[] src, int offset) {
		if (src == null || offset < 0) {
			return;
		}
		if (offset == src.length - 1) {
			System.out.println(src);
		} else {
			for (int i = offset; i < src.length; i++) {
				swap(src, offset, i);
				permutate(src, offset + 1);
				swap(src, i, offset);
			}
		}
	}

	private static void swap(char[] src, int i, int j) {
		char temp = src[i];
		src[i] = src[j];
		src[j] = temp;
	}
}
