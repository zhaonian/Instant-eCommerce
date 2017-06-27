package com.sanoxy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanoxy.controller.response.Response.Status;
import com.sanoxy.controller.response.UserId;
import com.sanoxy.controller.service.exception.DuplicatedUserException;
import com.sanoxy.controller.service.exception.InvalidRequestException;
import com.sanoxy.controller.service.exception.UserNotExistException;
import com.sanoxy.dao.user.User;
import com.sanoxy.repository.user.UserRepository;
import com.sanoxy.controller.request.user.CreateUserRequest;
import com.sanoxy.controller.request.user.LogInRequest;
import com.sanoxy.controller.request.user.LogoutRequest;
import com.sanoxy.controller.response.DBName;
import com.sanoxy.controller.response.Response;
import com.sanoxy.service.Session;

@Controller
@RequestMapping(value = "api/user", produces = {MediaType.APPLICATION_JSON_VALUE})
public class UserController {

        @Autowired
        private UserRepository userRepository;

        /*
	 * Create a new user in the db
         */
        @RequestMapping(value = {"/create", ""}, method = RequestMethod.POST)
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

        /*
	 * Log the user in the session
         */
        @RequestMapping(value = {"/login", ""}, method = RequestMethod.POST)
        @ResponseBody
        public UserId logIn(@RequestBody LogInRequest request) throws InvalidRequestException, UserNotExistException {
                if (!request.isValid()) {
                        throw new InvalidRequestException();
                }
                User user = userRepository.findByNameAndPassword(request.getUsername(), request.getPassword());
                if (user == null) {
                        throw new UserNotExistException();
                }
                Session.setUser(user.getId(), user);
                return new UserId(user);
        }

        /*
	 * Log the user out of the session
         */
        @RequestMapping(value = {"/logout", ""}, method = RequestMethod.POST)
        @ResponseBody
        public Response logout(@RequestBody LogoutRequest request) throws InvalidRequestException {
                if (!request.isValid()) {
                        throw new InvalidRequestException();
                }
                Session.removeUser(request.getId());
                return new Response(Status.Success);
        }

        /*
	 * Return sanoxy directly for now
	 * TODO: Each user will have its own database in the future
         */
        @RequestMapping(value = {"/connection", ""}, method = RequestMethod.GET)
        @ResponseBody
        public DBName validateConnection() {
                return new DBName("sanoxy");
        }
}
