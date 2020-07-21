package com.xter.algorithm.other;

/**
 * @author XTER
 * @desc 斐波那契
 * @date 2019/12/10
 */
public class Fabonacci {

	public static void main(String[] args) {
		Fabonacci f = new Fabonacci();
		for (int i = 0; i < 10; i++) {
			System.out.println(f.fabA(i));
//			System.out.println(f.fab(i));
		}
	}

	int fab(int n) {
		if (n < 0) {
			System.out.println("非法数据");
			return 0;
		}
		if (n > 1) {
			return fab(n - 1) + fab(n - 2);
		} else if (n == 1) {
			return 1;
		} else {
			return 0;
		}
	}

	int fabA(int n){
		if(n==0) return 0;
		if(n==1) return 1;

		int array1 = 0;
		int array2 = 1;
		int array3 = 0;
		if(n>1){
			for (int i = 1; i < n; i++) {
				array3 = array1+array2;
				array1 = array2;
				array2 = array3;
			}
		}
		return array3;
	}
}
