
package com.sanoxy.service.util;

import com.sanoxy.dao.user.User;
import com.sanoxy.dao.user.Workspace;
import java.util.Set;


public class IdentityInfo {
        
        private final User user;
        private final Workspace workspace;
        private final Set<Permission> permissions;
        
        public IdentityInfo(User user, Workspace workspace, Set<Permission> permissions) {
                this.user = user;
                this.workspace = workspace;
                
                this.permissions = permissions;
        }
        
        public User getUser() {
                return this.user;
        }
        
        public Workspace getWorkspace() {
                return this.workspace;
        }
        
        public Set<Permission> getPermissions() {
                return this.permissions;
        }
}
