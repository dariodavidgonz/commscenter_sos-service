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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coordinate == null) ? 0 : coordinate.hashCode());
		result = prime * result + ((distance == null) ? 0 : distance.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StationRelativeLocation other = (StationRelativeLocation) obj;
		if (coordinate == null) {
			if (other.coordinate != null)
				return false;
		} else if (!coordinate.equals(other.coordinate))
			return false;
		if (distance == null) {
			if (other.distance != null)
				return false;
		} else if (!distance.equals(other.distance))
			return false;
		return true;
	}
	
	

}
