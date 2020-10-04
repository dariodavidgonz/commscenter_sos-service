package com.commscenter.topsecret.location;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SecretPointUtilsTest {

	@Test
	void calculateDistanceBetweenPointsNoDistance() {
		SecretPoint a = new SecretPoint(0D,0D);
		SecretPoint b = new SecretPoint(0D,0D);
		assertEquals(0D, SecretPointService.calculateDistanceBetweenPoints(a, b));
	}
	
	@Test
	void calculateDistanceBetweenPointsOneUnitY() {
		SecretPoint a = new SecretPoint(0D,1D);
		SecretPoint b = new SecretPoint(0D,0D);
		assertEquals(1D, SecretPointService.calculateDistanceBetweenPoints(a, b));
	}
	
	@Test
	void calculateDistanceBetweenPointsOneUnitX() {
		SecretPoint a = new SecretPoint(0D,0D);
		SecretPoint b = new SecretPoint(1D,0D);
		assertEquals(1D, SecretPointService.calculateDistanceBetweenPoints(a, b));
	}
	
	@Test
	void calculateDistanceBetweenPointsAlmostExact() {
		SecretPoint a = new SecretPoint(-7D,-4D);
		SecretPoint b = new SecretPoint(17D,6.5D);
		assertEquals(26.196374, SecretPointService.calculateDistanceBetweenPoints(a, b), 0.01);
	}
	
}
