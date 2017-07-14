package com.sanoxy.controller;

import com.sanoxy.controller.request.user.CreateUserRequest;
import com.sanoxy.controller.request.user.LogInRequest;
import com.sanoxy.controller.request.user.LogoutRequest;
import com.sanoxy.controller.response.DatabaseConnectionResponse;
import com.sanoxy.controller.response.Response;
import com.sanoxy.controller.response.Response.Status;
import com.sanoxy.controller.response.UserIdentityResponse;
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
        public Response createUser(@RequestBody CreateUserRequest request) throws InvalidRequestException, 
                                                                                              DuplicatedUserException, 
                                                                                              UserNotExistException, 
                                                                                              AuthenticationException {
                request.validate();
                
                if (userRepository.existsByName(request.getUsername()))
                        throw new DuplicatedUserException();
                
                User user = request.asUser();
                userRepository.save(user);
                return new Response(Status.Success);
        }

        /*
	 * Log the user in the session
         */
        @RequestMapping(value = {"/login/{db_name}", ""}, method = RequestMethod.POST)
        @ResponseBody
        public UserIdentityResponse logIn(@RequestBody LogInRequest request, 
                                          @PathVariable("db_name") String db_name) throws InvalidRequestException, 
                                                                                    UserNotExistException, 
                                                                                    AuthenticationException {
                request.validate();
                
                User user = userRepository.findByName(request.getUsername());
                if (user == null)
                        throw new UserNotExistException();
                
                if (!user.getSalt().equals(request.getPassword())) 
                        throw new AuthenticationException("Password does not match");
                
                UserIdentityResponse response = new UserIdentityResponse(0);
                Session.setUser(response.getUid(), user);
                return response;
        }

        /*
	 * Log the user out of the session
         */
        @RequestMapping(value = {"/logout", ""}, method = RequestMethod.POST)
        @ResponseBody
        public Response logout(@RequestBody LogoutRequest request) throws InvalidRequestException {
                request.validate();
                
                Session.removeUser(request.getIdentity().getUid());
                return new Response(Status.Success);
        }

        /*
	 * Return sanoxy directly for now
	 * TODO: Each user will have its own database in the future
         */
        @RequestMapping(value = {"/connection/{db_name}", ""}, method = RequestMethod.GET)
        @ResponseBody
        public DatabaseConnectionResponse validateConnection(@PathVariable("db_name") String db_name) {
                return new DatabaseConnectionResponse(0);
        }
}
