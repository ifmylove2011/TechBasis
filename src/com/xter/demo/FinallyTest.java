package com.xter.demo;

public class FinallyTest {

	public static void main(String[] args) {
		System.out.println(calc(3,2));
		calc();
	}

	private static int calc(int a,int b){
		try{
			int c = 2/0;
			return a+b;
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			System.out.println("invoke this line");
		}
		return a*b;
	}

	private static void calc(){
		int a=3,b= 4;
		try{
			int c = 2/0;
		}catch (Exception e){
			e.printStackTrace();
//			return;
		}finally {
			System.out.println("invoke this line");
		}
		System.out.println("calc this");
	}
}
