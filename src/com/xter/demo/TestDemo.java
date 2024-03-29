package com.xter.demo;

import com.xter.util.ShakeAvoid;

import java.util.concurrent.TimeUnit;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2021/1/9
 * 描述:
 */
public class TestDemo {

	public static void main(String[] args) {
//		testShake();
//		testShakeS();
//		testAnd();
		testBool();
	}

	public static void testBool(){
//		System.out.println(false&&true||true);
//		System.out.println(true&&(false||true));
		if(test111()&&test222()||test333()){
			System.out.println("--------");
		}
	}

	private static boolean test111(){
		System.out.println("111");
		return false;
	}

	private static boolean test222(){
		System.out.println("222");
		return true;
	}

	private static boolean test333(){
		System.out.println("333");
		return false;
	}

	public static void testAnd(){
		int testResult = 2;
		int NETWORK_VALIDATION_RESULT_PARTIAL = 0x02;
		boolean partial = ((testResult & NETWORK_VALIDATION_RESULT_PARTIAL) != 0);
		System.out.println(partial);
	}

	public static void testShake() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println(ShakeAvoid.get().check(111, 400L));
					TimeUnit.MILLISECONDS.sleep(300);
					System.out.println(ShakeAvoid.get().check(111, 400L));
					TimeUnit.MILLISECONDS.sleep(300);
					System.out.println(ShakeAvoid.get().check(111, 400L));
					TimeUnit.MILLISECONDS.sleep(300);
					System.out.println(ShakeAvoid.get().check(111, 400L));
					TimeUnit.MILLISECONDS.sleep(300);
					System.out.println(ShakeAvoid.get().check(111, 400L));
					TimeUnit.MILLISECONDS.sleep(300);
					System.out.println(ShakeAvoid.get().check(111, 400L));
					TimeUnit.MILLISECONDS.sleep(300);
					System.out.println(ShakeAvoid.get().check(111, 400L));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public static void testShakeS(){
		new Thread(new Runnable() {
			@Override
			public void run() {
					System.out.println(ShakeAvoid.get().check(222, 3));
					System.out.println(ShakeAvoid.get().check(222, 3));
					System.out.println(ShakeAvoid.get().check(222, 3));
					System.out.println(ShakeAvoid.get().check(222, 3));
					System.out.println(ShakeAvoid.get().check(222, 3));
					System.out.println(ShakeAvoid.get().check(222, 3));
			}
		}).start();
	}

}
