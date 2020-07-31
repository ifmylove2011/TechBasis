package com.xter.algorithm.exercise;

import com.xter.datastructure.Node;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/7/23
 * 描述:链表相邻元素翻转，如1-2-3-4-5-6-7，翻转后为2-1-4-3-6-5-7；
 * 引申K个元素为一组翻转，如K=3，则1-2-3-4-5-6-7翻转为3-2-1-6-5-4-7
 */
public class NodeReverseNear {

	public static void main(String[] args) {
		Node origin = generateNode();

//		nearReverse(origin);
		groupReverse(origin, 4);
		while (origin != null) {
			System.out.println(origin);
			origin = origin.next;
		}
	}

	public static void nearReverse(Node head) {
		Node node1 = head.next;
		Node node2 = head;
		Node near = null;
		while (node1 != null && node1.next != null) {
			//存储下一次起始结点
			near = node1.next.next;
			//更改与上一次尾结点的连结点
			node2.next = node1.next;
			//翻转
			node1.next.next = node1;
			//接续当前尾结点与下一次起始结点
			node1.next = near;
			//将当前结点更改为尾结点
			node2 = node1;
			//将下一次首结点赋值给当前
			node1 = near;
		}
	}

	public static void groupReverse(Node head, int k) {
		if (head == null || head.next == null || k < 2) {
			return;
		}
		Node start = head.next;
		Node result = head;
		Node end = null;
		Node nextStart = null;
		while (start != null) {
			end = start;
			for (int i = 1; i < k; i++) {
				if (end != null) {
					end = end.next;
				} else {
					return;
				}
			}

			nextStart = end.next;
			end.next = null;
			result.next = reverse(start);
			start.next = nextStart;
			result = start;
			start = nextStart;
		}
	}

	/**
	 * 不带头链表逆序
	 *
	 * @param src 原链表
	 * @return 处理后链表
	 */
	private static Node reverse(Node src) {
		Node last = src;
		Node mid = src.next;
		Node pre = null;
		last.next = null;
		while (mid != null) {
			pre = mid.next;
			mid.next = last;
			last = mid;
			mid = pre;
		}
		return last;
	}

	public static Node generateNode() {
		Node head = new Node();

		Node tmp;
		Node cur = head;
		for (int i = 1; i < 11; i++) {
			tmp = new Node();
			tmp.data = i;
			cur.next = tmp;
			cur = tmp;
		}
		return head;
	}
}
