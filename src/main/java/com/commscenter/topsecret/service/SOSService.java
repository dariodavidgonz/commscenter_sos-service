package com.commscenter.topsecret.service;

import java.util.List;

import com.commscenter.topsecret.location.SecretPoint;

public interface SOSService {
	
	public SecretPoint getLocation(List<Double> distances);
	public String getMessage(List<List<String>> messages);

}
