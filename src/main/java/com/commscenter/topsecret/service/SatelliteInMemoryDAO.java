package com.commscenter.topsecret.service;

import java.util.ArrayList;
import java.util.List;

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

	public List<Satellite> findAll() {
		List<Satellite> satellites = new ArrayList<>();
		satellites.add(new RebelSatellite(1, "Kenobi", new SecretPoint(-500D, -200D)));
		satellites.add(new RebelSatellite(2, "Skywalker", new SecretPoint(100D, -100D)));
		satellites.add(new RebelSatellite(3, "Sato", new SecretPoint(500D, 100D)));
		return satellites;
	}

}
