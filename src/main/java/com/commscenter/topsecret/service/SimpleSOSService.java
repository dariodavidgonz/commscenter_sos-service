package com.commscenter.topsecret.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.commscenter.topsecret.location.LocationResolver;
import com.commscenter.topsecret.location.Satellite;
import com.commscenter.topsecret.location.SecretPoint;
import com.commscenter.topsecret.location.StationLocation;
import com.commscenter.topsecret.message.MessageResolver;

@Component
public class SimpleSOSService implements SOSService {

	@Autowired
	private LocationResolver trilateration2DLocationResolver;
	@Autowired
	private MessageResolver simpleMessageResolver;
	@Autowired
	private SatelliteService satelliteService;

	@Override
	public SecretPoint getLocation(List<Double> distances) {
		List<Satellite> satellites = satelliteService.getSatellites();
		List<StationLocation> stationLocations = generateStationLocation(distances, satellites);
		return this.trilateration2DLocationResolver.getLocation(stationLocations);
	}

	@Override
	public String getMessage(List<List<String>> messages) {
		return simpleMessageResolver.getMessage(messages);
	}
	
	private List<StationLocation> generateStationLocation(List<Double> distances, List<Satellite> satellites) {
		List<StationLocation> stationLocations = new ArrayList<>();
		for (int i = 0; i < distances.size(); i++) {
			stationLocations.add(new StationLocation(satellites.get(i).getLocation(), distances.get(i)));
		}
		return stationLocations;
	}

}
