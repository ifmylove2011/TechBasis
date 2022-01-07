package com.xter.algorithm.other;

import com.xter.util.Trilateration;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2021/1/5
 * 描述:
 */
public class TrilaterationX {
	public static void main(String[] args){
//		double[] xy = trilateration(12641371.971, 4138703.5211, 6, 12641381.9026, 4138706.4714, 6, 12641370.7839, 4138708.7705, 6);
//		System.out.println(xy[0]+"::"+xy[1]);

		test2();
	}

	/**
	 * 等腰直角
	 */
	static void test1(){
		int result = 0;
		Trilateration.Vec3d anchorArray[] = new Trilateration.Vec3d[4];
		for (int i = 0; i < 4; i++) {
			anchorArray[i] = new Trilateration.Vec3d();
		}
		Trilateration.Vec3d report = new Trilateration.Vec3d();

		anchorArray[2].x = 0.000; //anchor0.x uint:m
		anchorArray[2].y = 3.000; //anchor0.y uint:m
		anchorArray[2].z = 0.000; //anchor0.z uint:m

		anchorArray[1].x = 3.000; //anchor1.x uint:m
		anchorArray[1].y = 0.000; //anchor1.y uint:m
		anchorArray[1].z = 0.000; //anchor1.z uint:m

		anchorArray[0].x = 0.000; //anchor2.x uint:m
		anchorArray[0].y = 0.000; //anchor2.y uint:m
		anchorArray[0].z = 0.000; //anchor2.z uint:m

		anchorArray[3].x = 3.000; //anchor3.x uint:m
		anchorArray[3].y = 3.000; //anchor3.y uint:m
		anchorArray[3].z = 0.000; //anchor3.z uint:m

		int[] distanceArray = new int[]{2121,2121,2121,0};
		Trilateration trilateration = new Trilateration();
		result = trilateration.GetLocation(report,0,anchorArray,distanceArray);
		System.out.println(result);
		System.out.println(report.toString());
	}

	/**
	 * 3、4、5直角
	 */
	static void test2(){
		int result = 0;
		Trilateration.Vec3d anchorArray[] = new Trilateration.Vec3d[4];
		for (int i = 0; i < 4; i++) {
			anchorArray[i] = new Trilateration.Vec3d();
		}
		Trilateration.Vec3d report = new Trilateration.Vec3d();

		anchorArray[2].x = 0.000; //anchor0.x uint:m
		anchorArray[2].y = 3.000; //anchor0.y uint:m
		anchorArray[2].z = 0.000; //anchor0.z uint:m

		anchorArray[1].x = 4.000; //anchor1.x uint:m
		anchorArray[1].y = 0.000; //anchor1.y uint:m
		anchorArray[1].z = 0.000; //anchor1.z uint:m

		anchorArray[0].x = 0.000; //anchor2.x uint:m
		anchorArray[0].y = 0.000; //anchor2.y uint:m
		anchorArray[0].z = 0.000; //anchor2.z uint:m

		anchorArray[3].x = 4.000; //anchor3.x uint:m
		anchorArray[3].y = 3.000; //anchor3.y uint:m
		anchorArray[3].z = 0.000; //anchor3.z uint:m

		int[] distanceArray = new int[]{3092,1250,3750,2462};
		Trilateration trilateration = new Trilateration();
		result = trilateration.GetLocation(report,1,anchorArray,distanceArray);
		System.out.println(result);
		System.out.println(report.toString());
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
