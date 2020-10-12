package com.commscenter.topsecret.springcloudfunction.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.commscenter.topsecret.location.coordinate.SecretPoint;
import com.commscenter.topsecret.satellite.SatelliteSOS;
import com.commscenter.topsecret.service.SOSResponse;
import com.commscenter.topsecret.service.SOSService;
import com.commscenter.topsecret.service.SecretMessage;

class FunctionTests {

	@InjectMocks
	private final TopSecretFunctionApplication functions = new TopSecretFunctionApplication();

	@Mock
	public SOSService sosService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		Mockito.when(sosService.resolveSOS(ArgumentMatchers.any()))
				.thenReturn(new SOSResponse(new SecretPoint(0D, 0D), "mock message"));
	}

	@Test
	void testFunctionResponseWithSOSService() {
		List<SatelliteSOS> satellites = new ArrayList<>();
		satellites.add(new SatelliteSOS("kenobi", 0D, Arrays.asList("mock", "message")));
		SOSResponse output = this.functions.topsecret().apply(new SecretMessage(satellites));
		assertEquals(0D, output.getPosition().getX());
		assertEquals(0D,  output.getPosition().getY());
		assertEquals("mock message", output.getMessage());
	}

}