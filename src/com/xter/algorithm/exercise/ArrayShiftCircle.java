package com.xter.algorithm.exercise;

import java.util.Arrays;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/10/16
 * 描述:一个含有N个元素的数组循环右移K（K为正整数）位，要求时间复杂度为O(N)，且只允许使用两个附加变量；
 */
public class ArrayShiftCircle {

	public static void main(String[] args) {
		char[] src = generateArray();
//		shiftCircle(src,9);
		shiftCircleX(src,9);
		System.out.println(Arrays.toString(src));
	}

	private static void reverse(char[] src,int start,int end){
		while (start<end){
			char temp = src[start];
			src[start] = src[end];
			src[end] = temp;
			start++;
			end--;
		}
	}

	private static void shiftCircleX(char[] src,int k){
		int size = src.length;
		k = k%size;
		reverse(src,0,size-k-1);
		reverse(src,size-k,size-1);
		reverse(src,0,size-1);
	}

	private static void shiftCircle(char[] src, int k) {
		int size = src.length;
		int k1 = k%size;
		int k2 = size -k1;
		char[] array1 = new char[k1];
		char[] array2 = new char[k2];
		for(int i=0;i<k2;i++){
			array2[i] = src[i];
		}
		for(int i = k2;i<size;i++){
			array1[i-k2] = src[i];
		}
		for(int i=0;i<k2;i++){
			src[i+k1] = array2[i];
		}
		for(int i=0;i<k1;i++){
			src[i] = array1[i];
		}
	}

	private static char[] generateArray() {
		String src = "abcd1234";
		return src.toCharArray();
	}
}
