package com.xter.algorithm.exercise;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/8/27
 * 描述:
 */
public class DNSCache {

	private TrieNode root = new TrieNode();

	public static void main(String[] args) {
		DNSCache cache = new DNSCache();
		cache.put("12.1.2.3","www.google.com");
		cache.put("12.1.2.4","www.baidu.com");

		System.out.println(cache.findDNS("12.1.2.4"));
	}

	public void put(String ip,String url){
		int len =ip.length();
		TrieNode node = root;

		int index;
		for(index=0;index<len;index++){
			int i = getIndexFromChar(ip.charAt(index));
			if(node.children[i]==null){
				node.children[i] = new TrieNode();
			}
			node = node.children[i];
		}
		node.isLeaf = true;
		node.url = url;
	}

	public String findDNS(String ip){
		TrieNode node = root;
		int len = ip.length();
		int index;
		for (index = 0; index < len; index++) {
			int i = getIndexFromChar(ip.charAt(index));
			if(node.children[i]==null){
				return null;
			}
			node = node.children[i];
		}

		if(node!=null&&node.isLeaf){
			return node.url;
		}
		return null;
	}

	public static int getIndexFromChar(char c) {
		return (c == '.') ? 10 : (c - '0');
	}

	public static char getCharFromIndex(int index) {
		return (index == 10) ? '.' : (char) ('0' + index);
	}

	static class TrieNode {
		private static final int COUNT = 11;

		boolean isLeaf;
		String url;
		TrieNode[] children;

		public TrieNode() {
			this.isLeaf = false;
			this.url = null;
			this.children = new TrieNode[COUNT];
			for (int i = 0; i < COUNT; i++) {
				children[i] = null;
			}
		}
	}
}
