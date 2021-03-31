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
		testShake();
//		testShakeS();
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
