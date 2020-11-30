package com.xter.algorithm.exercise;

import com.xter.datastructure.BiTreeNode;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/8/27
 * 描述:在二叉树中找出路径最大的和；
 * 路径可以以任意结点作为起点和终点；
 */
public class BiTreePathMax {

	public static void main(String[] args) {
		BiTreeNode root = BiTreeNodeArrayPut.getSimpleNode();
		AtomicInteger max = new AtomicInteger(Integer.MIN_VALUE);

		findMaxPath(root,max);
		System.out.println(max.get());
	}

	private static int findMaxPath(BiTreeNode root, AtomicInteger maxValue) {
		if (root == null) {
			return 0;
		}
		int sumLeft = findMaxPath(root.leftChild, maxValue);
		int sumRight = findMaxPath(root.rightChild, maxValue);

		int subMax = sumLeft > sumRight ? sumLeft : sumRight;

		int rootMax = root.data + sumLeft + sumRight;
		int leftMax = root.data + sumLeft;
		int rightMax = root.data + sumRight;

		int max = Math.max(rootMax, Math.max(leftMax, rightMax));
		if (max > maxValue.get()) {
			maxValue.set(max);
		}
		System.out.println("root="+root.data+",rootMax="+rootMax+",subnum="+subMax);
		return root.data + subMax;
	}
}
