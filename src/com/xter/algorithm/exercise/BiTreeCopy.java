package com.xter.algorithm.exercise;

import com.xter.datastructure.BiTreeNode;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/8/26
 * 描述:复制二叉树；
 * 给定一个二叉树根结点，复制此树；
 */
public class BiTreeCopy {

	public static void main(String[] args) {
		BiTreeNode root1 = BiTreeNodeArrayPut.getSimpleNode();

		BiTreeNode root2 = new BiTreeNode();
		copy(root1,root2);

		System.out.println(root2);
	}

	private static void copy(BiTreeNode oldNode,BiTreeNode newNode){
		if(oldNode==null||newNode==null){
			return;
		}
		if(oldNode.leftChild!=null){
			BiTreeNode node2New = new BiTreeNode();
			copy(oldNode.leftChild,node2New);
			newNode.leftChild = node2New;
		}
		newNode.data = oldNode.data;
		if(oldNode.rightChild!=null){
			BiTreeNode node2New = new BiTreeNode();
			copy(oldNode.rightChild,node2New);
			newNode.rightChild = node2New;
		}
	}
}
