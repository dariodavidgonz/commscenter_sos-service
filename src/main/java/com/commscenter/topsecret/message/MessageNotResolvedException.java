package com.commscenter.topsecret.message;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Could not resolve message.")
public class MessageNotResolvedException extends RuntimeException {

	private static final long serialVersionUID = 3506116128695013679L;

	public MessageNotResolvedException() {
		super();
	}
}