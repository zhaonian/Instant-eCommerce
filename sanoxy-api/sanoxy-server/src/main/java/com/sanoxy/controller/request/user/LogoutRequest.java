package com.sanoxy.controller.request.user;

import com.sanoxy.controller.request.ValidatedRequest;
import com.sanoxy.controller.service.exception.InvalidRequestException;

public class LogoutRequest implements ValidatedRequest {
	
	private String username;
	private String id;
	
	@Override
	public boolean isValid() throws InvalidRequestException {
		if (username == null || username.isEmpty()) {
			throw new InvalidRequestException("user name is invalid");
		}
		if (id == null || id.isEmpty()) {
			throw new InvalidRequestException("id is invalid");
		}
		return true;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
