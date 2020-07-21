package com.xter.encrpt;

public class TEADemo {

	public static void main(String[] args) {
		testTEA();
//		System.out.println(0x89abcdef);
//		System.out.println(0xffffffff);
//		System.out.println(0x70000000);
//		System.out.println(Integer.toBinaryString(-1));
//		System.out.println(Long.toHexString(2309737967L));
//
//		long s = 0x89abcdef;
//		System.out.println(Long.toHexString(s));
	}

	public static void testTEA(){
		long[] datas = {0x01234567L, 0x89abcdefL};
		long[] key = { 0x11111111L, 0x22222222L, 0x33333333L, 0x44444444L };
		long y = datas[0];
		long z = datas[1];

		int DELTA = 0x9e3779b9;

		int sum = 0;
		for (int i = 0; i < 1; i++) {
			sum += DELTA;
			y += ((z << 4) + key[0]) ^ (z + sum) ^ ((z >> 5) + key[1]);
			System.out.println(Long.toHexString((z << 4) + key[0]));
			System.out.println(Long.toHexString(z + sum));
			System.out.println(Long.toHexString((z>>5)));
			z += ((y << 4) + key[2]) ^ (y + sum) ^ ((y >> 5) + key[3]);
		}
	}
}
