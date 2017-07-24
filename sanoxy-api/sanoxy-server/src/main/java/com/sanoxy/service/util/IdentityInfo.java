
package com.sanoxy.service.util;

import com.sanoxy.dao.user.User;
import com.sanoxy.dao.user.Workspace;
import java.util.Set;


public class IdentityInfo {
        
        private String rawPasscode;
        private User user;
        private Workspace workspace;
        private Set<Permission> permissions;
        
        public IdentityInfo() {
        }
        
        public IdentityInfo(User user, String rawPasscode, Workspace workspace, Set<Permission> permissions) {
                this.user = user;
                this.rawPasscode = rawPasscode;
                this.workspace = workspace;
                this.permissions = permissions;
        }
        
        public String getRawPasscode() {
                return this.rawPasscode;
        }
        
        public void setRawPasscode(String rawPasscode) {
                this.rawPasscode = rawPasscode;
        }
        
        public User getUser() {
                return this.user;
        }
        
        public void setUser(User user) {
                this.user = user;
        }
        
        public Workspace getWorkspace() {
                return this.workspace;
        }
        
        public void setWorkspace(Workspace workspace) {
                this.workspace = workspace;
        }
        
        public Set<Permission> getPermissions() {
                return this.permissions;
        }
        
        public void setPermissions(Set<Permission> permissions) {
                this.permissions = permissions;
        }
}
