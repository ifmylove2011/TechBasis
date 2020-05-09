package com.xter.callback;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/5/8
 * 描述:
 */
public class Main {
	public static void main(String[] args) {
		testCallback();
//		testOutType();
//		testInType();
	}

	static void testCallback() {
		FlingImpl fling = new FlingImpl();
		fling.setGestureListener(new GestureListener<Fling>() {
			@Override
			public void done(Fling gesture) {
				gesture.onFling();
			}
		});
		fling.onMove();
	}

	static void testOutType() {
		List<Integer> list1 = new ArrayList<>();
		List<? extends Number> list2 = new ArrayList<>();
		list1.add(1);
		list1.add(2);
//		list2.add(1);//不允许
		list2 = list1;
		//取出来的数据可以确定是Number的子类
		Number n1 = list2.get(0);
		Number n2 = list2.get(1);
	}

	static void testInType() {
		List<Number> list1 = new ArrayList<>();
		List<? super Integer> list2 = new ArrayList<>();
		list2 = list1;
		list2.add(1);//允许
		list2.add(2);//允许
		//取出来的数据只能视为Object
		Integer a = (Integer) list2.get(0);
		Object b = list2.get(1);
	}

	static void testType(){
		List<Integer> list1 = new ArrayList<>();
		List<?> list2 = new ArrayList<>();
		list1.add(1);
//		list2.add(2);//不允许
		list2 = list1;
		Object a = list1.get(0);
	}
}
