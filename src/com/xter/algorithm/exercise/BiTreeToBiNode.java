package com.xter.algorithm.exercise;

import com.xter.datastructure.BiTreeNode;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/8/24
 * 描述:二元查找树转换为排序的双向链表；
 */
public class BiTreeToBiNode {


	public static void main(String[] args) {
		BiTreeNode root = BiTreeNodeArrayPut.getSimpleNode();

		transform(root);

		BiTreeNode tmp = head.rightChild;
		while (tmp!=null){
			System.out.println(tmp.data);
			tmp = tmp.rightChild;
		}
	}

	private static BiTreeNode head;
	private static BiTreeNode tail;

	public static void transform(BiTreeNode node) {
		if (node == null) {
			return;
		}
		transform(node.leftChild);
		node.leftChild = tail;
		if (null == tail) {
			head = node;
		} else {
			tail.rightChild = node;
		}
		tail = node;
		transform(node.rightChild);
	}


}
