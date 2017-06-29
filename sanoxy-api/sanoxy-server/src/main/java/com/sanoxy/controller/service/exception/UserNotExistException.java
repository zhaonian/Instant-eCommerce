package com.sanoxy.controller.service.exception;

public class UserNotExistException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String message = "User does not exist";
	
	public UserNotExistException() { }
	
	@Override
	public String getMessage() {
		return message;
	}
}
