package com.commscenter.topsecret.satellite.dao;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class DAOException  extends RuntimeException {

	private static final long serialVersionUID = 2753861183678076048L;

	public DAOException(String message) {
		super("(Data Error) " + message);
	}

}
