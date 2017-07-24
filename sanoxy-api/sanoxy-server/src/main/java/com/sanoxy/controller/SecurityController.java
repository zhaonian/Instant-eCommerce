
package com.sanoxy.controller;

import com.sanoxy.controller.request.ValidatedIdentifiedRequest;
import com.sanoxy.service.SecurityService;
import com.sanoxy.service.exception.InvalidRequestException;
import com.sanoxy.service.util.Permission;
import java.util.Set;
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
        public Set<Permission> getAllPermissions() {
                return securityService.getAllPermissions();
        }
        
        @RequestMapping(value = {"/view_current_permissions", ""}, method = RequestMethod.POST)
        @ResponseBody
        public Set<Permission> viewCurrentPermission(@RequestBody ValidatedIdentifiedRequest request) throws InvalidRequestException {
                request.validate();
                return securityService.getPermissions(request.getUserIdentity());
        }
        
        @RequestMapping(value = {"/view_user_permissions/{workspaceId}/{userId}", ""}, method = RequestMethod.POST)
        @ResponseBody
        public Set<Permission> viewUserPermission(@PathVariable("workspaceId") Integer workspaceId,
                                                        @PathVariable("userId") Integer userId,
                                                        @RequestBody ValidatedIdentifiedRequest request) throws InvalidRequestException {
                request.validate();
                if (workspaceId == null)
                        throw new InvalidRequestException("workspaceId is missing");
                if (userId == null)
                        throw new InvalidRequestException("userId is missing");
                return securityService.getPermissions(workspaceId, userId);
        }
}
