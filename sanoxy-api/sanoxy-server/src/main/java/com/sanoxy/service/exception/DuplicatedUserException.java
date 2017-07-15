package com.sanoxy.service.exception;

public class DuplicatedUserException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String message = "Username already exists";
	
	public DuplicatedUserException() { }
	
	@Override
	public String getMessage() {
		return message;
	}
}
