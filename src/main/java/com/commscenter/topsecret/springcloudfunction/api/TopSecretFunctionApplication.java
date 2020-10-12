package com.commscenter.topsecret.springcloudfunction.api;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.commscenter.topsecret.satellite.SatelliteSOS;
import com.commscenter.topsecret.service.SOSResponse;
import com.commscenter.topsecret.service.SOSService;
import com.commscenter.topsecret.service.SecretMessage;

/**
 * Spring Cloud Function implementation  
 * 
 * Each function represents and endpoint.
 * 
 * @author Dario Gonzalez
 */
@SpringBootApplication
public class TopSecretFunctionApplication {

	@Autowired
	private SOSService sosService;

	/*
	 * Main method class for SpringBoot.
	 * 
	 */
	public static void main(String[] args) {
		SpringApplication.run(TopSecretFunctionApplication.class, args);
	}

	/**
	 * TopSecret request of an SOS message which retrieves SOS calculated information
	 * 
	 */
	@Bean
	public Function<SecretMessage, SOSResponse> topsecret() {
		return request -> sosService.resolveSOS(request);
	}
	
	/**
	 * 
	 * This function is prepared for creating or retrieving TopSecret information
	 * 
	 */
	@Bean
	public Function<SatelliteSOS, SOSResponse> topsecret_split() {
		return request -> applyOperation(request);
	}


	/**
	 * 
	 * We apply the creation operation if the request has body,
	 * otherwise we retrieve the SOS information
	 * 
	 * Note: This routing is a limitation of spring data function,
	 * as this function represents an unique URL
	 * 
     * @returns response of the operation
	 */
	private SOSResponse applyOperation(SatelliteSOS request) {
		if (!isEmptyRequestBody(request)) {
			return sosService.receiveSplitSOSMessagePart(request);		
		} else {
			return sosService.resolveSplitSatelliteSOS();
		}
	}

	/**
	 *  This method verifies if a SatelliteSOS has information on its body
	 * 
	 * @param request the request to validate
	 * @returns true if the request is empty
	 * 
	 */
	private boolean isEmptyRequestBody(SatelliteSOS request) {
		return request == null || request.getName() == null;
	}

}