package com.xter.algorithm.exercise;

import com.xter.datastructure.Node;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/7/24
 * 描述:合并两个有序链表，要求合并后仍然有序;
 */
public class NodeCombine {
	public static void main(String[] args) {
		int[] array1 = {1, 3, 6, 7, 9, 10,14,15};
		int[] array2 = {2, 4, 5, 8, 11, 13};
		Node head1 = generateNode(array1);
		Node head2 = generateNode(array2);

		combine(head1, head2);
		print(head1);
	}

	public static void combine(Node head1, Node head2) {
		Node node1 = head1;
		Node tmp1 = null;
		Node tmp2 = null;
		while (node1 != null && node1.next != null) {
			if(head2.next==null){
				return;
			}
			if (node1.next.data < head2.next.data) {
				node1 = node1.next;
			} else {
				tmp2 = head2.next;
				head2.next = tmp2.next;
				tmp1 = node1.next;
				tmp2.next = tmp1;
				node1.next = tmp2;
			}
		}
		node1.next = head2.next;
	}

	public static Node generateNode(int[] array) {
		Node head = new Node();
		int size = array.length;
		Node tmp;
		Node cur = head;
		for (int i = 0; i < size; i++) {
			tmp = new Node();
			tmp.data = array[i];
			cur.next = tmp;
			cur = tmp;
		}
		return head;
	}

	public static void print(Node origin) {
		while (origin != null) {
			System.out.println(origin);
			origin = origin.next;
		}
	}
}
