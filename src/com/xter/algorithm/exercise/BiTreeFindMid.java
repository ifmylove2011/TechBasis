package com.xter.algorithm.exercise;

import com.xter.datastructure.BiTreeNode;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/8/26
 * 描述:对于一棵二叉树，令f=(最大结点值+最小结点值)/2，找出距离f值最近值且大于f的结点；
 */
public class BiTreeFindMid {

	public static void main(String[] args) {
		BiTreeNode root = BiTreeNodeArrayPut.getSimpleNode();

		findNear(root, 6);
	}

	private static void findNear(BiTreeNode node, int value) {
		if (node == null) {
			return;
		}
		if (node.data < value) {
			if (node.rightChild != null) {
				findNear(node.rightChild, value);
			} else {
				System.out.println(node.data);
			}
		}else if (node.data > value) {
			if (node.leftChild != null) {
				findNear(node.leftChild, value);
			} else {
				System.out.println(node.data);
			}
		}else{
			if (node.rightChild != null) {
				System.out.println(node.rightChild.data);
			}
		}
	}
}

