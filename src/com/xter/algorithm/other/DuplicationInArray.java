package com.xter.algorithm.other;

import java.util.Arrays;

/**
 * @author XTER
 * @desc 在一个长度为《的数组里的所有数字都在0〜n-1的范围内。数组中某
 * 些数字是重复的，但不知道有几个数字重复了，也不知道每个数字重复了
 * 几次。请找出数组中任意一个重复的数字。例如，如果输入长度为7的数
 * 组{2, 3, 1，0, 2, 5, 3}，那么对应的输出是重复的数字2或者3。
 * @date 2019/10/8
 */
public class DuplicationInArray {
	public static void main(String[] args) {
		int[] array = {2,3,1,0,2,5,3};

		for (int i = 0; i < array.length; i++) {
			while (array[i]!=i){
				int temp = array[i];
				if(temp == array[temp]){
					System.out.println(temp);
					break;
				}

				array[i] = array[temp];
				array[temp] = temp;
				System.out.println(Arrays.toString(array));
			}
		}
	}
}
