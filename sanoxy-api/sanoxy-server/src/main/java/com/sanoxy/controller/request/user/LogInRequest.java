package com.sanoxy.controller.request.user;

import com.sanoxy.controller.request.ValidatedRequest;
import com.sanoxy.controller.service.exception.InvalidRequestException;

public class LogInRequest implements ValidatedRequest {
	
	private String username;
	private String password;
	
	@Override
	public boolean isValid() throws InvalidRequestException {
		if (username == null || username.isEmpty()) {
			throw new InvalidRequestException("user name is invalid");
		}
		if (password == null || password.isEmpty()) {
			throw new InvalidRequestException("password is invalid");
		}
		return true;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
