package com.commscenter.topsecret.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.commscenter.topsecret.location.RebelSatellite;
import com.commscenter.topsecret.location.Satellite;
import com.commscenter.topsecret.location.SecretPoint;

@Component
public class InMemorySatelliteService implements SatelliteService {

	public List<Satellite> getSatellites() {
		List<Satellite> satellites = new ArrayList<>();
		satellites.add(new RebelSatellite(1, "Kenobi", new SecretPoint(-500D, -200D)));
		satellites.add(new RebelSatellite(2, "Skywalker", new SecretPoint(100D, -100D)));
		satellites.add(new RebelSatellite(3, "Sato", new SecretPoint(500D, 100D)));
		return satellites;
	}

}
