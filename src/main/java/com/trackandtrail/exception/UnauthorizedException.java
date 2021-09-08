package com.trackandtrail.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public final class UnauthorizedException extends RuntimeException{
//data invalid
	private static final long serialVersionUID = 1L;
	
	public UnauthorizedException() {
		
		super(); //Runtime Exception super class 
	} 
	
	public UnauthorizedException(String message){
		super(message);
	}
	
	public UnauthorizedException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
