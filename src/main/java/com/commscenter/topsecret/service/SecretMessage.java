package com.commscenter.topsecret.service;

import java.util.List;

import com.commscenter.topsecret.satellite.SatelliteSOS;

/**
 * 
 * A Secret Message containing SOS information from a list of satellites
 * 
 * @author Dario Gonzalez
 */
public class SecretMessage {

	private List<SatelliteSOS> satellites;

	public SecretMessage() {
		super();
	}

	public SecretMessage(List<SatelliteSOS> satellites) {
		super();
		this.satellites = satellites;
	}

	public List<SatelliteSOS> getSatellites() {
		return satellites;
	}

	public void setSatellites(List<SatelliteSOS> satellites) {
		this.satellites = satellites;
	}

	@Override
	public String toString() {
		return "TopSecret [satellites=" + satellites + ", getSatellites()=" + getSatellites() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

}
