
package com.sanoxy.controller;

import com.sanoxy.controller.request.ValidatedIdentifiedRequest;
import com.sanoxy.controller.request.user.PermissionRequest;
import com.sanoxy.controller.response.IdentityInfoResponse;
import com.sanoxy.controller.response.Response;
import com.sanoxy.controller.response.Response.Status;
import com.sanoxy.service.SecurityService;
import com.sanoxy.service.WorkspaceService;
import com.sanoxy.service.exception.DuplicatedWorkspaceException;
import com.sanoxy.service.exception.InvalidRequestException;
import com.sanoxy.service.exception.PermissionDeniedException;
import com.sanoxy.service.util.UserPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "api/workspace", produces = {MediaType.APPLICATION_JSON_VALUE})
public class WorkspaceController {
        
        @Autowired
        SecurityService securityService;
        
        @Autowired
        WorkspaceService workspaceService;
        
        @RequestMapping(value = {"/create/{workspaceName}", ""}, method = RequestMethod.GET)
        @ResponseBody
        public IdentityInfoResponse createNewWorkspace(@PathVariable("workspaceName") String workspaceName,
                                                       @RequestBody ValidatedIdentifiedRequest request) throws InvalidRequestException, 
                                                                                                               PermissionDeniedException,
                                                                                                               DuplicatedWorkspaceException {
                request.validate();
                securityService.requirePermission(request.getUserIdentity(), UserPermission.CreateWorkspace.getPermission());
                return new IdentityInfoResponse(workspaceService.createNewWorkspace(workspaceName));
        }
        
        @RequestMapping(value = {"/add/{workspaceId}/{userId}", ""}, method = RequestMethod.GET)
        @ResponseBody
        public Response addToWorkspace(@PathVariable("workspaceId") Integer workspaceId,
                                       @PathVariable("userId") Integer userId,
                                       @RequestBody PermissionRequest request) throws InvalidRequestException, 
                                                                                               PermissionDeniedException,
                                                                                               DuplicatedWorkspaceException {
                request.validate();
                securityService.requirePermission(request.getUserIdentity(), UserPermission.AddUserToWorkspace.getPermission());
                workspaceService.addUserToWorkspace(workspaceId, userId, request.getPermissions());
                return new Response(Status.Success);
        }
}
