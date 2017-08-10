
package com.sanoxy.controller.request.user;

import com.sanoxy.controller.request.ValidatedIdentifiedRequest;
import com.sanoxy.service.util.Permission;
import java.util.Set;


public class PermissionRequest extends ValidatedIdentifiedRequest {
        
        private Set<Permission> permissions;
        
        public PermissionRequest() {
        }
        
        public PermissionRequest(Set<Permission> permissions) {
                this.permissions = permissions;
        }
        
        public Set<Permission> getPermissions() {
                return permissions;
        }
        
        public void setPermissions(Set<Permission> permissions) {
                this.permissions = permissions;
        }
}
