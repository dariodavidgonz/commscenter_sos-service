package com.commscenter.topsecret.satellite;

import java.util.List;

/**
 * SOS received from a Satellite with information of a ship requesting for help
 * 
 * @author Dario Gonzalez
 */
public class SatelliteSOS {

	private String name;
	private Double distance;
	private List<String> message;

	public SatelliteSOS() {
		super();
	}

	public SatelliteSOS(String name, Double distance, List<String> message) {
		super();
		this.name = name;
		this.distance = distance;
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public List<String> getMessage() {
		return message;
	}

	public void setMessage(List<String> message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "SatelliteSOS [name=" + name + ", distance=" + distance + ", message=" + message + "]";
	}

}
