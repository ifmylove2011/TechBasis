package com.xter.algorithm.exercise;

import com.xter.datastructure.Node;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/7/21
 * 描述:链表重新排序，将0-1-2-3-...-(N-1)-(N)排成0-N-1-(N-1)-2-(N-2)...
 * 不允许申请新结点，不允许修改数据域
 */
public class NodeResort {
	public static void main(String[] args) {
		Node origin = generateNode();

		//分割为两段
		Node mid = getMidNode(origin);

		Node right = mid.next;
		mid.next = null;
		Node left = origin;

		right = reverse(right);
		//合并
		Node result = combineNode1(left, right);
		while (result.next != null) {
			result = result.next;
			System.out.println(result);
		}
	}

	/**
	 * 本质是将right各个结点间隔插入left中，先左后右
	 *
	 * @param left  带头的结点
	 * @param right 不带头的结点
	 * @return 链表
	 */
	private static Node combineNode(Node left, Node right) {
		Node leftTmp = left.next;
		Node tmpL;
		Node tmpR = right;
		while (tmpR != null) {
			tmpL = leftTmp.next;
			tmpR = right.next;
			leftTmp.next = right;
			right.next = tmpL;
			right = tmpR;
			leftTmp = tmpL;
		}
		return left;
	}

	/**
	 * 本质是将right各个结点间隔插入left中，先右后左
	 *
	 * @param left  带头的结点
	 * @param right 不带头的结点
	 * @return 链表
	 */
	private static Node combineNode1(Node left, Node right) {
		Node leftTmp = left.next;
		Node tmpL;
		Node tmpR;
		left.next = right;
		while (leftTmp != null) {
			tmpL = leftTmp.next;
			tmpR = right.next;
			right.next = leftTmp;
			leftTmp.next = tmpR;
			right = tmpR;
			leftTmp = tmpL;
		}
		return left;
	}

	private static Node getMidNode(Node head) {
		Node mid = head.next;
		Node end = head.next.next;
		while (end.next != null && end.next.next != null) {
			mid = mid.next;
			end = end.next.next;
		}
		return mid;
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
