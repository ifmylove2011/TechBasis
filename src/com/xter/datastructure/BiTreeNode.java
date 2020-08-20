package com.xter.datastructure;

import java.util.Objects;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/8/13
 * 描述:二叉树结点
 */
public class BiTreeNode {

	public int data;
	public BiTreeNode leftChild, rightChild;

	@Override
	public String toString() {
		return "BiTreeNode{" +
				"data=" + data +
				", leftChild=" + (leftChild == null ? null : leftChild.data) +
				", rightChild=" + (rightChild == null ? null : rightChild.data) +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BiTreeNode node = (BiTreeNode) o;
		return equals(this, node);
	}

	private boolean equals(BiTreeNode root1, BiTreeNode root2) {
		if (root1 == null && root2 == null) {
			return true;
		}
		if (root1 == null || root2 == null) {
			return false;
		}
		if (root1.data == root2.data) {
			return equals(root1.leftChild, root2.leftChild) && equals(root2.rightChild, root2.rightChild);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(data, leftChild, rightChild);
	}
}
