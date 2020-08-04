package com.xter.algorithm.exercise;

import com.xter.datastructure.Node;

import java.util.concurrent.TimeUnit;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/7/17
 * 描述:链表逆序
 */
public class NodeReverse {

	public static void main(String[] args) {
		Node origin = generateNode();
		reverse1(origin);

		while (origin.next != null) {
			origin = origin.next;
			System.out.println(origin);
		}
	}

	public static void reverse(Node node) {
		if (node != null && node.next != null && node.next.next != null) {
			//首结点变尾结点
			Node cur = node.next;
			Node next = cur.next;
			cur.next = null;

			//从第二个结点开始，当前前驱即为下一次循环后驱
			Node pre = cur;
			cur = next;

			while (cur.next != null) {
				//当前结点逆序
				next = cur.next;
				cur.next = pre;
				//游标到下一结点
				pre = cur;
				cur = next;
			}
			cur.next = pre;
			//逆序后的首结点与原头结点建立联系
			node.next = cur;
		}
	}

	public static void reverse1(Node node) {
		node.next = turnNode(node.next);
	}

	/**
	 * 返回的一直是当前的尾结点（逆序后的首结点）
	 *
	 * @param node 当前游标结点
	 * @return 逆序后首结点
	 */
	private static Node turnNode(Node node) {
		if (node == null || node.next == null) {
			return node;
		} else {
			Node tmp = turnNode(node.next);
			node.next.next = node;
			node.next = null;
			return tmp;
		}
	}


	public static void reverse2(Node head) {
		if (head == null || head.next == null) {
			return;
		}
		Node near = head.next.next;
		Node tmp = null;
		head.next.next = null;
		while (near != null) {
			//保存下一个结点，否则无法继续 遍历
			tmp = near.next;
			//当前首结点变成当前结点的下一结点
			near.next = head.next;
			head.next = near;
			near = tmp;
		}
	}

	public static Node generateNode() {
		Node head = new Node();

		Node tmp;
		Node cur = head;
		for (int i = 1; i < 10; i++) {
			tmp = new Node();
			tmp.data = i;
			cur.next = tmp;
			cur = tmp;
		}
		return head;
	}
}
