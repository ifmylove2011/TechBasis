package com.xter.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class DataGen {
	private static class Holder{
		private static DataGen INSTANCE = new DataGen();
	}

	public static DataGen getInstance(){
		return Holder.INSTANCE;
	}

	private static final String STR_SCOPE = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ0123456789";

	private Random random;

	public DataGen() {
		random = new Random();
	}

	public List<String> getList(int size, int maxLength,int mode){
		int range = STR_SCOPE.length();
		List<String> arrayList = mode ==0?new ArrayList<>():new LinkedList<>();
		for(int i=0;i<size;i++){
			int length = random.nextInt(maxLength);

			StringBuilder builder = new StringBuilder();
			for(int j=0;j<length;j++){
				builder.append(STR_SCOPE.charAt(random.nextInt(range)));
			}

			arrayList.add(builder.toString());
		}
		return arrayList;
	}
}
