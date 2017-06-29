package com.sanoxy.controller.response;

import com.sanoxy.dao.user.User;

public class UserId {
	
	private String Id;
	
	public UserId() {};
	
	public UserId(User user) {
		this.Id = user.getId();
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}
}
