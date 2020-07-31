package com.xter.algorithm.exercise;


import com.xter.datastructure.Node;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/7/30
 * 描述:给定一个有序链表，其中每个结点也表示一个有序链表，结点包含两个指针，
 * 一是指向主链表下一结点，二是指向此结点头的链表；
 * 如
 * 3  --> 11 --> 15 --> 30
 * |           |           |          |
 * 6          21         22         39
 * |                       |           |
 * 8                      50        40
 * |                                   |
 * 31                                55
 */
public class NodeFlattern {

	public static void main(String[] args) {
		NodeF head = generateNodeF();

		NodeF h =flattern(head);
		print(h);
	}


	public static NodeF flattern(NodeF head){
		if(head==null||head.next==null){
			return head;
		}
		head.next = flattern(head.next);
		head = merge(head,head.next);
		return head;
	}

	public static NodeF merge(NodeF node1,NodeF node2){
		if(node1==null){
			return node2;
		}
		if(node2==null){
			return node1;
		}
		NodeF result;
		if(node1.data<node2.data){
			result = node1;
			result.down = merge(node1.down,node2);
		}else{
			result = node2;
			result.down = merge(node1,node2.down);
		}
		return result;
	}

	public static NodeF generateNode(int[] array) {
		NodeF head = new NodeF();
		int size = array.length;
		NodeF tmp;
		NodeF cur = head;
		cur.data = array[0];
		for (int i = 1; i < size; i++) {
			tmp = new NodeF();
			tmp.data = array[i];
			cur.down = tmp;
			cur = tmp;
		}
		return head;
	}

	public static NodeF generateNodeF(){
		NodeF node1 = generateNode(new int[]{3,6,8,31});
		NodeF node2 = generateNode(new int[]{11,21});
		NodeF node3 = generateNode(new int[]{15,22,50});
		NodeF node4 = generateNode(new int[]{30,39,40,55});
		node3.next = node4;
		node2.next = node3;
		node1.next = node2;
		return node1;
	}

	static class NodeF {
		int data;
		NodeF next,down;

		@Override
		public String toString() {
			return "NodeF{" +
					"data=" + data +
					'}';
		}
	}

	public static void print(NodeF origin) {
		while (origin != null) {
			System.out.println(origin);
			origin = origin.down;
		}
	}
}
