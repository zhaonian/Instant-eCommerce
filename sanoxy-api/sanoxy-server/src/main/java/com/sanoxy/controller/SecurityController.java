
package com.sanoxy.controller;

import com.sanoxy.controller.request.ValidatedIdentifiedRequest;
import com.sanoxy.controller.response.PermissionSetResponse;
import com.sanoxy.service.SecurityService;
import com.sanoxy.service.exception.InvalidRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "api/security", produces = {MediaType.APPLICATION_JSON_VALUE})
public class SecurityController {
        
        @Autowired
        SecurityService securityService;
        
        @RequestMapping(value = {"/get_all_permissons", ""}, method = RequestMethod.GET)
        @ResponseBody
        public PermissionSetResponse getAllPermissions() {
                return new PermissionSetResponse(securityService.getAllPermissions());
        }
        
        @RequestMapping(value = {"/view_current_permissions", ""}, method = RequestMethod.POST)
        @ResponseBody
        public PermissionSetResponse viewCurrentPermission(@RequestBody ValidatedIdentifiedRequest request) throws InvalidRequestException {
                request.validate();
                return new PermissionSetResponse(securityService.getPermissions(request.getUserIdentity()));
        }
        
        @RequestMapping(value = {"/view_user_permissions/{workspaceId}/{userId}", ""}, method = RequestMethod.POST)
        @ResponseBody
        public PermissionSetResponse viewUserPermission(@PathVariable("workspaceId") Integer workspaceId,
                                                        @PathVariable("userId") Integer userId,
                                                        @RequestBody ValidatedIdentifiedRequest request) throws InvalidRequestException {
                request.validate();
                return new PermissionSetResponse(securityService.getPermissions(workspaceId, userId));
        }
}
