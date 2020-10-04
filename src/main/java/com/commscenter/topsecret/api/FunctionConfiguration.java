package com.commscenter.topsecret.api;

import java.util.function.Function;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import com.commscenter.topsecret.location.SecretPoint;

@SpringBootApplication
public class FunctionConfiguration {

	/*
	 * Main method for executing locally. Not used in Production.
	 */
	public static void main(String[] args) {
		SpringApplication.run(FunctionConfiguration.class, args);
	}

	@Bean
	public Function<SOSSignal, Message<SecretPoint>> getLocation() {
		return value -> getLocation(value);

	}

	private Message<SecretPoint> getLocation(SOSSignal value) {
		return MessageBuilder.withPayload(new SecretPoint(0D, 0D)).build();
	}
}