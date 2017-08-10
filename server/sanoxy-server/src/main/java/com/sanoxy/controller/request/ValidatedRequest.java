package com.sanoxy.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sanoxy.service.exception.InvalidRequestException;

public interface ValidatedRequest {
	
	@JsonIgnore
	public void validate() throws InvalidRequestException;
}