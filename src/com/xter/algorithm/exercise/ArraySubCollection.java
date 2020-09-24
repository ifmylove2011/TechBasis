package com.xter.algorithm.exercise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/9/24
 * 描述:求一个集合的所有子集；
 */
public class ArraySubCollection {

	public static void main(String[] args) {
		String[] content = {"a", "b", "c"};
//		boolean[] place = {false, false, false};
//		collect(content, place, 0);
		List<String> list = collect(content);
		for(String s:list){
			System.out.println(s);
		}

	}

	private static void collect(String[] strings, boolean[] place, int index) {
		System.out.println(Arrays.toString(place) + "--->" + index);
		int size = strings.length;
		if (index == size) {
			System.out.print("<");
			for (int i = 0; i < size; i++) {
				if (place[i]) {
					System.out.print(strings[i] + " ");
				}
			}
			System.out.print(">");
			System.out.println();
		} else {
			place[index] = false;
			collect(strings, place, index + 1);
			place[index] = true;
			collect(strings, place, index + 1);
		}

	}

	private static List<String> collect(String[] strings) {
		List<String> list = new ArrayList<>();
		list.add(strings[0]);
		for (int i = 1; i < strings.length; i++) {
			int size = list.size();
			for (int j = 0; j < size; j++) {
				list.add(list.get(j) + strings[i]);
			}
			list.add(strings[i]);
		}
		return list;
	}
}
