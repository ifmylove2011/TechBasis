package com.xter.reference;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class InvokeDynamic {

	static class ClassA{
		public void println(String s){
			System.out.println(s);
		}
	}

	public static void main(String[] args) throws Throwable {
		Object obj = System.currentTimeMillis()%2==0?System.out:new ClassA();
		getPrintlnMH(obj).invokeExact("yes, I invoked");
	}

	private static MethodHandle getPrintlnMH(Object receiver) throws NoSuchMethodException, IllegalAccessException {
		MethodType mt = MethodType.methodType(void.class,String.class);
		return MethodHandles.lookup().findVirtual(receiver.getClass(),"println",mt).bindTo(receiver);
	}
}
