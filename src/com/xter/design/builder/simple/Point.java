package com.xter.design.builder.simple;

public class Point {

	private int x;
	private int y;
	private int z;
	private String desc;

	private Point(Builder builder){
		x = builder.x;
		y = builder.y;
		z = builder.z;
		desc = builder.desc;
	}

	public static class Builder{
		private int x = 0;
		private int y = 0;
		private int z = 0;
		private String desc = "nice";

		public Builder setX(int x){
			this.x = x;
			return this;
		}

		public Builder setY(int y){
			this.y = y;
			return this;
		}

		public Builder setZ(int z){
			this.z = z;
			return this;
		}

		public Builder setDesc(String desd){
			this.desc = desd;
			return this;
		}

		public Point build(){
			return new Point(this);
		}
	}
}
