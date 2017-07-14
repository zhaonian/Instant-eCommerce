package com.sanoxy.controller.request.user;

import com.sanoxy.controller.request.ValidatedRequest;
import com.sanoxy.controller.service.exception.InvalidRequestException;

public class LogInRequest implements ValidatedRequest {
	
	private String username;
	private String password;
        
        public LogInRequest()
        {
        }
        
        public LogInRequest(String username, String password)
        {
                this.username = username;
                this.password = password;
        }
	
	@Override
	public void validate() throws InvalidRequestException {
		if (username == null || username.isEmpty())
			throw new InvalidRequestException("user name is invalid");
		if (password == null || password.isEmpty())
			throw new InvalidRequestException("password is invalid");
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
