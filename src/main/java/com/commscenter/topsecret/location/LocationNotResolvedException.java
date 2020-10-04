package com.commscenter.topsecret.location;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Could not resolve location.")
public class LocationNotResolvedException extends RuntimeException {

	private static final long serialVersionUID = 2506116128695013679L;

	public LocationNotResolvedException() {
		super();
	}
}