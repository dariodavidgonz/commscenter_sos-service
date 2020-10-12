package com.commscenter.topsecret.satellite;

import com.commscenter.topsecret.location.coordinate.SecretPoint;

/**
 * The Satellite interface defines operations over a Satellite.
 * 
 * @author Dario Gonzalez
 */
public interface Satellite {

	SecretPoint getLocation();

	String getName();

	Integer getId();

}
