package com.xter.design.builder;

public class Point {

	private int x;
	private int y;
	private int z;
	private String desc;

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "Point{" +
				"x=" + x +
				", y=" + y +
				", z=" + z +
				", desc='" + desc + '\'' +
				'}';
	}
}
