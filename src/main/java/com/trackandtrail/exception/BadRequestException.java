package com.trackandtrail.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public final class BadRequestException extends RuntimeException{
//data invalid
	private static final long serialVersionUID = 1L;
	
	public BadRequestException() {
		
		super(); //Runtime Exception super class 
	} 
	
	public BadRequestException(String message){
		super(message);
	}
	
	public BadRequestException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
