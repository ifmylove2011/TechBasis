package com.xter.algorithm.exercise;

import com.xter.datastructure.Node;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/7/30
 * 描述:判断两个单链表（无环）是事交叉，若相交，找出相交点；
 */
public class NodeIntersectCheck {

	public static void main(String[] args) {
		Node sameNode = generateNode();
		Node head1 = generateNode(4);
		Node tmp1 = head1.next;
		while (tmp1.next!=null){
			tmp1 = tmp1.next;
		}
		tmp1.next = sameNode;
		Node head2 = generateNode(3);
		Node tmp2 = head2.next;
		while (tmp2.next!=null){
			tmp2 = tmp2.next;
		}
		tmp2.next = sameNode;


//		print(head1);
//		print(head2);
		System.out.println(checkIntersect(head1, head2));
	}

	public static Node checkIntersect(Node head1, Node head2) {
		int len1 = 0;
		int len2 = 0;
		Node node1 = head1;
		Node node2 = head2;
		while (node1.next != null) {
			len1++;
			node1 = node1.next;
		}
		while (node2.next != null) {
			len2++;
			node2 = node2.next;
		}
		if (node1 == node2) {
			boolean flag = len1 > len2;
			int k = flag ? len1 - len2 : len2 - len1;
			node1 = head1;
			node2 = head2;
			if (flag) {
				while (k > 0) {
					node1 = node1.next;
					k--;
				}
			} else {
				while (k > 0) {
					node2 = node2.next;
					k--;
				}
			}

			while (node1 != null) {
				node1 = node1.next;
				node2 = node2.next;
				if (node1 == node2) {
					return node1;
				}
			}
		}
		return null;
	}

	public static Node generateNode(int k) {
		Node head = new Node();

		Node tmp;
		Node cur = head;
		for (int i = 1; i < k; i++) {
			tmp = new Node();
			tmp.data = i;
			cur.next = tmp;
			cur = tmp;
		}
		return head;
	}

	public static Node generateNode() {
		Node head = new Node();

		Node tmp;
		Node cur = head;
		cur.data = 4;
		for (int i = 5; i < 8; i++) {
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
