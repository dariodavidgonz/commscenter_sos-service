package com.commscenter.topsecret.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.commscenter.topsecret.location.coordinate.SecretPoint;
import com.commscenter.topsecret.location.coordinate.StationRelativeLocation;
import com.commscenter.topsecret.location.resolver.LocationResolver;
import com.commscenter.topsecret.message.MessageResolver;
import com.commscenter.topsecret.satellite.Satellite;
import com.commscenter.topsecret.satellite.SatelliteSOS;

/**
 * 
 * Implementation of SOSService
 * 
 * @author Dario Gonzalez
 */
@Component
public class SimpleSOSService implements SOSService {

	@Autowired
	private LocationResolver locationResolver;
	@Autowired
	private MessageResolver messageResolver;
	@Autowired
	private SatelliteDAO satelliteDAO;

	/**
	 ** 
	 *{@inheritDoc}
	 *
	 */
	@Override
	public SecretPoint getLocation(List<Double> distances) {
		List<Satellite> satellites = satelliteDAO.findAll();
		List<StationRelativeLocation> stationLocations = generateStationLocation(distances, satellites);
		return locationResolver.getLocation(stationLocations);
	}

	/**
	 ** 
	 *{@inheritDoc}
	 *
	 */
	@Override
	public String getMessage(List<List<String>> messages) {
		return messageResolver.getMessage(messages);
	}
	
	/**
	 ** 
	 *{@inheritDoc}
	 *
	 */
	@Override
	public SOSResponse resolveSOS(SecretMessage info) {		
		List<Satellite> satellites = satelliteDAO.findAll();	
		validateSatellites(info, satellites);
		List<StationRelativeLocation> stationLocations = generateStationLocation(getDistances(info), satellites);		
		SOSResponse response = new SOSResponse();
		response.setPosition(this.locationResolver.getLocation(stationLocations));   
		response.setMessage(messageResolver.getMessage(getMessages(info)));	
		return response;
	}

	private List<List<String>> getMessages(SecretMessage info) {
		return info.getSatellites().stream().map(SatelliteSOS::getMessage)
				.collect(Collectors.toList());
	}

	private List<Double> getDistances(SecretMessage info) {
		return info.getSatellites().stream().map(SatelliteSOS::getDistance)
				.collect(Collectors.toList());
	}

	
	/**
	 ** 
	 * We verify that each satellite name in SecretMessage is valid
	 *
	 *@throws BadRequestException if the information is not correct
	 */
	private void validateSatellites(SecretMessage info, List<Satellite> satellites) {
		List<String> satelliteNames =  satellites.stream().map(Satellite::getName)
				.collect(Collectors.toList());
		info.getSatellites().stream().allMatch(satSOS -> satelliteNames.contains(satSOS.getName()));
		List<SatelliteSOS> invalidSatellites = info.getSatellites()
				  .stream()
				  .filter(satSOS -> satelliteNames.contains(satSOS.getName()))
				  .collect(Collectors.toList());;
		if (invalidSatellites.size() > 0) {
			String message = invalidSatellites.stream()
		            .map( SatelliteSOS::getName )
		            .collect( Collectors.joining("," ) );
			throw new BadRequestException("Invalid Satellites " + message);
		}
		
	}


	private List<StationRelativeLocation> generateStationLocation(List<Double> distances, List<Satellite> satellites) {
		List<StationRelativeLocation> stationLocations = new ArrayList<>();
		for (int i = 0; i < distances.size(); i++) {
			stationLocations.add(new StationRelativeLocation(satellites.get(i).getLocation(), distances.get(i)));
		}
		return stationLocations;
	}

}
