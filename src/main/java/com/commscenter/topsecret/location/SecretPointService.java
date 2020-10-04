package com.commscenter.topsecret.location;

import java.util.List;

public class SecretPointService {

	public static double calculateDistanceBetweenPoints(SecretPoint a, SecretPoint b) {
		return Math.sqrt((b.getY() - a.getY()) * (b.getY() - a.getY()) + (b.getX() - a.getX()) * (b.getX() - a.getX()));
	}
	
	/**
	 * 
	 * Simple Trilateration based on a 2D Model.
	 * 
	 * @param ratios of distances to points
	 * @param point a
	 * @param point b
	 * @param point c
	 * @return location secret point
	 */
	private static SecretPoint doTrilateration(List<Double> ratios, SecretPoint pointA, SecretPoint pointB,
			SecretPoint pointC) {
		double a = 2 * pointB.getX() - 2 * pointA.getX();
		double b = 2 * pointB.getY() - 2 * pointA.getY();
		double c = ratios.get(0) * ratios.get(0) - ratios.get(1) * ratios.get(1) - pointA.getX() * pointA.getX()
				+ pointB.getX() * pointB.getX() - pointA.getY() * pointA.getY() + pointB.getY() * pointB.getY();
		double d = 2 * pointC.getX() - 2 * pointB.getX();
		double e = 2 * pointC.getY() - 2 * pointB.getY();
		double f = ratios.get(1) * ratios.get(1) - ratios.get(2) * ratios.get(2) - pointB.getX() * pointB.getX()
				+ pointC.getX() * pointC.getX() - pointB.getY() * pointB.getY() + pointC.getY() * pointC.getY();
		double x = (c * e - f * b) / (e * a - b * d);
		double y = (c * d - a * f) / (b * d - a * e);
		return new SecretPoint(x, y);
	}

	/**
	 * 
	 * Simple Trilateration based on a 2D Model.
	 * 
	 * @param ratios of distances to points
	 * @param points
	 * @return location secret point
	 */
	public static SecretPoint doTrilateration(List<Double> ratios, List<SecretPoint> points) {
		return doTrilateration(ratios, points.get(0), points.get(1), points.get(2));
	}
	
}
