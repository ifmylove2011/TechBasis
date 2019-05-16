package com.xter.design.builder;

public class PointDirector {
	private PointBuilder mBuilder;

	public PointDirector(PointBuilder builder) {
		this.mBuilder = builder;
	}

	public void work() {
		mBuilder.buildX(2);
		mBuilder.buildX(3);
		mBuilder.buildZ(4);
		mBuilder.buildDesc("nice");
	}
}
