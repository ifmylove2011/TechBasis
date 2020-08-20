package com.xter.algorithm.exercise;

import com.xter.datastructure.BiTreeNode;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/8/13
 * 描述:将有一个有序整数数组放到二叉树中
 */
public class BiTreeNodeArrayPut {

	public static void main(String[] args) {
		int[] array = generateArray(11);
		BiTreeNode root = putDataToNode(array, 0, array.length - 1);
		print(root);
	}

	public static BiTreeNode getSimpleNode(){
		int[] array = generateArray(11);
		BiTreeNode root = putDataToNode(array, 0, array.length - 1);
		return root;
	}

	private static BiTreeNode putDataToNode(int[] array, int start, int end) {
		if (end<start) {
			return null;
		}
		BiTreeNode node = new BiTreeNode();
		int mid = (start + end) / 2;
		node.data = array[mid];
		node.leftChild = putDataToNode(array, start, mid - 1);
		node.rightChild = putDataToNode(array, mid + 1, end);
		return node;
	}

	public static void print(BiTreeNode node) {
		if (node == null) {
			return;
		}
		if (node.leftChild != null) {
			print(node.leftChild);
		}
		System.out.print(node.data + " ");
		if (node.rightChild != null) {
			print(node.rightChild);
		}
	}

	private static int[] generateArray(int size) {
		int[] array = new int[size];
		for (int i = 0; i < size; i++) {
			array[i] = i;
		}
		return array;
	}
}
