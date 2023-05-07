package com.xter.list;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2021/9/1
 * 描述:
 */
public class ListChangeDemo {

	public static void main(String[] args) {
		List<Item> list = new ArrayList<>();
		list.add(new Item(true,"A"));
		list.add(new Item(false,"B"));
		Item A = list.get(1);
		A.isSelect = !A.isSelect;
		System.out.println(list.toString());

		ListIterator<Item> itemListIterator = list.listIterator();
		while (itemListIterator.hasNext()){
			if(itemListIterator.next().isSelect){
				break;
			}
		}
		itemListIterator.previous();
		itemListIterator.add(new Item(false,"C"));
		System.out.println(list.toString());

		Item D = new Item(false,"D");
		list.add(D);
		System.out.println(list.toString());
		D.isSelect = true;
		System.out.println(list.toString());

		ListIterator<Item> listIterator = list.listIterator(1);
		while (listIterator.hasNext()){
			Item item = listIterator.next();
			item.isSelect = !item.isSelect;
		}
		System.out.println(list.toString());
	}

	static class Item{
		public boolean isSelect;
		public String name;

		public Item(boolean isSelect, String name) {
			this.isSelect = isSelect;
			this.name = name;
		}

		@Override
		public String toString() {
			return "Item{" +
					"isSelect=" + isSelect +
					", name='" + name + '\'' +
					'}';
		}
	}
}
