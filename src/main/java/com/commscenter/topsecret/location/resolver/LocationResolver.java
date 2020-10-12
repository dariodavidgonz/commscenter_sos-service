package com.commscenter.topsecret.location.resolver;

import java.util.List;

import com.commscenter.topsecret.location.coordinate.SecretPoint;
import com.commscenter.topsecret.location.coordinate.StationRelativeLocation;

/**
 * Location Resolver interface. The implementations of this interface are able
 * to use different strategies to resolve a location based on reference points
 * 
 * @author Dario Gonzalez
 */
public interface LocationResolver {

	/**
	 * Returns a location based on a list of reference points.
	 *
	 * @param stationLocations a list of stationLocations with information relative
	 *                         to the location to resolve
	 * @return SecretPoint location with axis coordinates of the location
	 * 
	 * @see <a href=
	 *      "https://github.com/dariodavidgonz/commscenter_sos-service/wiki/Location-Resolver:-Trilateration">Location-Resolver:-Trilateration</a>
	 */
	SecretPoint getLocation(List<StationRelativeLocation> stationLocations);

}
