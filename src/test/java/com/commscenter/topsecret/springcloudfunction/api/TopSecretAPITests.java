package com.commscenter.topsecret.springcloudfunction.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@SpringBootTest(classes = TopSecretFunctionApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TopSecretAPITests {

	@Autowired
	private TestRestTemplate rest;

	@Test
	void testInternalServer() throws Exception {
		ResponseEntity<String> result = this.rest.exchange(RequestEntity.post(new URI("/topsecret")).body("hello"),
				String.class);
		final ObjectNode node = new ObjectMapper().readValue(result.getBody(), ObjectNode.class);
		assertTrue(node.has("status"));
		assertEquals("500", node.get("status").toString());
	}
	
	@Test
	void testBadRequest() throws Exception {
		ResponseEntity<String> result = this.rest.exchange(RequestEntity.post(new URI("/topsecret")).body("{\"satellites\": [{\"na\": \"skywalker\",\"distance\": 316.22776601683796,\"message\": [\"este\", \"\", \"\", \"mensaje\", \"\"]},{\"name\": \"kenobi\",\"distance\": 300,\"message\": [\"\", \"es\", \"\", \"\", \"\"]},{\"name\": \"sato\",\"distance\": 728.0109889280518,\"message\": [\"este\", \"\", \"un\", \"\", \"\"]}]}"),
				String.class);
		final ObjectNode node = new ObjectMapper().readValue(result.getBody(), ObjectNode.class);
		assertTrue(node.has("status"));
		assertEquals("400", node.get("status").toString());
	}
	
	@Test
	void testNotResolved() throws Exception {
		ResponseEntity<String> result = this.rest.exchange(RequestEntity.post(new URI("/topsecret")).body("{\"satellites\": [{\"name\": \"skywalker\",\"distance\": 6.22776601683796,\"message\": [\"este\", \"\", \"\", \"mensaje\", \"\"]},{\"name\": \"kenobi\",\"distance\": 300,\"message\": [\"\", \"es\", \"\", \"\", \"\"]},{\"name\": \"sato\",\"distance\": 728.0109889280518,\"message\": [\"este\", \"\", \"un\", \"\", \"\"]}]}"),
				String.class);
		final ObjectNode node = new ObjectMapper().readValue(result.getBody(), ObjectNode.class);
		assertTrue(node.has("status"));
		assertEquals("404", node.get("status").toString());
	}
	
	@Test
	void testNotResolvedMessage() throws Exception {
		ResponseEntity<String> result = this.rest.exchange(RequestEntity.post(new URI("/topsecret")).body("{\"satellites\": [{\"name\": \"skywalker\",\"distance\": 316.22776601683796,\"message\": [\"este\", \"\", \"\", \"mensaje\", \"\"]},{\"name\": \"kenobi\",\"distance\": 300,\"message\": [\"c\", \"es\", \"\", \"\", \"\"]},{\"name\": \"sato\",\"distance\": 728.0109889280518,\"message\": [\"este\", \"\", \"un\", \"\", \"\"]}]}"),
				String.class);
		final ObjectNode node = new ObjectMapper().readValue(result.getBody(), ObjectNode.class);
		assertTrue(node.has("status"));
		assertEquals("404", node.get("status").toString());
	}
	
	
	@Test
	void testResolution() throws Exception {
		ResponseEntity<String> result = this.rest.exchange(RequestEntity.post(new URI("/topsecret")).body("{\"satellites\": [{\"name\": \"skywalker\",\"distance\": 316.22776601683796,\"message\": [\"este\", \"\", \"\", \"mensaje\", \"\"]},{\"name\": \"kenobi\",\"distance\": 300,\"message\": [\"\", \"es\", \"\", \"\", \"\"]},{\"name\": \"sato\",\"distance\": 728.0109889280518,\"message\": [\"este\", \"\", \"un\", \"\", \"\"]}]}"),
				String.class);
		final ObjectNode node = new ObjectMapper().readValue(result.getBody(), ObjectNode.class);
		assertTrue(node.has("message"));
		assertEquals("\"este es un mensaje\"", node.get("message").toString());
		assertTrue(node.has("position"));
		assertEquals(-200D, node.get("position").get("x").asDouble());
		assertEquals(-100D, node.get("position").get("y").asDouble());
	}
	
}