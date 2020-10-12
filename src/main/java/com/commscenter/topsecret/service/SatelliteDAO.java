package com.commscenter.topsecret.service;

import java.util.List;

import com.commscenter.topsecret.satellite.Satellite;

/**
 * 
 * Data access object for Satellites
 * 
 * @author Dario Gonzalez
 */
public interface SatelliteDAO {

	public List<Satellite> findAll();

}
