package com.trackandtrail.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CusDataIntegrityViolationException extends org.springframework.dao.DataIntegrityViolationException {
	private static final long serialVersionUID = 1L;

	public CusDataIntegrityViolationException(String message) {
		super(message);
	}

	public CusDataIntegrityViolationException(String message, Throwable cause) {
		super(message, cause);
	}
}
