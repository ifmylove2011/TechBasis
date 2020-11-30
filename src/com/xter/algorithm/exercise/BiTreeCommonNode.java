package com.xter.algorithm.exercise;

import com.xter.datastructure.BiTreeNode;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/8/25
 * 描述:排序树中的两个结点的共同父结点；
 */
public class BiTreeCommonNode {


	public static void main(String[] args) {
		BiTreeNode root = BiTreeNodeArrayPut.getSimpleNode();

		System.out.println(getCommonNode(root,1,6).data);
	}

	public static BiTreeNode getCommonNode(BiTreeNode root, int data1, int data2) {
		if (root == null) {
			return root;
		}
		if ((data1 < root.data) ^ (data2 < root.data)) {
			return root;
		}
		if (root.leftChild != null&&data1<root.leftChild.data){
			return getCommonNode(root.leftChild,data1,data2);
		}
		if (root.rightChild != null&&data1<root.rightChild.data){
			return getCommonNode(root.rightChild,data1,data2);
		}
		return null;
	}
}
