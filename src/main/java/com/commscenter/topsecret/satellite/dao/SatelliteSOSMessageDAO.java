package com.commscenter.topsecret.satellite.dao;

import com.commscenter.topsecret.satellite.SatelliteSOS;

/**
 * 
 * Data access object for Satellites Messages Parts
 * 
 * @author Dario Gonzalez
 */
public interface SatelliteSOSMessageDAO {

	void saveOrUpdate(SatelliteSOS satellite);
	SatelliteSOS findOne(String satelliteName);
	

}
