package com.sanoxy.controller.request.user;

import com.sanoxy.controller.request.ValidatedRequest;
import com.sanoxy.controller.service.exception.InvalidRequestException;

public class LogoutRequest implements ValidatedRequest {
        
	private String id;
        
        public LogoutRequest() {
                
        }
        
        public LogoutRequest(String id) {
                this.id = id;
        }
	
	@Override
	public boolean isValid() throws InvalidRequestException {
		if (id == null || id.isEmpty()) {
			throw new InvalidRequestException("id is invalid");
		}
		return true;
	}
        
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
