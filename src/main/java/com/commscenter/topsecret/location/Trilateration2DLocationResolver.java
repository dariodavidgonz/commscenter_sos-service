package com.commscenter.topsecret.location;

import java.util.ArrayList;
import java.util.List;

public class Trilateration2DLocationResolver implements LocationResolver {

	private Satellite kenobi = new RebelSatellite(1, "Kenobi", new SecretPoint(-500D, -200D));
	static Satellite skywalker = new RebelSatellite(2, "Skywalker", new SecretPoint(100D, -100D));
	static Satellite sato = new RebelSatellite(3, "Sato", new SecretPoint(500D, 100D));
	public static final Double DELTA_DISTANCE = 10D;

	public SecretPoint getLocation(List<Double> ratios) {
		List<SecretPoint> points = new ArrayList<>();
		points.add(kenobi.getPosition());
		points.add(skywalker.getPosition());
		points.add(sato.getPosition());
		SecretPoint trilateratedLocation = SecretPointService.doTrilateration(ratios, points);
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
		double distanceToOne = SecretPointService.calculateDistanceBetweenPoints(satelliteLocation, calculatedPoint);
		double absOne = Math.abs(distantToSatellite - distanceToOne);
		if (absOne > deltaDistance) {
			throw new LocationNotResolvedException();
		}
	}

}
