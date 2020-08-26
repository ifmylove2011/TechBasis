package com.xter.algorithm.exercise;

import com.xter.datastructure.BiTreeNode;

import java.util.Arrays;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/8/24
 * 描述:判断一个数组是否是二元查找树后序遍历的序列；
 */
public class BiTreePostOrderJudge {

	public static void main(String[] args) {
		BiTreeNode node = BiTreeNodeArrayPut.getSimpleNode();

		int[] array = new int[11];
		getArray(node, array);
//		array[5] = 8;
//		array[8] = 5;
		System.out.println(Arrays.toString(array));

		System.out.println(isPostOrder(array, 0, array.length - 1));
	}


	private static int index = 0;

	private static void getArray(BiTreeNode node, int[] array) {
		if (node == null) {
			return;
		}
		if (node.leftChild != null) {
			getArray(node.leftChild, array);
		}
		array[index++] = node.data;
		if (node.rightChild != null) {
			getArray(node.rightChild, array);
		}
	}

	public static boolean isPostOrder(int[] array, int start, int end) {
		if (start >= end) {
			return true;
		}
		System.out.println("start:" + start + ",end:" + end);
		int pivot = array[end];
		int nextEnd = end;
		for (int i = end - 1; i >= start; i--) {
			if (array[i] < pivot) {
				nextEnd = i;
				break;
			}
		}
		for (int i = start; i < nextEnd; i++) {
			if (array[i] > pivot) {
				return false;
			}
		}
		return isPostOrder(array, start, nextEnd - 1) && isPostOrder(array, nextEnd, end - 1);
	}
}
