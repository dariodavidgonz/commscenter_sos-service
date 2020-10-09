package com.commscenter.topsecret.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.commscenter.topsecret.location.LocationResolver;
import com.commscenter.topsecret.location.RebelSatellite;
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


	@Override
	public SecretPoint getLocation(List<Double> ratios) {
		List<Satellite> satellites = new ArrayList<>();
		satellites.add(new RebelSatellite(1, "Kenobi", new SecretPoint(-500D, -200D)));
		satellites.add(new RebelSatellite(2, "Skywalker", new SecretPoint(100D, -100D)));
		satellites.add(new RebelSatellite(3, "Sato", new SecretPoint(500D, 100D)));		
		List<StationLocation> stationLocations = new ArrayList<>();
		for (int i = 0; i < ratios.size(); i++) {
			stationLocations.add(new StationLocation(satellites.get(i).getLocation(), ratios.get(i)));
		}		
		return this.trilateration2DLocationResolver.getLocation(stationLocations);
	}
	
	@Override
	public String getMessage(List<List<String>> messages) {		
		return simpleMessageResolver.getMessage(messages);
	}

}
