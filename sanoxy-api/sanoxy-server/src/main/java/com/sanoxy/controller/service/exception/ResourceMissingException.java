package com.sanoxy.controller.service.exception;

public class ResourceMissingException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String message = "Resouce does not exist in db";
	
	public ResourceMissingException() { }
	
	public ResourceMissingException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
