package com.xter.design.builder;

import com.xter.design.builder.simple.Point;

public class Main {

	public static void main(String[] args) {
		PointBuilder builder = new PointBuilder();
		PointDirector director = new PointDirector(builder);
		director.work();

		System.out.println(builder.create());

		Point point = new Point.Builder()
				.setX(1)
				.setY(2)
				.setDesc("Haha")
				.build();
		System.out.println(point);
	}
}
