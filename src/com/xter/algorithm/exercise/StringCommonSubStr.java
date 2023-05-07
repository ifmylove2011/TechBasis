package com.xter.algorithm.exercise;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2021/5/7
 * 描述:如何求两个字符串的最长公共子串；
 * 找出两个字符串的最长公共子串，如字符串"abccade"与字符串"dgcadde"的最长公共子串为"cad"。
 */
public class StringCommonSubStr {

	public static void main(String[] args) {
		String str1 = "abccade";
		String str2 = "dgcadde";
		System.out.println(findMaxSubStr2(str1, str2));
	}

	public static String findMaxSubStr(String str1, String str2) {
		int len1 = str1.length();
		int len2 = str2.length();
		StringBuilder sb = new StringBuilder();
		int i, j, len = 0, maxIndex = 0;
		int[][] container = new int[len1 + 1][len2 + 1];
		for (i = 1; i < len1 + 1; i++) {
			for (j = 1; j < len2 + 1; j++) {
				if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
					container[i][j] = container[i - 1][j - 1] + 1;
					if (container[i][j] > len) {
						len = container[i][j];
						maxIndex = i;
					}
				} else {
					container[i][j] = 0;
				}
			}
		}
		for (i = maxIndex - len; i < maxIndex; i++) {
			sb.append(str1.charAt(i));
		}
		return sb.toString();
	}

	public static String findMaxSubStr2(String str1, String str2) {
		int len1 = str1.length();
		int len2 = str2.length();
		int m, n, len = 0, maxIndex = 0;
		//当前字符串在大范围内的索引起始值
		int s1 = 0, s2 = 0;
		int length = len1 + len2;
		StringBuilder sb = new StringBuilder();
		for ( m = 0; m < length; m++) {
			int temp = 0;
			if (m < len1) {
				s1 = len1 - m;
			} else {
				s2 = m - len1;
			}
			for ( n = 0; (s1 + n < len1) && (s2 + n) < len2; n++) {
				if(str1.charAt(s1+n) == str2.charAt(s2+n)){
					temp++;
				}else{
					if(temp>len){
						len = temp;
						maxIndex = s1+n;
					}else{
						temp = 0;
					}
				}
			}
			if(temp>len){
				len = temp;
				maxIndex = s1+n;
			}else{
				temp = 0;
			}
		}
		for(m = maxIndex-len;m<maxIndex;m++){
			sb.append(str1.charAt(m));
		}
		return sb.toString();
	}
}
