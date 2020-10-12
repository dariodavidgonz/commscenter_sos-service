package com.commscenter.topsecret.satellite.dao;

import java.util.List;
import java.util.Optional;

import com.commscenter.topsecret.satellite.Satellite;

/**
 * 
 * Data access object for Satellites
 * 
 * @author Dario Gonzalez
 */
public interface SatelliteDAO {

	public List<Satellite> findAll();
	public Optional<Satellite> findOne(String name);

}
