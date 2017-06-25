package com.sanoxy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanoxy.controller.response.Response.Status;
import com.sanoxy.controller.service.exception.DuplicatedUserException;
import com.sanoxy.controller.service.exception.InvalidRequestException;
import com.sanoxy.dao.user.User;
import com.sanoxy.controller.request.user.CreateUserRequest;
import com.sanoxy.controller.response.Response;
import com.sanoxy.repository.UserRepository;

@Controller
@RequestMapping(value = "api/user", produces = { MediaType.APPLICATION_JSON_VALUE })
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = { "/create", "" }, method = RequestMethod.POST)
	@ResponseBody
	public Response createUser(@RequestBody CreateUserRequest request) throws InvalidRequestException, DuplicatedUserException {
		if (!request.isValid()) {
			throw new InvalidRequestException();
		}
		if (userRepository.existsByName(request.getUserName())) {
			throw new DuplicatedUserException();
		}
		User user = request.asUser();
		userRepository.save(user);
		return new Response(Status.Success);
	}
}
