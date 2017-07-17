
package com.sanoxy.controller.response;

import com.sanoxy.service.util.Permission;
import java.util.Set;


public class PermissionSetResponse extends Response  {
        
        private Set<Permission> permissions;
        
        public PermissionSetResponse() {
        }
        
        public PermissionSetResponse(Set<Permission> permissions) {
                this.permissions = permissions;
        }
        
        public Set<Permission> getPermissions() {
                return permissions;
        }
        
        public void setPermissions(Set<Permission> permissions) {
                this.permissions = permissions;
        }
}
