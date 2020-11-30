package com.xter.algorithm.exercise;

import com.xter.datastructure.BiTreeNode;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/8/26
 * 描述:二叉树中找出与某个整数相等的所有路径；
 */
public class BiTreePathSum {


	public static void main(String[] args) {
		BiTreeNode root = BiTreeNodeArrayPut.getSimpleNode();

		find(root,0,7);
	}

	private static void find(BiTreeNode node, int sum, int value) {
		if (node == null) {
			return;
		}
		sum = sum + node.data;
		if (sum == value) {
			System.out.println(node);
		}
		if (node.leftChild != null) {
			find(node.leftChild, sum, value);
		}
		if (node.rightChild != null) {
			find(node.rightChild, sum, value);
		}
	}
}
