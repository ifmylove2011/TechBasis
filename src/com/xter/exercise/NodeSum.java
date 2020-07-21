package com.xter.exercise;

import com.xter.datastructure.Node;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/7/21
 * 描述:两个单链表，链表的每个结点代表一位数，计算两个数的和；
 * 例如链表(3-1-5)和(5-9-2)输出513+295=808，个数位在链表头
 */
public class NodeSum {

	public static void main(String[] args) {
		Node node1 = generateNode(10);
		Node node2 = generateNode(8);

		Node result = sum(node1, node2);
		while (result.next != null) {
			result = result.next;
			System.out.println(result);
		}
	}

	public static Node sum(Node node1, Node node2) {
		Node result = new Node();
		Node tmp1 = node1.next;
		Node tmp2 = node2.next;

		Node newNode = new Node();
		result.next = newNode;

		int sum = 0;
		while (tmp1 != null || tmp2 != null) {
			sum = (tmp1 == null ? 0 : tmp1.data) + (tmp2 == null ? 0 : tmp2.data) + sum;
			newNode.data = sum % 10;
			sum = sum / 10;
			newNode.next = new Node();
			newNode = newNode.next;
			tmp1 = (tmp1 == null ? null : tmp1.next);
			tmp2 = (tmp2 == null ? null : tmp2.next);
		}
		return result;
	}

	public static Node generateNode(int len) {
		Node head = new Node();

		Node tmp;
		Node cur = head;
		for (int i = 1; i < len; i++) {
			tmp = new Node();
			tmp.data = i;
			cur.next = tmp;
			cur = tmp;
		}
		return head;
	}
}
