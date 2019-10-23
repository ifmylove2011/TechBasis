package com.xter.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public class StreamCase {

	public static void main(String[] args) {
		test2();
	}

	public static void test1(){
		List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
		List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
		System.out.println(filtered);

		Random random = new Random();
		random.ints().limit(10).forEach(System.out::print);

		System.out.println();
		List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
		// 获取对应的平方数
		List<Integer> squaresList = numbers.stream().map( i -> i*i).distinct().collect(Collectors.toList());
		System.out.println(squaresList);

		List<String>strings1 = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
		// 获取空字符串的数量
		long count = strings1.stream().filter(s -> s.length()==2).count();
		System.out.println(count);

		strings.stream().sorted().mapToInt(new ToIntFunction<String>() {
			@Override
			public int applyAsInt(String value) {
				System.out.println(value);
				return value.getBytes().length;
			}
		}).forEach(System.out::print);
	}

	public static void test2(){
		List<String> someStr = Arrays.asList("a", "b", "c", "e", "a","b");
		// 获取对应的平方数
		List<String> toppledList = someStr.stream().distinct().collect(Collectors.toList());
		System.out.println(toppledList);
	}
}
