package com.commscenter.sos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commscenter.topsecret.location.LocationNotResolvedException;
import com.commscenter.topsecret.location.SecretPoint;
import com.commscenter.topsecret.location.Trilateration2DLocationResolver;

class Trilateration2DLocationResolverTest {

	static Logger logger = LoggerFactory.getLogger(Trilateration2DLocationResolverTest.class);

	final double deltaPrecision = 0.5;

	@Test
	void testCorrectWithoutDelta() {
		Trilateration2DLocationResolver resolver = new Trilateration2DLocationResolver();
		List<Double> aux = new ArrayList<Double>();
		aux.add(316.22776601683796);
		aux.add(300D);
		aux.add(728.0109889280518D);
		SecretPoint aux1 = resolver.getLocation(aux);
		assertEquals(-200.0D, aux1.getX());
		assertEquals(-100.0D, aux1.getY());
	}

	@Test
	void testCorrectWithDelta() {
		Trilateration2DLocationResolver resolver = new Trilateration2DLocationResolver();
		List<Double> aux = new ArrayList<Double>();
		aux.add(815.6501532519932);
		aux.add(934.2136653357196);
		aux.add(1125.5643795447686);
		SecretPoint aux1 = resolver.getLocation(aux);
		assertEquals(-500.50D, aux1.getX(), deltaPrecision);
		assertEquals(615.34D, aux1.getY(), deltaPrecision);
	}

	@Test
	void testLocationNotResolvedException() {
		Trilateration2DLocationResolver resolver = new Trilateration2DLocationResolver();
		List<Double> aux = new ArrayList<Double>();
		aux.add(100D);
		aux.add(115.5D);
		aux.add(142.7D);
		assertThrows(LocationNotResolvedException.class, () -> {
			resolver.getLocation(aux);
		});
	}

}
