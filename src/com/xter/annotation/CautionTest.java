package com.xter.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Caution(value3 = "type")
public class CautionTest {

	@Caution(value3 = "field")
	private String example1;

	private String example2;

	public static void main(@Caution(value1 = "ha", value3 = "params") String[] args) {
		Class ct = CautionTest.class;

		//判断类本身是否被Deprecated注解
		System.out.println("class has Caution: " + ct.isAnnotationPresent(Caution.class));

		System.out.println("--------------------");
		System.out.println("which method has Caution: ");

		Method[] methods = ct.getDeclaredMethods();
		for (Method m : methods) {
			//判断方法是否被Caution注解
			if (m.isAnnotationPresent(Caution.class)) {
				Caution caution = m.getAnnotation(Caution.class);
				System.out.println(caution.);
				System.out.println("    method name is " + m.getName() + ", value1=" + caution.value1() + ", value2=" + caution.value2() + ", value3=" + caution.value3());
			}

			Parameter[] parameters = m.getParameters();
			if (parameters != null && parameters.length > 0) {
				for (Parameter p : parameters) {
					//判断参数是否被Caution注解
					if (p.isAnnotationPresent(Caution.class)) {
						Caution caution = p.getAnnotation(Caution.class);
						System.out.println("         params type is " + p.toString() + ", value1=" + caution.value1() + ", value2=" + caution.value2() + ", value3=" + caution.value3());
					}
				}
			}
		}

		System.out.println("--------------------");
		System.out.println("field has Caution: ");

		Field[] fields = ct.getDeclaredFields();
		for (Field f : fields) {
			//判断成员变量是否被Caution注解
			if (f.isAnnotationPresent(Caution.class)) {
				Caution caution = f.getAnnotation(Caution.class);
				System.out.println("    field name is " + f.getName() + ", value1=" + caution.value1() + ", value2=" + caution.value2() + ", value3=" + caution.value3());
			}
		}
	}

	@Caution(value3 = "method")
	public static void work() {
		System.out.println("I'm working");
	}

	public static void study(@Caution(value1 = "study", value2 = 3, value3 = "params") String subject) {
		System.out.println("I'm studying " + subject);
	}

}
