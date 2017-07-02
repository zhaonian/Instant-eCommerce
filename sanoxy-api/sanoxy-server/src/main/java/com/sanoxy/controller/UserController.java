package com.sanoxy.controller;

import com.sanoxy.controller.request.user.CreateUserRequest;
import com.sanoxy.controller.request.user.LogInRequest;
import com.sanoxy.controller.request.user.LogoutRequest;
import com.sanoxy.controller.response.DBName;
import com.sanoxy.controller.response.Identity;
import com.sanoxy.controller.response.Response;
import com.sanoxy.controller.response.Response.Status;
import com.sanoxy.controller.service.exception.DuplicatedUserException;
import com.sanoxy.controller.service.exception.InvalidRequestException;
import com.sanoxy.controller.service.exception.UserNotExistException;
import com.sanoxy.dao.user.User;
import com.sanoxy.repository.user.UserRepository;
import com.sanoxy.service.Session;
import javax.naming.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
        public Identity createUser(@RequestBody CreateUserRequest request) throws InvalidRequestException, DuplicatedUserException, UserNotExistException, AuthenticationException {
                if (!request.isValid()) {
                        throw new InvalidRequestException();
                }
                if (userRepository.existsByName(request.getUsername())) {
                        throw new DuplicatedUserException();
                }
                User user = request.asUser();
                userRepository.save(user);
                return logIn(new LogInRequest(request.getUsername(), request.getPassword()));
        }

        /*
	 * Log the user in the session
         */
        @RequestMapping(value = {"/login", ""}, method = RequestMethod.POST)
        @ResponseBody
        public Identity logIn(@RequestBody LogInRequest request) throws InvalidRequestException, UserNotExistException, AuthenticationException {
                if (!request.isValid()) {
                        throw new InvalidRequestException();
                }
                User user = userRepository.findByName(request.getUsername());
                if (user == null) {
                        throw new UserNotExistException();
                }
                if (!user.getSalt().equals(request.getPassword())) {
                        throw new AuthenticationException("Password does not match");
                }
                Identity id = new Identity(true);
                Session.setUser(id.getId(), user);
                return id;
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
        @RequestMapping(value = {"/connection/{db_name}", ""}, method = RequestMethod.GET)
        @ResponseBody
        public DBName validateConnection(@PathVariable("db_name") String db_name) {
                return new DBName(db_name);
        }
}
