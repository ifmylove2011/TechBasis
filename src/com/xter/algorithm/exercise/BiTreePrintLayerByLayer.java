package com.xter.algorithm.exercise;

import com.xter.datastructure.BiTreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/8/18
 * 描述: 逐层打印二叉树结点数据；
 */
public class BiTreePrintLayerByLayer {

	public static void main(String[] args) {
		BiTreeNode root = BiTreeNodeArrayPut.getSimpleNode();

		printLayer(root);
	}

	public static void printLayer(BiTreeNode root) {
		if (root == null) {
			return;
		}
		Queue<BiTreeNode> queue = new LinkedList<>();
		queue.add(root);
		while (!queue.isEmpty()) {
			BiTreeNode node = queue.poll();
			System.out.println(node.data);
			if (node.leftChild != null) {
				queue.add(node.leftChild);
			}
			if (node.rightChild != null) {
				queue.add(node.rightChild);
			}
		}
	}

	public static int printLayer(BiTreeNode root,int level){
		if(root==null||level<0){
			return 0;
		}else if(level==0){
			System.out.println(root.data);
		}else{
			return printLayer(root.leftChild,level-1)+printLayer(root.rightChild,level-1);
		}
		return 0;
	}
}
