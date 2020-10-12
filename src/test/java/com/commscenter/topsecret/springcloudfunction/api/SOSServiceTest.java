package com.commscenter.topsecret.springcloudfunction.api;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.commscenter.topsecret.location.coordinate.SecretPoint;
import com.commscenter.topsecret.location.resolver.LocationResolver;
import com.commscenter.topsecret.service.SOSService;

@SpringBootTest
class SOSServiceTest {

	@Autowired
	private SOSService simpleSOSService;

	@MockBean
	private LocationResolver locationResolver;

	@Test
	void testGetLocationOkey() {
		Mockito.when(locationResolver.getLocation(ArgumentMatchers.any())).thenReturn(new SecretPoint(2D, 3D));
		List<Double> aux = new ArrayList<Double>();
		aux.add(0D);
		aux.add(0D);
		aux.add(0D);
		Assertions.assertEquals(2D, simpleSOSService.getLocation(aux).getX());
		Assertions.assertEquals(3D, simpleSOSService.getLocation(aux).getY());
	}
	
	@Test
	void testGetLocationFail() {
		Mockito.when(locationResolver.getLocation(ArgumentMatchers.any())).thenReturn(new SecretPoint(2D, 3D));
		List<Double> aux = new ArrayList<Double>();
		aux.add(0D);
		aux.add(0D);
		aux.add(0D);
		Assertions.assertNotEquals(3D, simpleSOSService.getLocation(aux).getX());
		Assertions.assertNotEquals(2D, simpleSOSService.getLocation(aux).getY());
	}

}