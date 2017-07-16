
package com.sanoxy.service;

import com.sanoxy.service.exception.PermissionDeniedException;
import com.sanoxy.service.util.IdentityInfo;
import com.sanoxy.service.util.Permission;
import com.sanoxy.service.util.UserIdentity;
import com.sanoxy.service.util.UserPermission;
import com.sanoxy.service.util.WorkspacePermission;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author davis
 */
@Service
public class SecurityServiceImpl implements SecurityService {
        
        @Autowired
        WorkspaceService workspaceService;
        
        @Autowired
        UserService userService;
        
        @Autowired
        IdentitySessionService identitySessionService;

        @Override
        public void requirePermission(UserIdentity identity, Permission perm) throws PermissionDeniedException {
                IdentityInfo info = identitySessionService.getIdentityInfo(identity.getUid());
                
                if (UserPermission.isUserPermission(perm)) {
                        Set<Permission> permissions = 
                                info.getUser().getUserPermissions();
                } else if (WorkspacePermission.isWorkspacePermission(perm)) {        
                        Set<Permission> permissions = 
                                workspaceService.getUserWorkspacePermission(info.getWorkspace().getWid(), info.getUser().getUid());
                        if (!permissions.contains(perm))
                                throw new PermissionDeniedException(perm);
                } else {
                        throw new PermissionDeniedException(UserPermission.CreatePermissionRule.getPermission());
                }
        }
}
