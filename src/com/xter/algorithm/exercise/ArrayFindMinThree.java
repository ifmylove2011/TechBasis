package com.xter.algorithm.exercise;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/9/15
 * 描述:已知三个升序数组a[l]/b[m]/c[n]，在三个数组中各找一个元素，使得组成的三元素组距离最小；
 * 三元组(a[i]/b[j]/c[k])距离定义：distance = max(|a[i]-b[j]|,|a[i]-c[k]|,|b[j]-c[k]|)
 */
public class ArrayFindMinThree {

	public static void main(String[] args) {
		int[] a = {3, 4, 5, 7, 15};
		int[] b = {10, 12, 14, 16, 17};
		int[] c = {20, 21, 23, 24, 30, 37};

		System.out.println(getMinDistance(a, b, c));
	}

	private static int getMinDistance(int[] a, int[] b, int[] c) {
		int minD = Integer.MAX_VALUE;
		int aSize = a.length;
		int bSize = b.length;
		int cSize = c.length;

		int aIndex = 0;
		int bIndex = 0;
		int cIndex = 0;

		int tmp = 0;

		while (true) {
			tmp = getDistance(a[aIndex], b[bIndex], c[cIndex]);
			if (tmp < minD) {
				minD = tmp;
			}
			int min = getMin(a[aIndex], b[bIndex], c[cIndex]);
			if (min == a[aIndex]) {
				if (++aIndex >= aSize) {
					break;
				}
			} else if (min == b[bIndex]) {
				if (++bIndex >= bSize) {
					break;
				}
			} else {
				if (++cIndex >= cSize) {
					break;
				}
			}
		}

		return minD;
	}


	private static int getDistance(int a, int b, int c) {
		return getMax(Math.abs(a - b), Math.abs(b - c), Math.abs(a - c));
	}

	private static int getMin(int a, int b, int c) {
		int max = a > b ? b : a;
		return c > max ? max : c;
	}

	private static int getMax(int a, int b, int c) {
		int max = a > b ? a : b;
		return c > max ? c : max;
	}

}
