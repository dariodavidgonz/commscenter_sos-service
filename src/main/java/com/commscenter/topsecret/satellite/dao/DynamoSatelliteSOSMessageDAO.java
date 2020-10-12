package com.commscenter.topsecret.satellite.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.commscenter.topsecret.satellite.SatelliteSOS;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * Data access object for SOS information send from Satellites
 * 
 * @author Dario Gonzalez
 */
@Component
public class DynamoSatelliteSOSMessageDAO implements SatelliteSOSMessageDAO {

	private DynamoDB dynamoDb;
	private static final String TABLENAME = "satellite_data";
	private static final Regions REGION = Regions.US_EAST_2;

	public void saveOrUpdate(SatelliteSOS satellite) {
		this.initDynamoDbClient();
		persistData(satellite);
	}

	private PutItemOutcome persistData(SatelliteSOS satellite) {
		String message = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			message = mapper.writeValueAsString(satellite.getMessage());
		} catch (JsonProcessingException e) {
			throw new DAOException("Could not read satellite");
		}
		return this.dynamoDb.getTable(TABLENAME)
				.putItem(new PutItemSpec().withItem(new Item().withString("name", satellite.getName())
						.withDouble("distance", satellite.getDistance()).withString("message", message)));
	}

	public SatelliteSOS findOne(String satelliteName) {

		this.initDynamoDbClient();
		GetItemSpec spec = new GetItemSpec().withPrimaryKey("name", satelliteName);
		SatelliteSOS output;
		try {
			Item outcome = this.dynamoDb.getTable(TABLENAME).getItem(spec);
			List<String> messages = null;
			ObjectMapper mapper = new ObjectMapper();
			messages = readMessages(outcome, mapper);
			output =  new SatelliteSOS(satelliteName, outcome.getDouble("distance"), messages);

		} catch (Exception e) {
			throw new DAOException("Unable to read item: " + satelliteName);
		}
		return output;

	}

	private List<String> readMessages(Item outcome, ObjectMapper mapper) {
		List<String> messages = null;
		TypeReference<List<String>> tRef = new TypeReference<List<String>>() {};
			try {
				messages = mapper.readValue(outcome.getString("message"), tRef);
			} catch (JsonProcessingException e) {
				throw new DAOException("Could not read message information");
			}
	
		return messages;
	}

	private void initDynamoDbClient() {
		AmazonDynamoDBClient client = new AmazonDynamoDBClient();
		client.setRegion(Region.getRegion(REGION));
		this.dynamoDb = new DynamoDB(client);
	}
}