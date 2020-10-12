package com.commscenter.topsecret.springcloudfunction.api;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
	 * @author Dario Gonzalez
	 */
	@Bean
	public Function<SecretMessage, SOSResponse> topsecret() {
		return request -> sosService.resolveSOS(request);
	}
	

}