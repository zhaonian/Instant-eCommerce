
package com.sanoxy.service.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanoxy.dao.user.User;
import com.sanoxy.dao.user.Workspace;
import java.io.IOException;
import java.util.Set;

/**
 * @author davis
 */
public class IdentityInfo {
        
        private final User user;
        private final Workspace workspace;
        private final Set<WorkspacePermission> permissions;
        
        public IdentityInfo(User user, Workspace workspace, String permissionsJson) throws IOException {
                this.user = user;
                this.workspace = workspace;
                
                ObjectMapper mapper = new ObjectMapper();
                this.permissions = mapper.readValue(permissionsJson, new TypeReference<Set<WorkspacePermission>>(){});
        }
        
        public User getUser() {
                return this.user;
        }
        
        public Workspace getWorkspace() {
                return this.workspace;
        }
        
        public Set<WorkspacePermission> getPermissions() {
                return this.permissions;
        }
}
