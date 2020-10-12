package com.commscenter.sos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commscenter.topsecret.location.coordinate.SecretPoint;
import com.commscenter.topsecret.location.coordinate.StationRelativeLocation;
import com.commscenter.topsecret.location.resolver.LocationNotResolvedException;
import com.commscenter.topsecret.location.resolver.Trilateration2DLocationResolver;
import com.commscenter.topsecret.satellite.RebelSatellite;
import com.commscenter.topsecret.satellite.Satellite;

class Trilateration2DLocationResolverTest {

	static Logger logger = LoggerFactory.getLogger(Trilateration2DLocationResolverTest.class);
	private Satellite kenobi;
	private Satellite skywalker;
	private Satellite sato;
	Trilateration2DLocationResolver resolver;
	

	@BeforeEach 
	public void init() {
		kenobi = new RebelSatellite(1, "Kenobi", new SecretPoint(-500D, -200D));
		skywalker = new RebelSatellite(2, "Skywalker", new SecretPoint(100D, -100D));
		sato = new RebelSatellite(3, "Sato", new SecretPoint(500D, 100D));
		resolver = new Trilateration2DLocationResolver();
	}
	
	@ParameterizedTest
	@CsvSource({"316.22776601683796D,300D,728.0109889280518D,-200D, -100D,0.99",
		"670.820393249937D,200D,400D, 100.00,100.00,0.99",
		"815.65D,934.21,1125.56, -500.50D,615.65D,0.99",
		"815.6501532519932,934.2136653357196,1125.5643795447686,-500.50D, 615.65D, 0.000000001",
		"815.6,934.2,1125.6,-500.50D, 615.65D,0.50"})
	void testTrilaterationValuesWithDecimalPrecision(Double a, Double b,Double c, Double outX, Double outY, Double decimalPrecision) {
		List<StationRelativeLocation> stationLocations = getStationLocations(a,b,c);
		SecretPoint aux1 = resolver.getLocation(stationLocations);
		assertEquals(outX, aux1.getX(),decimalPrecision);
		assertEquals(outY, aux1.getY(),decimalPrecision);
	}
	
	@ParameterizedTest
	@CsvSource({"100D,115.5D,142.7D",
		"10D,5.5D,2.7D"})
	void testLocationNotResolvedException(Double a, Double b,Double c) {
		List<StationRelativeLocation> stationLocations = getStationLocations(a,b,c);
		assertThrows(LocationNotResolvedException.class, () -> {
			resolver.getLocation(stationLocations);
		});
	}
	
	
	private List<StationRelativeLocation> getStationLocations(Double a, Double b, Double c) {
		List<StationRelativeLocation> stationLocations = new ArrayList<>();
		stationLocations.add(new StationRelativeLocation(kenobi.getLocation(), a));
		stationLocations.add(new StationRelativeLocation(skywalker.getLocation(), b));
		stationLocations.add(new StationRelativeLocation(sato.getLocation(), c));
		return stationLocations;
	}

}
