package com.commscenter.sos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commscenter.topsecret.location.LocationNotResolvedException;
import com.commscenter.topsecret.location.RebelSatellite;
import com.commscenter.topsecret.location.Satellite;
import com.commscenter.topsecret.location.SecretPoint;
import com.commscenter.topsecret.location.StationLocation;
import com.commscenter.topsecret.location.Trilateration2DLocationResolver;

class Trilateration2DLocationResolverTest {

	static Logger logger = LoggerFactory.getLogger(Trilateration2DLocationResolverTest.class);
	private Satellite kenobi = new RebelSatellite(1, "Kenobi", new SecretPoint(-500D, -200D));
	static Satellite skywalker = new RebelSatellite(2, "Skywalker", new SecretPoint(100D, -100D));
	static Satellite sato = new RebelSatellite(3, "Sato", new SecretPoint(500D, 100D));
	

	@Test
	void testTrilaterationExactValues() {
		List<StationLocation> stationLocations = new ArrayList<>();
		stationLocations.add(new StationLocation(kenobi.getLocation(), 316.22776601683796D));
		stationLocations.add(new StationLocation(skywalker.getLocation(), 300D));
		stationLocations.add(new StationLocation(sato.getLocation(), 728.0109889280518D));
		Trilateration2DLocationResolver resolver = new Trilateration2DLocationResolver();
		SecretPoint aux1 = resolver.getLocation(stationLocations);
		assertEquals(-200.0D, aux1.getX());
		assertEquals(-100.0D, aux1.getY());
	}

	@Test
	void testLocationFoundNineDecimalPrecision() {
		Trilateration2DLocationResolver resolver = new Trilateration2DLocationResolver();
		List<StationLocation> stationLocations = new ArrayList<>();
		stationLocations.add(new StationLocation(kenobi.getLocation(), 815.6501532519932));
		stationLocations.add(new StationLocation(skywalker.getLocation(), 934.2136653357196));
		stationLocations.add(new StationLocation(sato.getLocation(), 1125.5643795447686));
		SecretPoint aux1 = resolver.getLocation(stationLocations);
		assertEquals(-500.50D, aux1.getX(), 0.000000001);
		assertEquals(615.65D, aux1.getY(), 0.000000001);
	}

	
	@Test
	void testInputDistancesRoundedTwoDecimals() {
		Trilateration2DLocationResolver resolver = new Trilateration2DLocationResolver();
		List<StationLocation> stationLocations = new ArrayList<>();
		stationLocations.add(new StationLocation(kenobi.getLocation(), 815.65));
		stationLocations.add(new StationLocation(skywalker.getLocation(), 934.21));
		stationLocations.add(new StationLocation(sato.getLocation(), 1125.56));
		SecretPoint aux1 = resolver.getLocation(stationLocations);
		assertEquals(-500.50D, aux1.getX(), 0.01);
		assertEquals(615.65D, aux1.getY(), 0.01);
	}
	
	@Test
	void testInputDistancesRoundedOneDecimal() {
		Trilateration2DLocationResolver resolver = new Trilateration2DLocationResolver();
		List<StationLocation> stationLocations = new ArrayList<>();
		stationLocations.add(new StationLocation(kenobi.getLocation(), 815.6));
		stationLocations.add(new StationLocation(skywalker.getLocation(), 934.2));
		stationLocations.add(new StationLocation(sato.getLocation(), 1125.6));
		SecretPoint aux1 = resolver.getLocation(stationLocations);
		assertEquals(-500.50D, aux1.getX(), 0.5);
		assertEquals(615.65D, aux1.getY(), 0.5);
	}	

	@Test
	void testLocationNotResolvedException() {
		Trilateration2DLocationResolver resolver = new Trilateration2DLocationResolver();
		List<StationLocation> stationLocations = new ArrayList<>();
		stationLocations.add(new StationLocation(kenobi.getLocation(), 100D));
		stationLocations.add(new StationLocation(skywalker.getLocation(), 115.5D));
		stationLocations.add(new StationLocation(sato.getLocation(), 142.7D));
		assertThrows(LocationNotResolvedException.class, () -> {
			resolver.getLocation(stationLocations);
		});
	}
	
	@Test
	void testLocationNotResolvedNotIntersectingDistancesException() {
		Trilateration2DLocationResolver resolver = new Trilateration2DLocationResolver();
		List<StationLocation> stationLocations = new ArrayList<>();
		stationLocations.add(new StationLocation(kenobi.getLocation(), 10D));
		stationLocations.add(new StationLocation(skywalker.getLocation(), 5.5D));
		stationLocations.add(new StationLocation(sato.getLocation(), 2.7D));
		List<Double> aux = new ArrayList<Double>();
		assertThrows(LocationNotResolvedException.class, () -> {
			resolver.getLocation(stationLocations);
		});
	}

}
