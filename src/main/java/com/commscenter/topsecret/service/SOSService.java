package com.commscenter.topsecret.service;

import java.util.List;

import com.commscenter.topsecret.location.coordinate.SecretPoint;
import com.commscenter.topsecret.satellite.SatelliteSOS;

/**
 * 
 * An SOS Service interface It defines the necessary methods to allow incoming
 * SOS requests to the communication center.
 * 
 * @author Dario Gonzalez
 */
public interface SOSService {

	/**
	 * Returns a location based on a list of distances to satellites. The distances
	 * list should by order by the Satellite Unified Order (Linked in this
	 * documentation)
	 * 
	 * @see <a href=
	 *      "https://github.com/dariodavidgonz/commscenter_sos-service/wiki/Satellite-Unified-Order">Satellite-Unified-Order</a>
	 *
	 *
	 * @param distances distances to each satellite
	 * @return SecretPoint location with axis coordinates
	 * 
	 */
	SecretPoint getLocation(List<Double> distances);

	/**
	 * Return a message based on a list of reference messages. Messages do not be in
	 * any particular order.
	 *
	 * @param refereceMessageLists a list of messages to be use as reference for
	 *                             resolving the unified message
	 * @return message resolved
	 * 
	 * @see <a href=
	 *      "https://github.com/dariodavidgonz/commscenter_sos-service/wiki/Message-Resolver:-Merge-Lists">Message-Resolver</a>
	 */
	String getMessage(List<List<String>> refereceMessageLists);

	/**
	 * Resolves an SOS message based on the information provided
	 *
	 * @param info location and message information to use in resolution
	 * @return sos response with information resolved using input info
	 * 
	 */
	SOSResponse resolveSOS(SecretMessage info);
	
	
	/**
	 * Stores satellite SOS information
	 *
	 * @param msg information to be stored
	 * @return SOSResponse
	 * 
	 */
	SOSResponse receiveSplitSOSMessagePart(SatelliteSOS msg);
	
	/**
	 * Retrieves unified satellite SOS information that was received separately
	 *
	 * @return SOSResponse unified from information available on the system
	 * 
	 */
	SOSResponse resolveSplitSatelliteSOS(); 

}
