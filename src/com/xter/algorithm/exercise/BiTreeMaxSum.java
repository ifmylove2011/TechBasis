package com.xter.algorithm.exercise;

import com.xter.datastructure.BiTreeNode;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/8/20
 * 描述:给定一棵二叉树，找出一棵子树，使得所有结点和最大；
 */
public class BiTreeMaxSum {

	public static void main(String[] args) {
		BiTreeNode root = BiTreeNodeArrayPut.getSimpleNode();
		BiTreeNode maxRoot = new BiTreeNode();

		 maxSum(root,maxRoot);
		System.out.println(maxSum);
		System.out.println(maxRoot);
	}

	static int maxSum = Integer.MIN_VALUE;

	private static int maxSum(BiTreeNode root,BiTreeNode maxRoot){
		if(root==null){
			return 0;
		}
		int leftSum = maxSum(root.leftChild,maxRoot);
		int rightSum = maxSum(root.rightChild,maxRoot);
		int sum = leftSum+rightSum+root.data;
		System.out.println("Sum="+sum);
		if(sum>maxSum){
			maxSum = sum;
			maxRoot.data = root.data;
		}
		return sum;
	}
}
