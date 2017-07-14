package com.sanoxy.controller.request.user;


import com.sanoxy.controller.request.ValidatedRequest;
import com.sanoxy.controller.service.exception.InvalidRequestException;
import com.sanoxy.dao.user.User;

public class CreateUserRequest implements ValidatedRequest {
	
	private String username;
	private String password;
	
	public User asUser() {
		User user = new User();
		user.setName(username);
		user.setPermission(1);
		user.setSalt(password);
		return user;
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
	public void setUsername(String userName) {
		this.username = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
