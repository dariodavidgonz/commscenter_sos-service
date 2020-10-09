package com.commscenter.topsecret.location;

public class StationLocation {

	private SecretPoint coordinate;
	private Double distance;

	public StationLocation() {
		super();
	}

	public StationLocation(SecretPoint coordinate, Double distance) {
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
