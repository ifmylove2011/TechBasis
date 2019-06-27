package com.xter;

public class Main {

	public static void main(String[] args) {
		System.out.println("Hi Tech Company");

		System.out.println(isSuccess());

		System.out.println(isPowerOfTwo(1));
		System.out.println(isPowerOfTwo(2));
		System.out.println(isPowerOfTwo(8));

		System.out.println(5+2<<2);
	}

	public static boolean isSuccess(){
		try{
			return true;
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			System.out.println("finish");
		}

		System.out.println("other");
		return false;
	}

	private static boolean isPowerOfTwo(int val) {
		return (val & -val) == val;
	}
}
