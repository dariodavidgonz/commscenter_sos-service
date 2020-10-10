package com.commscenter.topsecret.location;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Trilateration2DLocationResolver implements LocationResolver {

	public static final Double MAX_TOLERANCE = 10D;

	public SecretPoint getLocation(List<StationLocation> stationLocations) {
		validateStationLocationSize(stationLocations);
		List<SecretPoint> points = new ArrayList<>();
		List<Double> ratios = new ArrayList<>();
		for (StationLocation stationLocation : stationLocations) {
			points.add(stationLocation.getCoordinate());
			ratios.add(stationLocation.getDistance());
		}
		SecretPoint locationResult = doTrilateration(ratios.get(0), ratios.get(1), ratios.get(2), points.get(0), points.get(1),
				points.get(2));
		verifyTrilateration(locationResult, ratios.get(0), ratios.get(1), ratios.get(2), points.get(0), points.get(1),
				points.get(2));
		return locationResult;
	}

	private void validateStationLocationSize(List<StationLocation> stationLocations) {
		if (stationLocations.size() > 3) {
			throw new LocationNotResolvedException();
		}		
	}

	/**
	 * 
	 * Simple Trilateration based on a 2D Model.
	 * 
	 * @param distance1
	 * @param distance2
	 * @param distance3
	 * @param point     a
	 * @param point     b
	 * @param point     c
	 * @return location secret point
	 */
	private static SecretPoint doTrilateration(Double distance1, Double distance2, Double distance3, SecretPoint pointA,
			SecretPoint pointB, SecretPoint pointC) {
		double a = -2 * pointA.getX() + 2 * pointB.getX();
		double b = -2 * pointA.getY() + 2 * pointB.getY();
		double c = distance1 * distance1 - distance2 * distance2 - pointA.getX() * pointA.getX()
				- pointA.getY() * pointA.getY() + pointB.getX() * pointB.getX() + pointB.getY() * pointB.getY();
		double d = -2 * pointB.getX() + 2 * pointC.getX();
		double e = -2 * pointB.getY() + 2 * pointC.getY();
		double f = distance2 * distance2 - distance3 * distance3 - pointB.getX() * pointB.getX()
				- pointB.getY() * pointB.getY() + pointC.getX() * pointC.getX() + pointC.getY() * pointC.getY();
		double x = (c * e - f * b) / (e * a - b * d);
		double y = (c * d - a * f) / (b * d - a * e);		
		return new SecretPoint(x, y);
	}

	private static void verifyTrilateration(SecretPoint result, Double distance1, Double distance2, Double distance3,
			SecretPoint pointA, SecretPoint pointB, SecretPoint pointC) {
		if (!(isPrecisionToPointAccepted(result, distance1, pointA) && isPrecisionToPointAccepted(result, distance2, pointB) && isPrecisionToPointAccepted(result, distance3, pointC))) {
			throw new LocationNotResolvedException();
		}
	}

	private static boolean isPrecisionToPointAccepted(SecretPoint calculatedPoint, Double inputDistanceToRerence, SecretPoint referencePoint) {
		return Math.abs(calculatedPoint.calculateDistanceTo(referencePoint) - inputDistanceToRerence)  <= MAX_TOLERANCE;
	}


}
