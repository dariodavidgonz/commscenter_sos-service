package com.commscenter.topsecret.location.coordinate;

/**
 * The SecretPoint class defines a point representing a location in (x,y) coordinate space.
 * 
 * @author Dario Gonzalez
 */
public class SecretPoint {

	private Double x;
	private Double y;

	public SecretPoint() {
		super();
	}

	public SecretPoint(Double x, Double y) {
		super();
		this.x = x;
		this.y = y;
	}

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}
	
	/**
	 * Calculates distance from this point to a target point.
	 * 
	 * @param targetPoint point target to calculate distance
	 * @returns distance in Double precision
	 * @see <a href=
	 *      "https://www.wikihow.com/Find-the-Distance-Between-Two-Points"> How to calculate distance between two points</a>
	 */
	public Double calculateDistanceTo(SecretPoint targetPoint) {
		return Math.sqrt((targetPoint.getY() - this.getY()) * (targetPoint.getY() - this.getY()) + (targetPoint.getX() - this.getX()) * (targetPoint.getX() - this.getX()));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((x == null) ? 0 : x.hashCode());
		result = prime * result + ((y == null) ? 0 : y.hashCode());
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
		SecretPoint other = (SecretPoint) obj;
		if (x == null) {
			if (other.x != null)
				return false;
		} else if (!x.equals(other.x))
			return false;
		if (y == null) {
			if (other.y != null)
				return false;
		} else if (!y.equals(other.y))
			return false;
		return true;
	}
	
	

}
