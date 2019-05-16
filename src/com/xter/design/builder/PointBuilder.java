package com.xter.design.builder;

public class PointBuilder {

	private Point point = new Point();

	public void buildX(int x) {
		point.setX(x);
	}

	public void buildY(int y) {
		point.setY(y);
	}

	public void buildZ(int z) {
		point.setZ(z);
	}

	public void buildDesc(String desc) {
		point.setDesc(desc);
	}

	public Point create() {
		return point;
	}

}
