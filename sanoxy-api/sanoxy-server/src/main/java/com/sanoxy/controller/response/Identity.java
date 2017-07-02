package com.sanoxy.controller.response;

import java.util.UUID;

public class Identity {
	
	private String Id;
	
        public Identity() {
	}
        
	public Identity(boolean uuid) {
		this.Id = UUID.randomUUID().toString();
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}
}
