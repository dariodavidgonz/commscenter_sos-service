package com.commscenter.topsecret.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.commscenter.topsecret.location.coordinate.SecretPoint;
import com.commscenter.topsecret.satellite.RebelSatellite;
import com.commscenter.topsecret.satellite.Satellite;

/**
 * 
 * In memory implementation of the Satellite DAO
 * 
 * @author Dario Gonzalez
 */
@Component
public class SatelliteInMemoryDAO implements SatelliteDAO {

	List<Satellite> satellites;

	public SatelliteInMemoryDAO() {
		super();
		satellites = new ArrayList<>();
		satellites.add(new RebelSatellite(1, "kenobi", new SecretPoint(-500D, -200D)));
		satellites.add(new RebelSatellite(2, "skywalker", new SecretPoint(100D, -100D)));
		satellites.add(new RebelSatellite(3, "sato", new SecretPoint(500D, 100D)));
	}

	public List<Satellite> findAll() {

		return satellites;
	}

	public Optional<Satellite> findOne(String name) {
		return satellites.stream().filter(sat -> sat.getName().equalsIgnoreCase(name)).findFirst();	
	}

}
