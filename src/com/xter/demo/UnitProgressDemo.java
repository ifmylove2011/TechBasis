package com.xter.demo;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2021/11/2
 * 描述:
 */
public class UnitProgressDemo {

	public static void main(String[] args) {
		int totalLength = 1000;
		int unitLength = totalLength/10;
		int unitProgress = 0;
		for (int i = 0; i < totalLength; i=i+4) {
			int curUnitProgress = i/unitLength;
			if(curUnitProgress>unitProgress){
				System.out.println(i/(float)totalLength*100f);
				unitProgress = curUnitProgress;
			}
		}
	}


}
