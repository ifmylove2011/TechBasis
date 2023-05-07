package com.xter.demo;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2021/4/20
 * 描述:
 */
public class InitDemo {
//	static int x = 1;
//
//	static{
//		System.out.println(x);
//		x = 0;
//	}


	public static void main(String[] args) {
//		System.out.println(InitDemo.x);
		A a = new B();
		System.out.println("C:"+a.x);
//		A b = new A();
//		System.out.println("C:"+b.x);
	}

	static class A{
		static{
			System.out.println("A static code");
		}

		public int x =1;

		A(){
			x = 2;
			System.out.println("A constructor");
			show();
		}

		{
			System.out.println("A code");
		}

		public void show(){
			System.out.println("A:"+x);
		}
	}

	static class B extends A{
		static{
			System.out.println("B static code");
		}

		public int x =3;

		B(){
			x = 4;
			System.out.println("B constructor");
			show();
		}
		{
			System.out.println("B code");
		}
		public void show(){
			System.out.println("B:"+x);
		}
	}
}
