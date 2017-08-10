package com.sanoxy.service.exception;

public class InvalidRequestException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String message = "Your request was invalid!";
	
	public InvalidRequestException() { }
	
	public InvalidRequestException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
