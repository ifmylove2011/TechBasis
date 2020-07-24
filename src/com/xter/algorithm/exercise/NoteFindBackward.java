package com.xter.algorithm.exercise;

import com.xter.datastructure.Node;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/7/22
 * 描述:找出单链表中的倒数第k个结点；
 * 如1-2-3-4-5-6-7，倒数第3个结点为5
 */
public class NoteFindBackward {
	public static void main(String[] args) {
		Node origin = generateNode();

//		System.out.println(findBack(origin, 3));
		System.out.println(findBack1(origin, 3));
	}

	public static Node findBack(Node head, int k) {
		Node node1 = head;
		int size = 0;
		while (node1.next != null) {
			size++;
			node1 = node1.next;
		}
		Node node2 = head;
		int index = 0;
		int dst = size - k;
		while (index <= dst) {
			index++;
			node2 = node2.next;
		}
		return node2;
	}

	public static Node findBack1(Node head, int k) {
		Node node1 = head.next;
		Node node2 = head.next;
		for (int i = 1; i < k; i++) {
			node2 = node2.next;
		}
		while (node2.next != null) {
			node1 = node1.next;
			node2 = node2.next;
		}
		return node1;
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
}
