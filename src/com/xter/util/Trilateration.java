package com.xter.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/10/20
 * 描述:
 */
public class Trilateration {

	static final int TRIL_3SPHERES = 3;
	static final int TRIL_4SPHERES = 4;

	/* Largest nonnegative number still considered zero */
	static final double MAXZERO = 0.01;

	static final int ERR_TRIL_CONCENTRIC = -1;
	static final int ERR_TRIL_COLINEAR_2SOLUTIONS = -2;
	static final int ERR_TRIL_SQRTNEGNUMB = -3;
	static final int ERR_TRIL_NOINTERSECTION_SPHERE4 = -4;
	static final int ERR_TRIL_NEEDMORESPHERE = -5;

	/* Return the difference of two vectors, (vector1 - vector2). */
	Vec3d vdiff(Vec3d vector1, Vec3d vector2) {
		Vec3d v = new Vec3d();
		v.x = vector1.x - vector2.x;
		v.y = vector1.y - vector2.y;
		v.z = vector1.z - vector2.z;
		return v;
	}

	/* Return the sum of two vectors. */
	Vec3d vsum(Vec3d vector1, Vec3d vector2) {
		Vec3d v = new Vec3d();
		v.x = vector1.x + vector2.x;
		v.y = vector1.y + vector2.y;
		v.z = vector1.z + vector2.z;
		return v;
	}

	/* Multiply vector by a number. */
	Vec3d vmul(Vec3d vector, double n) {
		Vec3d v = new Vec3d();
		v.x = vector.x * n;
		v.y = vector.y * n;
		v.z = vector.z * n;
		return v;
	}

	/* Divide vector by a number. */
	Vec3d vdiv(Vec3d vector, double n) {
		Vec3d v = new Vec3d();
		;
		v.x = vector.x / n;
		v.y = vector.y / n;
		v.z = vector.z / n;
		return v;
	}

	/* Return the Euclidean norm. */
	double vdist(Vec3d v1, Vec3d v2) {
		double xd = v1.x - v2.x;
		double yd = v1.y - v2.y;
		double zd = v1.z - v2.z;
		return Math.sqrt(xd * xd + yd * yd + zd * zd);
	}

	/* Return the Euclidean norm. */
	double vnorm(Vec3d vector) {
		return Math.sqrt(vector.x * vector.x + vector.y * vector.y + vector.z * vector.z);
	}

	/* Return the dot product of two vectors. */
	double dot(Vec3d vector1, Vec3d vector2) {
		return vector1.x * vector2.x + vector1.y * vector2.y + vector1.z * vector2.z;
	}

	/* Replace vector with its cross product with another vector. */
	Vec3d cross(Vec3d vector1, Vec3d vector2) {
		Vec3d v = new Vec3d();
		v.x = vector1.y * vector2.z - vector1.z * vector2.y;
		v.y = vector1.z * vector2.x - vector1.x * vector2.z;
		v.z = vector1.x * vector2.y - vector1.y * vector2.x;
		return v;
	}

	/* Return the GDOP (Geometric Dilution of Precision) rate between 0-1.
	 * Lower GDOP rate means better precision of intersection.
	 */
	double gdoprate(Vec3d tag, Vec3d p1, Vec3d p2, Vec3d p3) {
		Vec3d ex, t1, t2, t3;
		double h, gdop1, gdop2, gdop3, result;

		ex = vdiff(p1, tag);
		h = vnorm(ex);
		t1 = vdiv(ex, h);

		ex = vdiff(p2, tag);
		h = vnorm(ex);
		t2 = vdiv(ex, h);

		ex = vdiff(p3, tag);
		h = vnorm(ex);
		t3 = vdiv(ex, h);

		gdop1 = Math.abs(dot(t1, t2));
		gdop2 = Math.abs(dot(t2, t3));
		gdop3 = Math.abs(dot(t3, t1));

		if (gdop1 < gdop2) result = gdop2;
		else result = gdop1;
		if (result < gdop3) result = gdop3;

		return result;
	}

	/* Intersecting a sphere sc with radius of r, with a line p1-p2.
	 * Return zero if successful, negative error otherwise.
	 * mu1 & mu2 are constant to find points of intersection.
	 */
	int sphereline(Vec3d p1, Vec3d p2, Vec3d sc, double r, DoubleValue mu1, DoubleValue mu2) {
		double a, b, c;
		double bb4ac;
		Vec3d dp = new Vec3d();

		dp.x = p2.x - p1.x;
		dp.y = p2.y - p1.y;
		dp.z = p2.z - p1.z;

		a = dp.x * dp.x + dp.y * dp.y + dp.z * dp.z;

		b = 2 * (dp.x * (p1.x - sc.x) + dp.y * (p1.y - sc.y) + dp.z * (p1.z - sc.z));

		c = sc.x * sc.x + sc.y * sc.y + sc.z * sc.z;
		c += p1.x * p1.x + p1.y * p1.y + p1.z * p1.z;
		c -= 2 * (sc.x * p1.x + sc.y * p1.y + sc.z * p1.z);
		c -= r * r;

		bb4ac = b * b - 4 * a * c;

		if (Math.abs(a) == 0 || bb4ac < 0) {
			mu1.value = 0;
			mu2.value = 0;
			return -1;
		}

		mu1.value = (-b + Math.sqrt(bb4ac)) / (2 * a);
		mu2.value = (-b - Math.sqrt(bb4ac)) / (2 * a);

		return 0;
	}

	/* Return TRIL_3SPHERES if it is performed using 3 spheres and return
	 * TRIL_4SPHERES if it is performed using 4 spheres
	 * For TRIL_3SPHERES, there are two solutions: result1 and result2
	 * For TRIL_4SPHERES, there is only one solution: best_solution
	 *
	 * Return negative number for other errors
	 *
	 * To force the function to work with only 3 spheres, provide a duplicate of
	 * any sphere at any place among p1, p2, p3 or p4.
	 *
	 * The last parameter is the largest nonnegative number considered zero;
	 * it is somewhat analogous to machine epsilon (but inclusive).
	 */
	int trilateration(Vec3d result1,
	                  Vec3d result2,
	                  Vec3d best_solution,
	                  Vec3d p1, double r1,
	                  Vec3d p2, double r2,
	                  Vec3d p3, double r3,
	                  Vec3d p4, double r4,
	                  double maxzero) {
		Vec3d ex, ey, ez, t1, t2, t3;
		double h, i, j, x, y, z, t;
		DoubleValue mu1 = new DoubleValue(), mu2 = new DoubleValue(), mu = new DoubleValue();
		int result;

		/*********** FINDING TWO POINTS FROM THE FIRST THREE SPHERES **********/

		// if there are at least 2 concentric spheres within the first 3 spheres
		// then the calculation may not continue, drop it with error -1

		/* h = |p3 - p1|, ex = (p3 - p1) / |p3 - p1| */
		ex = vdiff(p3, p1); // vector p13
		h = vnorm(ex); // scalar p13
		System.out.println("p3.p1->" + h);
		if (h <= maxzero) {
			/* p1 and p3 are concentric, not good to obtain a precise intersection point */
			//printf("concentric13 return -1\n");
			return ERR_TRIL_CONCENTRIC;
		}

		/* h = |p3 - p2|, ex = (p3 - p2) / |p3 - p2| */
		ex = vdiff(p3, p2); // vector p23
		h = vnorm(ex); // scalar p23
		System.out.println("p3.p2->" + h);
		if (h <= maxzero) {
			/* p2 and p3 are concentric, not good to obtain a precise intersection point */
			//printf("concentric23 return -1\n");
			return ERR_TRIL_CONCENTRIC;
		}

		/* h = |p2 - p1|, ex = (p2 - p1) / |p2 - p1| */
		ex = vdiff(p2, p1); // vector p12
		h = vnorm(ex); // scalar p12
		System.out.println("p2.p1->" + h);
		if (h <= maxzero) {
			/* p1 and p2 are concentric, not good to obtain a precise intersection point */
			//printf("concentric12 return -1\n");
			return ERR_TRIL_CONCENTRIC;
		}


		ex = vdiv(ex, h); // unit vector ex with respect to p1 (new coordinate system)

		/* t1 = p3 - p1, t2 = ex (ex . (p3 - p1)) */
		t1 = vdiff(p3, p1); // vector p13
		i = dot(ex, t1); // the scalar of t1 on the ex direction
		t2 = vmul(ex, i); // colinear vector to p13 with the length of i

		/* ey = (t1 - t2), t = |t1 - t2| */
		ey = vdiff(t1, t2); // vector t21 perpendicular to t1
		t = vnorm(ey); // scalar t21
		if (t > maxzero) {
			/* ey = (t1 - t2) / |t1 - t2| */
			ey = vdiv(ey, t); // unit vector ey with respect to p1 (new coordinate system)

			/* j = ey . (p3 - p1) */
			j = dot(ey, t1); // scalar t1 on the ey direction
		} else
			j = 0.0;

		/* Note: t <= maxzero implies j = 0.0. */
		if (Math.abs(j) <= maxzero) {

			/* Is point p1 + (r1 along the axis) the intersection? */
			t2 = vsum(p1, vmul(ex, r1));
			if (Math.abs(vnorm(vdiff(p2, t2)) - r2) <= maxzero &&
					Math.abs(vnorm(vdiff(p3, t2)) - r3) <= maxzero) {
				/* Yes, t2 is the only intersection point. */
				if (result1 != null)
					copyValue(result1, t2);
				if (result2 != null)
					copyValue(result2, t2);
				return TRIL_3SPHERES;
			}

			/* Is point p1 - (r1 along the axis) the intersection? */
			t2 = vsum(p1, vmul(ex, -r1));
			if (Math.abs(vnorm(vdiff(p2, t2)) - r2) <= maxzero &&
					Math.abs(vnorm(vdiff(p3, t2)) - r3) <= maxzero) {
				/* Yes, t2 is the only intersection point. */
				if (result1 != null)
					copyValue(result1, t2);
				if (result2 != null)
					copyValue(result2, t2);
				return TRIL_3SPHERES;
			}
			/* p1, p2 and p3 are colinear with more than one solution */
			return ERR_TRIL_COLINEAR_2SOLUTIONS;
		}

		System.out.println("h is valid =" + h);
		/* ez = ex x ey */
		ez = cross(ex, ey); // unit vector ez with respect to p1 (new coordinate system)

//		x = (r1 * r1 - r2 * r2) / (2 * h) + h / 2;
		x = (r1 * r1 - r2 * r2) / (2 * h) + h / 2;
		y = (r1 * r1 - r3 * r3 + i * i) / (2 * j) + j / 2 - x * i / j;
//		y = (r1 * r1 - r3 * r3 - x * x + (x - i) * (x - i) + j * j) / (2 * j);
//		System.out.println("i="+i+"~~"+p1.x+",j="+j+"~~"+p1.y);
//		i = p1.x;
//		j = p1.y;
//		x = (r3 * r3 - r2 * r2) / (2 * h) + h / 2;
//		y = (r3 * r3 - r1 * r1 + i * i) / (2 * j) + j / 2 - x * i / j;
		z = r1 * r1 - x * x - y * y;
		System.out.println("z=" + z);
		if (z < -maxzero) {
			/* The solution is invalid, square root of negative number */
			return ERR_TRIL_SQRTNEGNUMB;
		} else if (z > 0.0)
			z = Math.sqrt(z);
		else
			z = 0.0;

		/* t2 = p1 + x ex + y ey */
		t2 = vsum(p1, vmul(ex, x));
		t2 = vsum(t2, vmul(ey, y));

		/* result1 = p1 + x ex + y ey + z ez */
		if (result1 != null)
//			result1 = vsum(t2, vmul(ez, z));
			copyValue(result1, vsum(t2, vmul(ez, z)));

		/* result1 = p1 + x ex + y ey - z ez */
		if (result2 != null)
//			result2 = vsum(t2, vmul(ez, -z));
			copyValue(result2, vsum(t2, vmul(ez, -z)));

		/*********** END OF FINDING TWO POINTS FROM THE FIRST THREE SPHERES **********/
		/********* RESULT1 AND RESULT2 ARE SOLUTIONS, OTHERWISE RETURN ERROR *********/


		/************* FINDING ONE SOLUTION BY INTRODUCING ONE MORE SPHERE ***********/

		// check for concentricness of sphere 4 to sphere 1, 2 and 3
		// if it is concentric to one of them, then sphere 4 cannot be used
		// to determine the best solution and return -1

		/* h = |p4 - p1|, ex = (p4 - p1) / |p4 - p1| */
		ex = vdiff(p4, p1); // vector p14
		h = vnorm(ex); // scalar p14
		if (h <= maxzero) {
			/* p1 and p4 are concentric, not good to obtain a precise intersection point */
			//printf("concentric14 return 0\n");
			return TRIL_3SPHERES;
		}
		/* h = |p4 - p2|, ex = (p4 - p2) / |p4 - p2| */
		ex = vdiff(p4, p2); // vector p24
		h = vnorm(ex); // scalar p24
		if (h <= maxzero) {
			/* p2 and p4 are concentric, not good to obtain a precise intersection point */
			//printf("concentric24 return 0\n");
			return TRIL_3SPHERES;
		}
		/* h = |p4 - p3|, ex = (p4 - p3) / |p4 - p3| */
		ex = vdiff(p4, p3); // vector p34
		h = vnorm(ex); // scalar p34
		if (h <= maxzero) {
			/* p3 and p4 are concentric, not good to obtain a precise intersection point */
			//printf("concentric34 return 0\n");
			return TRIL_3SPHERES;
		}

		// if sphere 4 is not concentric to any sphere, then best solution can be obtained
		/* find i as the distance of result1 to p4 */
		t3 = vdiff(result1, p4);
		i = vnorm(t3);
		/* find h as the distance of result2 to p4 */
		t3 = vdiff(result2, p4);
		h = vnorm(t3);

		/* pick the result1 as the nearest point to the center of sphere 4 */
		if (i > h) {
//			best_solution = result1;
			copyValue(best_solution, result1);
//			result1 = result2;
			copyValue(result1, result2);
//			result2 = best_solution;
			copyValue(result2, best_solution);
		}

		int count4 = 0;
		double rr4 = r4;
		result = 1;
		/* intersect result1-result2 vector with sphere 4 */
		while (result != 0 && count4 < 10) {
			result = sphereline(result1, result2, p4, rr4, mu1, mu2);
			rr4 += 0.1;
			count4++;
		}

		if (result != 0) {

			/* No intersection between sphere 4 and the line with the gradient of result1-result2! */
//			best_solution = result1; // result1 is the closer solution to sphere 4
			copyValue(best_solution, result1);
			//return ERR_TRIL_NOINTERSECTION_SPHERE4;

		} else {

			if (mu1.value < 0 && mu2.value < 0) {

				/* if both mu1 and mu2 are less than 0 */
				/* result1-result2 line segment is outside sphere 4 with no intersection */
				if (Math.abs(mu1.value) <= Math.abs(mu2.value)) mu.value = mu1.value;
				else mu.value = mu2.value;
				/* h = |result2 - result1|, ex = (result2 - result1) / |result2 - result1| */
				ex = vdiff(result2, result1); // vector result1-result2
				h = vnorm(ex); // scalar result1-result2
				ex = vdiv(ex, h); // unit vector ex with respect to result1 (new coordinate system)
				/* 50-50 error correction for mu */
				mu.value = 0.5 * mu.value;
				/* t2 points to the intersection */
				t2 = vmul(ex, mu.value * h);
				t2 = vsum(result1, t2);
				/* the best solution = t2 */
//				best_solution = t2;
				copyValue(best_solution, t2);

			} else if ((mu1.value < 0 && mu2.value > 1) || (mu2.value < 0 && mu1.value > 1)) {

				/* if mu1 is less than zero and mu2 is greater than 1, or the other way around */
				/* result1-result2 line segment is inside sphere 4 with no intersection */
				if (mu1.value > mu2.value) mu.value = mu1.value;
				else mu.value = mu2.value;
				/* h = |result2 - result1|, ex = (result2 - result1) / |result2 - result1| */
				ex = vdiff(result2, result1); // vector result1-result2
				h = vnorm(ex); // scalar result1-result2
				ex = vdiv(ex, h); // unit vector ex with respect to result1 (new coordinate system)
				/* t2 points to the intersection */
				t2 = vmul(ex, mu.value * h);
				t2 = vsum(result1, t2);
				/* vector t2-result2 with 50-50 error correction on the length of t3 */
				t3 = vmul(vdiff(result2, t2), 0.5);
				/* the best solution = t2 + t3 */
//				best_solution = vsum(t2, t3);
				copyValue(best_solution, vsum(t2, t3));

			} else if (((mu1.value > 0 && mu1.value < 1) && (mu2.value < 0 || mu2.value > 1))
					|| ((mu2.value > 0 && mu2.value < 1) && (mu1.value < 0 || mu1.value > 1))) {

				/* if one mu is between 0 to 1 and the other is not */
				/* result1-result2 line segment intersects sphere 4 at one point */
				if (mu1.value >= 0 && mu1.value <= 1) mu.value = mu1.value;
				else mu.value = mu2.value;
				/* add or subtract with 0.5*mu.value to distribute error equally onto every sphere */
				if (mu.value <= 0.5) mu.value -= 0.5 * mu.value;
				else mu.value -= 0.5 * (1 - mu.value);
				/* h = |result2 - result1|, ex = (result2 - result1) / |result2 - result1| */
				ex = vdiff(result2, result1); // vector result1-result2
				h = vnorm(ex); // scalar result1-result2
				ex = vdiv(ex, h); // unit vector ex with respect to result1 (new coordinate system)
				/* t2 points to the intersection */
				t2 = vmul(ex, mu.value * h);
				t2 = vsum(result1, t2);
				/* the best solution = t2 */
//				best_solution = t2;
				copyValue(best_solution, t2);

			} else if (mu1.value == mu2.value) {

				/* if both mu1 and mu2 are between 0 and 1, and mu1 = mu2 */
				/* result1-result2 line segment is tangential to sphere 4 at one point */
				mu.value = mu1.value;
				/* add or subtract with 0.5*mu.value to distribute error equally onto every sphere */
				if (mu.value <= 0.25) mu.value -= 0.5 * mu.value;
				else if (mu.value <= 0.5) mu.value -= 0.5 * (0.5 - mu.value);
				else if (mu.value <= 0.75) mu.value -= 0.5 * (mu.value - 0.5);
				else mu.value -= 0.5 * (1 - mu.value);
				/* h = |result2 - result1|, ex = (result2 - result1) / |result2 - result1| */
				ex = vdiff(result2, result1); // vector result1-result2
				h = vnorm(ex); // scalar result1-result2
				ex = vdiv(ex, h); // unit vector ex with respect to result1 (new coordinate system)
				/* t2 points to the intersection */
				t2 = vmul(ex, mu.value * h);
				t2 = vsum(result1, t2);
				/* the best solution = t2 */
//				best_solution = t2;
				copyValue(best_solution, t2);

			} else {

				/* if both mu1 and mu2 are between 0 and 1 */
				/* result1-result2 line segment intersects sphere 4 at two points */

				//return ERR_TRIL_NEEDMORESPHERE;

				mu.value = mu1.value + mu2.value;
				/* h = |result2 - result1|, ex = (result2 - result1) / |result2 - result1| */
				ex = vdiff(result2, result1); // vector result1-result2
				h = vnorm(ex); // scalar result1-result2
				ex = vdiv(ex, h); // unit vector ex with respect to result1 (new coordinate system)
				/* 50-50 error correction for mu */
				mu.value = 0.5 * mu.value;
				/* t2 points to the intersection */
				t2 = vmul(ex, mu.value * h);
				t2 = vsum(result1, t2);
				/* the best solution = t2 */
//				best_solution = t2;
				copyValue(best_solution, t2);

			}
		}

		return TRIL_4SPHERES;

		/******** END OF FINDING ONE SOLUTION BY INTRODUCING ONE MORE SPHERE *********/
	}

	/* This function calls trilateration to get the best solution.
	 *
	 * If any three spheres does not produce valid solution,
	 * then each distance is increased to ensure intersection to happens.
	 *
	 * Return the selected trilateration mode between TRIL_3SPHERES or TRIL_4SPHERES
	 * For TRIL_3SPHERES, there are two solutions: solution1 and solution2
	 * For TRIL_4SPHERES, there is only one solution: best_solution
	 *
	 * nosolution_count = the number of failed attempt before intersection is found
	 * by increasing the sphere diameter.
	 */
	int deca_3dlocate(Vec3d solution1,
	                  Vec3d solution2,
	                  Vec3d best_solution,
	                  int nosolution_count,
	                  double best_3derror,
	                  double best_gdoprate,
	                  Vec3d p1, double r1,
	                  Vec3d p2, double r2,
	                  Vec3d p3, double r3,
	                  Vec3d p4, double r4,
	                  IntValue combination) {
		Vec3d o1 = new Vec3d(), o2 = new Vec3d(), solution = new Vec3d(), ptemp = new Vec3d();
		Vec3d solution_compare1 = new Vec3d(), solution_compare2 = new Vec3d();
		double    /*error_3dcompare1, error_3dcompare2,*/ rtemp;
		double gdoprate_compare1, gdoprate_compare2;
		double ovr_r1, ovr_r2, ovr_r3, ovr_r4;
		int overlook_count, combination_counter;
		int trilateration_errcounter, trilateration_mode34;
		int success, concentric, result;

		trilateration_errcounter = 0;
		trilateration_mode34 = 0;

		combination_counter = 4; /* four spheres combination */

		best_gdoprate = 1; /* put the worst gdoprate init */
		gdoprate_compare1 = 1;
		gdoprate_compare2 = 1;
		solution_compare1.x = 0;
		solution_compare1.y = 0;
		solution_compare1.z = 0;
		//error_3dcompare1 = 0;

		do {
			success = 0;
			concentric = 0;
			overlook_count = 0;
			ovr_r1 = r1;
			ovr_r2 = r2;
			ovr_r3 = r3;
			ovr_r4 = r4;

			do {
				System.out.println("p1->" + p1 + ",p2->" + p2 + ",p3->" + p3);
				result = trilateration(o1, o2, solution, p1, ovr_r1, p2, ovr_r2, p3, ovr_r3, p4, ovr_r4, MAXZERO);

				switch (result) {
					case TRIL_3SPHERES: // 3 spheres are used to get the result
						trilateration_mode34 = TRIL_3SPHERES;
						success = 1;
						break;

					case TRIL_4SPHERES: // 4 spheres are used to get the result
						trilateration_mode34 = TRIL_4SPHERES;
						success = 1;
						break;

					case ERR_TRIL_CONCENTRIC:
						concentric = 1;
						break;

					default: // any other return value goes here
						ovr_r1 += 0.10;
						ovr_r2 += 0.10;
						ovr_r3 += 0.10;
						ovr_r4 += 0.10;
						overlook_count++;
						break;
				}

				//qDebug() << "while(!success)" << overlook_count << concentric << "result" << result;

			} while (success == 0 && (overlook_count <= 5) && concentric == 0);


			if (success != 0) {
				switch (result) {
					case TRIL_3SPHERES:
//						solution1 = o1;
//						solution2 = o2;
						copyValue(solution1, o1);
						copyValue(solution2, o2);
						nosolution_count = overlook_count;

						combination_counter = 0;
						break;

					case TRIL_4SPHERES:
						/* calculate the new gdop */
						gdoprate_compare1 = gdoprate(solution, p1, p2, p3);

						/* compare and swap with the better result */
						if (gdoprate_compare1 <= gdoprate_compare2) {

//							solution1 = o1;
//							solution2 = o2;
//							best_solution = solution;
							copyValue(solution1, o1);
							copyValue(solution2, o2);
							copyValue(best_solution, solution);
							nosolution_count = overlook_count;
							best_3derror = Math.sqrt((vnorm(vdiff(solution, p1)) - r1) * (vnorm(vdiff(solution, p1)) - r1) +
									(vnorm(vdiff(solution, p2)) - r2) * (vnorm(vdiff(solution, p2)) - r2) +
									(vnorm(vdiff(solution, p3)) - r3) * (vnorm(vdiff(solution, p3)) - r3) +
									(vnorm(vdiff(solution, p4)) - r4) * (vnorm(vdiff(solution, p4)) - r4));
							best_gdoprate = gdoprate_compare1;

							/* save the previous result */
							solution_compare2 = solution_compare1;
							//error_3dcompare2 = error_3dcompare1;
							gdoprate_compare2 = gdoprate_compare1;

							combination.value = 5 - combination_counter;

							ptemp = p1;
							p1 = p2;
							p2 = p3;
							p3 = p4;
							p4 = ptemp;
							rtemp = r1;
							r1 = r2;
							r2 = r3;
							r3 = r4;
							r4 = rtemp;
							combination_counter--;
						}
						break;

					default:
						break;
				}
			} else {
				//trilateration_errcounter++;
				trilateration_errcounter = 4;
				combination_counter = 0;
			}

			//ptemp = p1; p1 = p2; p2 = p3; p3 = p4; p4 = ptemp;
			//rtemp = r1; r1 = r2; r2 = r3; r3 = r4; r4 = rtemp;
			//combination_counter--;
			//qDebug() << "while(combination_counter)" << combination_counter;

		} while (combination_counter != 0);

		// if it gives error for all 4 sphere combinations then no valid result is given
		// otherwise return the trilateration mode used
		if (trilateration_errcounter >= 4) return -1;
		else return trilateration_mode34;
	}

	public int GetLocation(Vec3d best_solution, int use4thAnchor, Vec3d[] anchorArray, int[] distanceArray) {
		Vec3d o1 = new Vec3d(), o2 = new Vec3d(), p1 = new Vec3d(), p2 = new Vec3d(), p3 = new Vec3d(), p4 = new Vec3d();
		double r1 = 0, r2 = 0, r3 = 0, r4 = 0, best_3derror = 0, best_gdoprate = 0;
		int result;
		int error = 0;
		IntValue combination = new IntValue();

		Vec3d t3;
		double dist1, dist2;

		/* Anchors coordinate */
		p1.x = anchorArray[0].x;
		p1.y = anchorArray[0].y;
		p1.z = anchorArray[0].z;
		p2.x = anchorArray[1].x;
		p2.y = anchorArray[1].y;
		p2.z = anchorArray[1].z;
		p3.x = anchorArray[2].x;
		p3.y = anchorArray[2].y;
		p3.z = anchorArray[2].z;
		p4.x = anchorArray[0].x;
		p4.y = anchorArray[0].y;
		p4.z = anchorArray[0].z; //4th same as 1st - only 3 used for trilateration

		r1 = (double) distanceArray[0] / 1000.0;
		r2 = (double) distanceArray[1] / 1000.0;
		r3 = (double) distanceArray[2] / 1000.0;

		r4 = (double) distanceArray[3] / 1000.0;

		System.out.println(String.format("r1=%.6f,r2=%.6f,r3=%.6f,r4=%.6f", r1, r2, r3, r4));
		//qDebug() << "GetLocation" << r1 << r2 << r3 << r4;

		//r4 = r1;

		/* get the best location using 3 or 4 spheres and keep it as know_best_location */
		result = deca_3dlocate(o1, o2, best_solution, error, best_3derror, best_gdoprate,
				p1, r1, p2, r2, p3, r3, p4, r1, combination);


		//qDebug() << "GetLocation" << result << "sol1: " << o1.x << o1.y << o1.z << " sol2: " << o2.x << o2.y << o2.z;

		if (result >= 0) {
			if (use4thAnchor == 1) //if have 4 ranging results, then use 4th anchor to pick solution closest to it
			{
				double diff1, diff2;
				/* find dist1 as the distance of o1 to known_best_location */
				t3 = vdiff(o1, anchorArray[3]);
				dist1 = vnorm(t3);

				t3 = vdiff(o2, anchorArray[3]);
				dist2 = vnorm(t3);

				/* find the distance closest to received range measurement from 4th anchor */
				diff1 = Math.abs(r4 - dist1);
				diff2 = Math.abs(r4 - dist2);

				/* pick the closest match to the 4th anchor range */
				if (diff1 < diff2)
//					best_solution = o1;
					copyValue(best_solution, o1);
				else
//					best_solution = o2;
					copyValue(best_solution, o2);
			} else {
				//assume tag is below the anchors (1, 2, and 3)
				if (o1.z < p1.z)
//					best_solution = o1;
					copyValue(best_solution, o1);
				else
//					best_solution = o2;
					copyValue(best_solution, o2);
			}
		}

		if (result >= 0) {
			return result;
		}

		//return error
		return -1;
	}

	public static void main(String[] args) {
		int result = 0;
		Vec3d anchorArray[] = new Vec3d[4];
		for (int i = 0; i < 4; i++) {
			anchorArray[i] = new Vec3d();
		}
		Vec3d report = new Vec3d();
//		int Range_deca[] = new int[4];
		anchorArray[2].x = 0.000; //anchor0.x uint:m
		anchorArray[2].y = 2.900; //anchor0.y uint:m
		anchorArray[2].z = 0.750; //anchor0.z uint:m

		anchorArray[1].x = 3.900; //anchor1.x uint:m
		anchorArray[1].y = 0.000; //anchor1.y uint:m
		anchorArray[1].z = 0.750; //anchor1.z uint:m

		anchorArray[0].x = 0.000; //anchor2.x uint:m
		anchorArray[0].y = 0.000; //anchor2.y uint:m
		anchorArray[0].z = 0.750; //anchor2.z uint:m

		anchorArray[3].x = 0.000; //anchor3.x uint:m
		anchorArray[3].y = 5.80; //anchor3.y uint:m
		anchorArray[3].z = 0.750; //anchor3.z uint:m

//		Range_deca[0] = 5784; //tag to A0 distance
//		Range_deca[1] = 7021; //tag to A1 distance
//		Range_deca[2] = 5995; //tag to A2 distance
//		Range_deca[3] = 2000; //tag to A3 distance
//		Range_deca[0] = 1397; //tag to A0 distance
//		Range_deca[1] = 8014; //tag to A1 distance
//		Range_deca[2] = 10497; //tag to A2 distance
//		Range_deca[3] = 0; //tag to A3 distance

		Trilateration trilateration = new Trilateration();
//		result = trilateration.GetLocation(report, 0, anchorArray, Range_deca);
//		System.out.println(result);
//		System.out.println(report);

		List<int[]> data = getDataFromFile();
		for (int[] d : data) {
			result = trilateration.GetLocation(report, 0, anchorArray, d);
			System.out.println(result);
			System.out.println("report:"+report);
			System.out.println();
		}
	}

	public static List<int[]> getDataFromFile() {
		List<int[]> data = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(new FileReader("trilateration_data_1.txt"));
			String line;
			while ((line = br.readLine()) != null) {
				String[] lineData = line.split(" ");
				if (lineData.length < 4) {
					continue;
				}
				int[] distanceArray = new int[4];
				distanceArray[2] = Integer.valueOf(lineData[2], 16);
				distanceArray[1] = Integer.valueOf(lineData[3], 16);
				distanceArray[0] = Integer.valueOf(lineData[4], 16);
				distanceArray[3] = Integer.valueOf(lineData[5], 16);
				data.add(distanceArray);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("data size:" + data.size());
		return data;
	}

	void copyValue(Vec3d v1, Vec3d v2) {
		v1.x = v2.x;
		v1.y = v2.y;
		v1.z = v2.z;
	}

	/*------------------------- 必要的数据类 -------------------------*/

	static class IntValue {
		public int value;
	}

	static class DoubleValue {
		public double value;
	}

	public static class Vec3d {
		public double x;
		public double y;
		public double z;

		@Override
		public String toString() {
			return String.format("[x=%.3f,y=%.3f,z=%.3f]", x, y, z);
		}
	}
}
