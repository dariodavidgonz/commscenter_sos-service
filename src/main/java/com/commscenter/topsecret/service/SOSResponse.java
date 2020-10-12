package com.commscenter.topsecret.service;

import java.util.List;

import com.commscenter.topsecret.location.coordinate.SecretPoint;
import com.fasterxml.jackson.annotation.JsonInclude;

/**

* Response class of the SOSService
* 
* @author Dario Gonzalez

*/
@JsonInclude(JsonInclude.Include.NON_NULL) 
public class SOSResponse {

	private SecretPoint position;
	private String message;
	private List<String> errorMessages;
	
	
	public SOSResponse() {
		super();
	}


	public SOSResponse(SecretPoint position, String message, List<String> errorMessages) {
		super();
		this.position = position;
		this.message = message;
		this.errorMessages = errorMessages;
	}


	public List<String> getErrorMessages() {
		return errorMessages;
	}


	public void setErrorMessages(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}


	public SecretPoint getPosition() {
		return position;
	}


	public void setPosition(SecretPoint position) {
		this.position = position;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	

}
