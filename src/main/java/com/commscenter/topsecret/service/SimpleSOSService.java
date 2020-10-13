package com.commscenter.topsecret.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.commscenter.topsecret.location.coordinate.SecretPoint;
import com.commscenter.topsecret.location.coordinate.StationRelativeLocation;
import com.commscenter.topsecret.location.resolver.LocationResolver;
import com.commscenter.topsecret.message.MessageResolver;
import com.commscenter.topsecret.satellite.Satellite;
import com.commscenter.topsecret.satellite.SatelliteSOS;
import com.commscenter.topsecret.satellite.dao.SatelliteDAO;
import com.commscenter.topsecret.satellite.dao.SatelliteSOSMessageDAO;

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
	@Autowired
	private SatelliteSOSMessageDAO satelliteSOSMessageDAO;
	
	static Logger logger = LoggerFactory.getLogger(SimpleSOSService.class);

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
	
	@Override
	public SOSResponse receiveSplitSOSMessagePart(SatelliteSOS msg)  {
		validateSatelliteSOS(msg);
		satelliteSOSMessageDAO.saveOrUpdate(msg);
		return new SOSResponse();
	}

	private void validateSatelliteSOS(SatelliteSOS name) {
		if (!satelliteDAO.findOne(name.getName()).isPresent()) {
			throw new BadRequestException("Invalid Satellite: " + name);
		}
		if (name.getDistance() == null || name.getMessage() == null) {
			throw new BadRequestException("Invalid input data for Satellite " + name);
		}
	}

	@Override
	public SOSResponse resolveSplitSatelliteSOS() {
		List<Satellite> satellites = satelliteDAO.findAll();
		List<SatelliteSOS> satelliteSOSList = satellites.stream()
                .map(satellite -> satelliteSOSMessageDAO.findOne(satellite.getName()))
                .collect(Collectors.toList());
		return resolveSOS(new SecretMessage(satelliteSOSList));
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
		if (info.getSatellites() == null) {
			throw new BadRequestException("Invalid Input");
		}
		List<SatelliteSOS> invalidSatellites = info.getSatellites()
				  .stream()
				  .filter(satSOS -> satSOS.getName() == null || !satelliteNames.contains(satSOS.getName().toLowerCase()))
				  .collect(Collectors.toList());
		if (!invalidSatellites.isEmpty()) {
			String message = invalidSatellites.stream()
		            .map( SatelliteSOS::getName )
		            .collect( Collectors.joining("," ) );
			throw new BadRequestException("Invalid Satellites: " + message);
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
