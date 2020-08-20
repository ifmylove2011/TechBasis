package com.xter.algorithm.exercise;

import com.sun.glass.ui.Size;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/8/12
 * 描述:在数组中找到满足a+b=c+d的两个数对，其中abcd分别是不同的元素；
 * 如给定{1,2,3,4,5,6,7}，找到1+4 =2+3；
 */
public class FindNumPair {

	public static void main(String[] args) {
		int[] array = generateArray(10);
		System.out.println(Arrays.toString(array));
		sumPair(array);
	}

	public static boolean sumPair(int[] array) {
		Map<Integer, String> indexSumPair = new HashMap<>();
		int size = array.length;
		for (int i = 0; i < size; i++) {
			for (int j = i + 1; j < size; j++) {
				int sum = array[i] + array[j];
				String index = i + "+" + j;
				String oldValue = indexSumPair.put(sum, index);
				if (oldValue != null) {
					String[] indice = oldValue.split("\\+");
					int a = Integer.parseInt(indice[0]);
					int b = Integer.parseInt(indice[1]);
					System.out.println(array[a] + "+" + array[b] + "=" + array[i] + "+" + array[j]);
					return false;
				}
			}
		}
		return false;
	}

	public static int[] generateArray(int size) {
		int[] array = new int[size];
		List<Integer> list = new ArrayList<>();
		Random random = new Random();
		int counter = 0;
		while (counter < size) {
			int value = random.nextInt(20) + 1;
			if (!list.contains(value)) {
				list.add(value);
				counter++;
			}
		}
		for (int i = 0; i < size; i++) {
			array[i] = list.get(i);
		}
		return array;
	}
}
