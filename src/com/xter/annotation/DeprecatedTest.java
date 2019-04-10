package com.xter.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

@Deprecated
public class DeprecatedTest {

	@Deprecated
	private String value;

	private String value1;

	public static void main(@Deprecated String[] args) {
		System.out.println("Hello World");

		Class dt = DeprecatedTest.class;

		//判断类本身是否被Deprecated注解
		System.out.println("class has Deprecated: " + dt.isAnnotationPresent(Deprecated.class));

		System.out.println("--------------------");
		System.out.println("method has Deprecated: ");
		Method[] methods = dt.getDeclaredMethods();
		for (Method m : methods) {
			//判断方法是否被Deprecated注解
			if(m.isAnnotationPresent(Deprecated.class)){
				Deprecated deprecated = m.getAnnotation(Deprecated.class);
				if (deprecated != null) {
					System.out.println(m.getName());
				}
			}
			Parameter[] parameters = m.getParameters();
			if(parameters!=null&&parameters.length>0){
				for(Parameter p:parameters){
					if(p.isAnnotationPresent(Deprecated.class)){
						Deprecated deprecated = p.getAnnotation(Deprecated.class);

						System.out.println(p.getName());
					}
				}
			}
		}

		System.out.println("--------------------");
		System.out.println("field has Deprecated: ");
		Field[] fields = dt.getDeclaredFields();
		for (Field f : fields) {
			Deprecated deprecated = f.getAnnotation(Deprecated.class);
			if (deprecated != null) {
				System.out.println(f.getName());
			}

		}
	}

	@Deprecated
	public static void work() {
		System.out.println("I'm working");
	}

	public static void study(@Deprecated String subject) {
		System.out.println("I'm studying " + subject);
	}
}
