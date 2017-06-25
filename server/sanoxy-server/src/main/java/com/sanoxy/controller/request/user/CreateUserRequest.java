package com.sanoxy.controller.request.user;


import com.sanoxy.controller.request.ValidatedRequest;
import com.sanoxy.controller.service.exception.InvalidRequestException;
import com.sanoxy.dao.user.User;

public class CreateUserRequest implements ValidatedRequest {
	
	private String userName;
	private String password;
	
	public User asUser() {
		User user = new User();
		user.setId(5);
		user.setName(userName);
		user.setPermission(1);
		user.setSalt(password);
		return user;
	}
	
	@Override
	public boolean isValid() throws InvalidRequestException {
		if (userName == null || userName.isEmpty()) {
			throw new InvalidRequestException("user name is invalid");
		}
		if (password == null || password.isEmpty()) {
			throw new InvalidRequestException("password is invalid");
		}
		return true;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
