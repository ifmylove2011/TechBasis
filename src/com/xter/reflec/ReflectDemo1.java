package com.xter.reflec;

import com.xter.design.decorate.HeaderPrinter;
import com.xter.design.decorate.IPrinter;
import com.xter.design.decorate.MulitPrinter;

import org.reflections.Reflections;

import java.util.Set;

public class ReflectDemo1 {

	public static void main(String[] args) {
		Reflections reflections = new Reflections("com.xter.design.decorate");
		Set<Class<? extends IPrinter>> subTypes = reflections.getSubTypesOf(IPrinter.class);
		subTypes.forEach(System.out::println);

		System.out.println(MulitPrinter.class.getCanonicalName());

//		System.out.println(	IPrinter.class.isAssignableFrom(MulitPrinter.class));
//		System.out.println(	MulitPrinter.class.isAssignableFrom(HeaderPrinter.class));
	}
}
