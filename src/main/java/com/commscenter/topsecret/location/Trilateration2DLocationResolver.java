package com.commscenter.topsecret.location;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Trilateration2DLocationResolver implements LocationResolver {

	public static final Double DELTA_DISTANCE = 10D;

	public SecretPoint getLocation(List<StationLocation> stationLocations) {
		List<SecretPoint> points = new ArrayList<>();
		List<Double> ratios = new ArrayList<>();
		for (StationLocation stationLocation : stationLocations) {
			points.add(stationLocation.getCoordinate());
			ratios.add(stationLocation.getDistance());
		}
		SecretPoint trilateratedLocation = doTrilateration(ratios, points);
		validateTrilateratedLocationAndRatiosWithDelta(ratios, trilateratedLocation, points, DELTA_DISTANCE);
		return trilateratedLocation;
	}

	/**
	 * Validate Trilaterated Location And Ratios With deltaDistance Each distance to
	 * trilaterated location should be the same +/- deltaDistance.
	 * 
	 * @param ratios
	 * @param points
	 * @param deltaDistance
	 */
	private void validateTrilateratedLocationAndRatiosWithDelta(List<Double> ratios, SecretPoint calculatedLocation,
			List<SecretPoint> points, double deltaDistance) {
		for (int i = 0; i < points.size(); i++) {
			validateDistanceBeetweenPoints(points.get(i), ratios.get(i), calculatedLocation, deltaDistance);
		}

	}

	private void validateDistanceBeetweenPoints(SecretPoint satelliteLocation, double distantToSatellite,
			SecretPoint calculatedPoint, double deltaDistance) {
		double distanceToOne = satelliteLocation.calculateDistanceTo(calculatedPoint);
		double absOne = Math.abs(distantToSatellite - distanceToOne);
		if (absOne > deltaDistance) {
			throw new LocationNotResolvedException();
		}
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
