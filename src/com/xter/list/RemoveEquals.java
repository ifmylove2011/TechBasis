package com.xter.list;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RemoveEquals {
	public static void main(String[] args){
		List<Node> list1 = new ArrayList<>();
		list1.add(new Node(1,"a1"));
		list1.add(new Node(2,"a2"));
		list1.add(new Node(3,"a3"));
		list1.add(new Node(4,"a4"));
		list1.add(new Node(5,"a5"));
		List<Node> list2 = new ArrayList<>();
		list2.add(new Node(1,"b1"));
		list2.add(new Node(2,"b2"));
		list2.add(new Node(6,"b6"));
		list2.add(new Node(7,"b7"));
		list2.add(new Node(8,"b8"));
//		list2.removeAll(list2);
		list1.retainAll(list2);
		System.out.println(list1);
		list2.removeAll(list1);
		System.out.println(list2);
		list1.addAll(list2);
		System.out.println(list1);
	}

	static class Node{
		public int id;
		public String attrs;

		public Node(int id, String attrs) {
			this.id = id;
			this.attrs = attrs;
		}

		@Override
		public String toString() {
			return "Node{" +
					"id=" + id +
					", attrs='" + attrs + '\'' +
					'}';
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			Node node = (Node) o;
			return id == node.id;
		}

		@Override
		public int hashCode() {
			return Objects.hash(id);
		}
	}

}
