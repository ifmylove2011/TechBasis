package com.xter.exercise;

import com.xter.datastructure.Node;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/7/20
 * 描述:去除无序链表中的重复项
 */
public class NodeRetain {

	public static void main(String[] args) {
		Node origin = generateNode();

//		retain(origin);
		origin = retain1(origin);

		while (origin.next != null) {
			origin = origin.next;
			System.out.println(origin);
		}
	}

	public static void retain(Node head) {
		Node repeatPre = null;
		Node repeat = null;
		for (Node cur = head.next; cur != null; cur = cur.next) {
			repeat = cur.next;
			repeatPre = cur;
			while (repeat != null) {
				if (repeat.data == cur.data) {
					repeatPre.next = repeat.next;
				} else {
					repeatPre = repeat;
				}
				repeat = repeat.next;
			}
		}
	}

	public static void removeDup(Node head) {
		Node outerCur = head.next;
		Node innerCur = null;
		Node innerPre = null;
		for (; outerCur != null; outerCur = outerCur.next) {
			for (innerCur = outerCur.next, innerPre = outerCur; innerCur != null; ) {
				if (outerCur.data == innerCur.data) {
					innerPre.next = innerCur.next;
					innerCur = innerCur.next;
				} else {
					innerPre = innerCur;
					innerCur = innerCur.next;
				}
			}
		}
	}

	/**
	 * 递归方式，原理是相似的
	 *
	 * @param head 当前头
	 * @return 头
	 */
	public static Node retain1(Node head) {
		if (head.next == null) {
			return head;
		}
		head.next = retain1(head.next);
		Node repeat = head.next;
		Node repeatPre = head;
		while (repeat != null) {
			if (repeat.data == head.data) {
				repeatPre.next = repeat.next;
			} else {
				repeatPre = repeat;
			}
			repeat = repeat.next;
		}
		return head;
	}

	public static Node generateNode() {
		Node head = new Node();

		int[] array = {1, 5, 2, 2, 4, 1, 3, 1, 5, 5, 7, 9, 7};

		Node tmp;
		Node cur = head;
		for (int i1 : array) {
			tmp = new Node();
			tmp.data = i1;
			cur.next = tmp;
			cur = tmp;
		}
		return head;
	}
}
