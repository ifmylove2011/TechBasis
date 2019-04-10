package com.xter.annotation;

import java.lang.reflect.Method;

public class CautionTest {

	@Deprecated
	public static void main(@Deprecated String[] args) {
		@Deprecated Class cautionTest = CautionTest.class;
		for (Method m : cautionTest.getMethods()) {
			Caution caution = m.getAnnotation(Caution.class);
			if (caution != null) {
				System.out.println(m.getName());
			}
		}
	}

}
