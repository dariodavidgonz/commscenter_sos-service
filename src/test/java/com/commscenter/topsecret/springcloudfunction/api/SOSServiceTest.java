package com.commscenter.topsecret.springcloudfunction.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.commscenter.topsecret.location.coordinate.SecretPoint;
import com.commscenter.topsecret.location.coordinate.StationRelativeLocation;
import com.commscenter.topsecret.location.resolver.LocationResolver;
import com.commscenter.topsecret.message.MessageResolver;
import com.commscenter.topsecret.satellite.RebelSatellite;
import com.commscenter.topsecret.service.SOSService;

@SpringBootTest
class SOSServiceTest {

	@Autowired
	private SOSService simpleSOSService;

	@MockBean
	private LocationResolver locationResolver;
	@MockBean
	private MessageResolver messageResolver;

	@Test
	void testGetLocationWithMockService() {
		List<Double> aux = new ArrayList<Double>();
		aux.add(316.22776601683796D);
		aux.add(300D);
		aux.add(728.0109889280518D);
		List<StationRelativeLocation> stationLocations = new ArrayList<>();
		stationLocations.add(new StationRelativeLocation(new SecretPoint(-500D, -200D), 316.22776601683796D));
		stationLocations.add(new StationRelativeLocation(new SecretPoint(100D, -100D), 300D));
		stationLocations.add(new StationRelativeLocation(new SecretPoint(500D, 100D), 728.0109889280518D));
		Mockito.when(locationResolver.getLocation(ArgumentMatchers.eq(stationLocations)))
				.thenReturn(new SecretPoint(-200D, -100D));
		Assertions.assertEquals(-200D, simpleSOSService.getLocation(aux).getX());
		Assertions.assertEquals(-100D, simpleSOSService.getLocation(aux).getY());
	}

	@Test
	void testWithMockMessageResolver() {
		List<List<String>> lists = Lists.newArrayList(Arrays.asList("este", "", "", "mensaje"),
				Arrays.asList("este", "es", "un", ""), Arrays.asList("", "es", "un", ""));
		Mockito.when(messageResolver.getMessage((ArgumentMatchers.eq(lists)))).thenReturn("este es un mensaje");
		String result = simpleSOSService.getMessage(lists);
		assertEquals("este es un mensaje", result);
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