package com.sanoxy.controller;

import com.sanoxy.controller.request.user.CreateUserRequest;
import com.sanoxy.controller.request.user.LogInRequest;
import com.sanoxy.controller.request.user.LogoutRequest;
import com.sanoxy.controller.response.Response;
import com.sanoxy.controller.response.Response.Status;
import com.sanoxy.service.UserService;
import com.sanoxy.service.exception.DuplicatedUserException;
import com.sanoxy.service.exception.InvalidRequestException;
import com.sanoxy.service.exception.PermissionDeniedException;
import com.sanoxy.service.exception.UserNotExistException;
import com.sanoxy.service.util.UserIdentity;
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
        private UserService userService;

        /*
	 * Create a new user in the db
         */
        @RequestMapping(value = {"/create", ""}, method = RequestMethod.POST)
        @ResponseBody
        public Response createUser(@RequestBody CreateUserRequest request) throws InvalidRequestException, 
                                                                                  DuplicatedUserException, 
                                                                                  PermissionDeniedException,
                                                                                  UserNotExistException, 
                                                                                  AuthenticationException {
                request.validate();
                userService.createNew(request.getUsername(), request.getPassword());
                return new Response(Status.Success);
        }

        /*
	 * Log the user in the session
         */
        @RequestMapping(value = {"/login/{workspace}", ""}, method = RequestMethod.POST)
        @ResponseBody
        public UserIdentity logIn(@RequestBody LogInRequest request, 
                                          @PathVariable("workspace") String workspace) throws InvalidRequestException, 
                                                                                    UserNotExistException, 
                                                                                    AuthenticationException {
                request.validate();
                if (workspace == null)
                        throw new InvalidRequestException("workspace name is missing");
                UserIdentity identity = userService.authenticate(workspace, request.getUsername(), request.getPassword());
                return identity;
        }

        /*
	 * Log the user out of the session
         */
        @RequestMapping(value = {"/logout", ""}, method = RequestMethod.POST)
        @ResponseBody
        public Response logout(@RequestBody LogoutRequest request) throws InvalidRequestException {
                request.validate();
                userService.logout(request.getUserIdentity());
                return new Response(Status.Success);
        }
}
