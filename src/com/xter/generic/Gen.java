package com.xter.generic;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import javafx.util.Pair;

public class Gen {

	public static <T extends Comparable & Serializable, K extends Comparable & Serializable> Pair<T, K> minAndMax(T[] data1, K[] data2) {
		if (data1 == null || data2 == null)
			return null;
		T min = data1[0];
		K max = data2[0];
		for (int i = 1; i < data1.length; i++) {
			if (min.compareTo(data1[i]) > 0) {
				min = data1[i];
			}
		}
		for (int i = 0; i < data2.length; i++) {
			if (max.compareTo(data2[i]) < 0) {
				max = data2[i];
			}
		}

		return new Pair<>(min, max);
	}

	public static void main(String[] args) {
		String[] data1 = {"A","C","F","X"};
		Integer[] data2 = {5,4,9,1,7,2};
		System.out.println(minAndMax(data1,data2));

//		Pair<String,String>[] pairs = new Pair<String,String>[10];//ERROR
		ArrayList<?> list;
	}
}
