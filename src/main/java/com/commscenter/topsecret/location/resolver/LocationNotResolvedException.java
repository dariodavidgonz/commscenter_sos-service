package com.commscenter.topsecret.location.resolver;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class LocationNotResolvedException extends RuntimeException {

	private static final long serialVersionUID = 2506116128695013679L;

	public LocationNotResolvedException() {
		super();
	}
}