package com.xter.algorithm.exercise;

import com.xter.datastructure.BiTreeNode;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/8/26
 * 描述:对二叉树进行镜像反转；
 */
public class BiTreeMirror {

	public static void main(String[] args) {
		BiTreeNode root = BiTreeNodeArrayPut.getSimpleNode();

		mirror(root);
	}

	private static void mirror(BiTreeNode node){
		if(node==null){
			return;
		}
		mirror(node.leftChild);
		mirror(node.rightChild);
		BiTreeNode tmp = node.leftChild;
		node.leftChild = node.rightChild;
		node.rightChild = tmp;
	}
}
