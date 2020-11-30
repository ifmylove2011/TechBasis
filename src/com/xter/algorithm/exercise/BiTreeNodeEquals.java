package com.xter.algorithm.exercise;

import com.xter.datastructure.BiTreeNode;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/8/20
 * 描述:判断两棵二叉树是否相等
 * {@link com.xter.datastructure.BiTreeNode}
 */
public class BiTreeNodeEquals {

	public static void main(String[] args) {
		BiTreeNode node1 = BiTreeNodeArrayPut.getSimpleNode();
		BiTreeNode node2 = BiTreeNodeArrayPut.getSimpleNode();
		System.out.println(node1.equals(node2));
	}
}
