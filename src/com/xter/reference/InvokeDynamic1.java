package com.xter.reference;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class InvokeDynamic1 {

	public class GrandFather {
		GrandFather() {
			System.out.println("1");
		}

		public void thinking() {
			System.out.println("I am grandfather");
		}
	}

	public class Father extends GrandFather {
		Father() {
			System.out.println("2");
		}

		public void thinking() {
			System.out.println("I am Father");
		}
	}

	public class Son extends Father {
		Son() {
			System.out.println("3");
		}

		public void thinking() {
			System.out.println("I am son");
			try {
				MethodType mt = MethodType.methodType(void.class);
				MethodHandle mh = MethodHandles.lookup().findSpecial(GrandFather.class, "thinking", mt, Son.class);
				mh.invoke(this);
			} catch (Throwable throwable) {
				throwable.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		(new InvokeDynamic1().new Son()).thinking();
	}
}
