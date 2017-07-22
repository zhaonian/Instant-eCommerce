
package com.sanoxy.controller;

import com.sanoxy.controller.request.ValidatedIdentifiedRequest;
import com.sanoxy.controller.request.user.PermissionRequest;
import com.sanoxy.controller.response.Response;
import com.sanoxy.controller.response.Response.Status;
import com.sanoxy.dao.user.User;
import com.sanoxy.service.SecurityService;
import com.sanoxy.service.WorkspaceService;
import com.sanoxy.service.exception.DuplicatedWorkspaceException;
import com.sanoxy.service.exception.InvalidRequestException;
import com.sanoxy.service.exception.PermissionDeniedException;
import com.sanoxy.service.util.IdentityInfo;
import com.sanoxy.service.util.UserPermission;
import java.util.List;
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
        
        @RequestMapping(value = {"/create/{workspaceName}", ""}, method = RequestMethod.POST)
        @ResponseBody
        public IdentityInfo createNewWorkspace(@PathVariable("workspaceName") String workspaceName,
                                                       @RequestBody ValidatedIdentifiedRequest request) throws InvalidRequestException, 
                                                                                                               PermissionDeniedException,
                                                                                                               DuplicatedWorkspaceException {
                request.validate();
                securityService.requirePermission(request.getUserIdentity(), UserPermission.CreateWorkspace.getPermission());
                return workspaceService.createNewWorkspace(workspaceName);
        }
        
        @RequestMapping(value = {"/delete/{workspaceId}", ""}, method = RequestMethod.POST)
        @ResponseBody
        public Response deleteWorkspace(@PathVariable("workspaceId") Integer workspaceId,
                                       @RequestBody ValidatedIdentifiedRequest request) throws InvalidRequestException, 
                                                                                               PermissionDeniedException,
                                                                                               DuplicatedWorkspaceException {
                request.validate();
                securityService.requirePermission(request.getUserIdentity(), UserPermission.DeleteWorkspace.getPermission());
                workspaceService.deleteWorkspace(workspaceId);
                return new Response(Status.Success);
        }
        
        @RequestMapping(value = {"/user/add/{workspaceId}/{userId}", ""}, method = RequestMethod.POST)
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
        
        @RequestMapping(value = {"/user/get/{workspaceId}", ""}, method = RequestMethod.GET)
        @ResponseBody
        public List<User> getWorkspaceUsers(@PathVariable("workspaceId") Integer workspaceId,
                                            @RequestBody ValidatedIdentifiedRequest request) throws InvalidRequestException, 
                                                                                               PermissionDeniedException,
                                                                                               DuplicatedWorkspaceException {
                request.validate();
                securityService.requirePermission(request.getUserIdentity(), UserPermission.AccessWorkspace.getPermission());
                return workspaceService.getWorkspaceUsers(workspaceId);
        }
        
        @RequestMapping(value = {"/user/remove/{workspaceId}/{userId}", ""}, method = RequestMethod.POST)
        @ResponseBody
        public Response removeFromWorkspace(@PathVariable("workspaceId") Integer workspaceId,
                                              @PathVariable("userId") Integer userId,
                                              @RequestBody ValidatedIdentifiedRequest request) throws InvalidRequestException, 
                                                                                               PermissionDeniedException,
                                                                                               DuplicatedWorkspaceException {
                request.validate();
                securityService.requirePermission(request.getUserIdentity(), UserPermission.RemoveUserFromWorkspace.getPermission());
                workspaceService.removeUserToWorkspace(workspaceId, userId);
                return new Response(Status.Success);
        }
        
        @RequestMapping(value = {"/user/permission/{workspaceId}/{userId}", ""}, method = RequestMethod.POST)
        @ResponseBody
        public Response changeUserPermission(@PathVariable("workspaceId") Integer workspaceId,
                                             @PathVariable("userId") Integer userId,
                                             @RequestBody PermissionRequest request) throws InvalidRequestException, 
                                                                                               PermissionDeniedException,
                                                                                               DuplicatedWorkspaceException {
                request.validate();
                securityService.requirePermission(request.getUserIdentity(), UserPermission.ChangeUserPermissions.getPermission());
                workspaceService.changeUserWorkspacePermission(workspaceId, userId, request.getPermissions());
                return new Response(Status.Success);
        }
}
