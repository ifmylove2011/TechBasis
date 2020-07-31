package com.xter.algorithm.exercise;

import com.xter.datastructure.Node;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/7/29
 * 描述:在只给定某个结点指针的情况下，删除此结点；
 * 如1-2-3-4-5-6-7，给5的指针，要求将5删除，变为1-2-3-4-6-7；
 */
public class NodeRemove {

	public static void main(String[] args) {
		Node origin = generateNode();
		Node oneNode = origin.next.next.next.next.next;
		removeNode(oneNode);
		print(origin);

	}

	private static void removeNode(Node oneNode){
		if(oneNode.next==null){
			System.out.println("最后结点无法删除");
			return;
		}
		oneNode.data = oneNode.next.data;
		oneNode.next = oneNode.next.next;
//		oneNode.next.next = null;
	}

	public static Node generateNode() {
		Node head = new Node();

		Node tmp;
		Node cur = head;
		for (int i = 1; i < 8; i++) {
			tmp = new Node();
			tmp.data = i;
			cur.next = tmp;
			cur = tmp;
		}
		return head;
	}


	public static void print(Node origin) {
		while (origin != null) {
			origin = origin.next;
			System.out.println(origin);
		}
	}
}
