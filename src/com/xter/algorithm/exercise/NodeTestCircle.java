package com.xter.algorithm.exercise;

import com.xter.datastructure.Node;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/7/22
 * 描述:检测某单链表是否有环;
 * 若有环，求环入口；
 */
public class NodeTestCircle {

	public static void main(String[] args) {
		Node origin = generateNode();

		Node meet = isLoop(origin);
		if(meet==null){
			System.out.println("无环");
		}else{
			System.out.println("有环，入口为"+findEntry(origin,meet));
		}
	}

	public static Node findEntry(Node head,Node meet){
		Node node1 = head.next;
		Node node2 = meet;
		while (node1.data!=node2.data){
			node1 = node1.next;
			node2 = node2.next;
		}
		return node1;
	}

	public static Node isLoop(Node head) {
//		int counter = 0;
		Node node1 = head.next;
		Node node2 = head.next;
		while (node2 != null && node2.next != null) {
//			counter++;
			node2 = node2.next.next;
			node1 = node1.next;
			if (node2.data == node1.data) {
//				System.out.println(counter);
				return node1;
			}
		}
		return null;
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
		cur.next = head.next.next.next.next;
		return head;
	}
}
