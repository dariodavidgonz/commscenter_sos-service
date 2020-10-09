package com.commscenter.topsecret.location;

public class SecretPoint {

	Double x;
	Double y;

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
	
	public Double calculateDistanceTo(SecretPoint b) {
		return Math.sqrt((b.getY() - this.getY()) * (b.getY() - this.getY()) + (b.getX() - this.getX()) * (b.getX() - this.getX()));
	}

}
