package com.commscenter.topsecret.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.messaging.Message;

import com.commscenter.topsecret.location.SecretPoint;

class FunctionTests {

	private final FunctionConfiguration functions = new FunctionConfiguration();

	@Test
	void testGetLocation() {
		Message<SecretPoint> output = this.functions.getLocation().apply(new SOSSignal("0,0,0"));
		assertThat(output.getPayload().getX()).isEqualTo(0D);
		assertThat(output.getPayload().getY()).isEqualTo(0D);
	}

}