package com.sanoxy.service.exception;

public class UserNotExistException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private final String message;
	
	public UserNotExistException() { 
                this.message = "User does not exist";
        }

        public UserNotExistException(String message) {
                this.message = message;
        }
	
	@Override
	public String getMessage() {
		return message;
	}
}
