package com.commscenter.topsecret.location.resolver;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.commscenter.topsecret.location.coordinate.SecretPoint;
import com.commscenter.topsecret.location.coordinate.StationRelativeLocation;

/**
 * Location Resolver implementing a trilateration algorithm based on 2
 * Dimensions and 3 Referenced Points
 * 
 * @author Dario Gonzalez
 * 
 */
@Component
public class Trilateration2DLocationResolver implements LocationResolver {

	public static final Double MAX_TOLERANCE = 50D;

	/**
	 * {@inheritDoc}
	 *
	 * Implementation limited to resolve 3 reference points.
	 * 
	 * @throws LocationNotResolvedException if stationLocations list size is not
	 *                                      equal to 3
	 * 
	 */
	public SecretPoint getLocation(List<StationRelativeLocation> stationLocations) {
		validateStationLocationSize(stationLocations);
		List<SecretPoint> points = getSecretPoints(stationLocations);
		List<Double> distances = getDistances(stationLocations);
		SecretPoint locationResult = doTrilateration(distances.get(0), distances.get(1), distances.get(2),
				points.get(0), points.get(1), points.get(2));
		verifyTrilateration(locationResult, distances.get(0), distances.get(1), distances.get(2), points.get(0),
				points.get(1), points.get(2));
		return locationResult;
	}

	/**
	 * 
	 * Trilateration calculation based on the on mathematical operations over two linear equations.
	 * Returns a location calculated using 3 distances and 3 references to SecretPoint. The
	 * function uses internal variable names that are explained in 'See also' link.
	 *
	 * @param distance1 distance to first reference point 1
	 * @param distance2 distance to first reference point 2
	 * @param distance3 distance to first reference point 3
	 * @param point1    reference point 1
	 * @param point2    reference point 2
	 * @param point3    reference point 3
	 * 
	 * @return SecretPoint location with axis coordinates of the ship
	 * 
	 * @see <a href=
	 *      "https://github.com/dariodavidgonz/commscenter_sos-service/wiki/Location-Resolver:-Trilateration">Location-Resolver:-Trilateration</a>
	 */
	private static SecretPoint doTrilateration(Double distance1, Double distance2, Double distance3, SecretPoint point1,
			SecretPoint point2, SecretPoint point3) {
		Double a = calculateCoefficientX(point1, point2);
		Double b = calculateCoefficientY(point1, point2);
		Double c = calculateConstantCoefficient(distance1, distance2, point1, point2);
		Double d = calculateCoefficientX(point2, point3);
		Double e = calculateCoefficientY(point2, point3);
		Double f = calculateConstantCoefficient(distance2, distance3, point2, point3);
		Double x = (c * e - f * b) / (e * a - b * d);
		Double y = (c * d - a * f) / (b * d - a * e);
		return new SecretPoint(x, y);
	}

	private static double calculateConstantCoefficient(Double distance1, Double distance2, SecretPoint point1,
			SecretPoint point2) {
		return distance1 * distance1 - distance2 * distance2 - point1.getX() * point1.getX()
				- point1.getY() * point1.getY() + point2.getX() * point2.getX() + point2.getY() * point2.getY();
	}

	private static double calculateCoefficientY(SecretPoint pointV, SecretPoint pointW) {
		return -2 * pointV.getY() + 2 * pointW.getY();
	}

	private static double calculateCoefficientX(SecretPoint pointV, SecretPoint pointW) {
		return -2 * pointV.getX() + 2 * pointW.getX();
	}

	/**
	 * Return the location of a ship based on a list of 3 reference SecretPoint.
	 * 
	 * Trilateration calculation is based on the simplification of mathematical
	 * operations.
	 * 
	 * The function uses internal variable names that are explained in 'See also'
	 * link.
	 *
	 * @param resultPoint location to verify
	 * @param distance1   distance to first reference point 1
	 * @param distance2   distance to first reference point 2
	 * @param distance3   distance to first reference point 3
	 * @param point1      reference point 1
	 * @param point2      reference point 2
	 * @param point3      reference point 3
	 * 
	 * @throws LocationNotResolvedException if the point is not valid within the
	 *                                      maximum tolerance
	 * @see <a href=
	 *      "https://github.com/dariodavidgonz/commscenter_sos-service/wiki/Location-Resolver:-Trilateration">Location-Resolver:-Trilateration</a>
	 */
	private void verifyTrilateration(SecretPoint resultPoint, Double distance1, Double distance2, Double distance3,
			SecretPoint point1, SecretPoint point2, SecretPoint point3) {
		if (!(isPrecisionToPointAcceptable(distance1, resultPoint, point1)
				&& isPrecisionToPointAcceptable(distance2, resultPoint, point2)
				&& isPrecisionToPointAcceptable(distance3, resultPoint, point3))) {
			throw new LocationNotResolvedException();
		}
	}

	/**
	 * Given two points, this method checks if the distance between them is the
	 * expected within a MAX_TOLERANCE
	 * 
	 * @param expectedDistance distance between to points to validate
	 * @param pointA           location reference point A of validation
	 * @param pointB           reference point B of validation
	 * 
	 * @returns true if the distance is the expected within the tolerance; false
	 *          otherwise
	 * 
	 * @see <a href=
	 *      "https://github.com/dariodavidgonz/commscenter_sos-service/wiki/Location-Resolver:-Trilateration">Location-Resolver:-Trilateration</a>
	 */
	private boolean isPrecisionToPointAcceptable(Double expectedDistance, SecretPoint pointA, SecretPoint pointB) {
		return Math.abs(pointA.calculateDistanceTo(pointB) - expectedDistance) <= MAX_TOLERANCE;
	}

	private List<Double> getDistances(List<StationRelativeLocation> stationLocations) {
		return stationLocations.stream().map(StationRelativeLocation::getDistance).collect(Collectors.toList());
	}

	private List<SecretPoint> getSecretPoints(List<StationRelativeLocation> stationLocations) {
		return stationLocations.stream().map(StationRelativeLocation::getCoordinate).collect(Collectors.toList());
	}

	/**
	 * validate StationLocation list size is equals to 3, otherwise trilateration
	 * will not be possible
	 * 
	 * @throws LocationNotResolvedException if stationLocations list size is not
	 *                                      equal to 3
	 * 
	 */
	private void validateStationLocationSize(List<StationRelativeLocation> stationLocations) {
		if (stationLocations.size() != 3) {
			throw new LocationNotResolvedException();
		}
	}

}
