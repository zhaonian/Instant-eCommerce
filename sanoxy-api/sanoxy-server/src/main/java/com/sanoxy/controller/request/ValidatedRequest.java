package com.sanoxy.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sanoxy.controller.service.exception.InvalidRequestException;

public interface ValidatedRequest {
	
	@JsonIgnore
	public boolean isValid() throws InvalidRequestException;
}