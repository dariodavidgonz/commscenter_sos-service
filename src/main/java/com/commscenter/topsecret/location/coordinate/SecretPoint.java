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

}
