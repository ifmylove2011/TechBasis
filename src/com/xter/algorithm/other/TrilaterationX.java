package com.xter.algorithm.other;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2021/1/5
 * 描述:
 */
public class TrilaterationX {
	public static void main(String[] args){
		double[] xy = trilateration(12641371.971, 4138703.5211, 6, 12641381.9026, 4138706.4714, 6, 12641370.7839, 4138708.7705, 6);
		System.out.println(xy[0]+"::"+xy[1]);
	}
	public static double[] trilateration(double x1,double y1,double d1, double x2, double y2,double d2, double x3, double y3, double d3)
	{
		double []d={0.0,0.0};
		double a11 = 2*(x1-x3);
		double a12 = 2*(y1-y3);
		double b1 = Math.pow(x1,2)-Math.pow(x3,2) +Math.pow(y1,2)-Math.pow(y3,2) +Math.pow(d3,2)-Math.pow(d1,2);
		double a21 = 2*(x2-x3);
		double a22 = 2*(y2-y3);
		double b2 = Math.pow(x2,2)-Math.pow(x3,2) +Math.pow(y2,2)-Math.pow(y3,2) +Math.pow(d3,2)-Math.pow(d2,2);
		d[0]=(b1*a22-a12*b2)/(a11*a22-a12*a21);
		d[1]=(a11*b2-b1*a21)/(a11*a22-a12*a21);
		return d;
	}

}
