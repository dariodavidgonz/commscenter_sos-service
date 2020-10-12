package com.commscenter.topsecret.location.coordinate;

/**
 * The StationRelativeLocation class defines a relative location of an station to a
 * reference point. It indicates where the station is located and also how far
 * in distance is the station from the reference point.
 * 
 * The reference point is unknown for the StationLocation.
 * 
 * @author Dario Gonzalez
 */
public class StationRelativeLocation {

	private SecretPoint coordinate;
	private Double distance;

	public StationRelativeLocation() {
		super();
	}

	public StationRelativeLocation(SecretPoint coordinate, Double distance) {
		super();
		this.coordinate = coordinate;
		this.distance = distance;
	}

	public SecretPoint getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(SecretPoint coordinate) {
		this.coordinate = coordinate;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

}
