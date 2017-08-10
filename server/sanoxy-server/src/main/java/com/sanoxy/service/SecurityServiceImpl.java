
package com.sanoxy.service;

import com.sanoxy.dao.user.Workspace;
import com.sanoxy.service.exception.PermissionDeniedException;
import com.sanoxy.service.exception.ResourceMissingException;
import com.sanoxy.service.util.IdentityInfo;
import com.sanoxy.service.util.Permission;
import com.sanoxy.service.util.UserIdentity;
import com.sanoxy.service.util.UserPermission;
import com.sanoxy.service.util.WorkspacePermission;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SecurityServiceImpl implements SecurityService {
        
        @Autowired
        WorkspaceService workspaceService;
        
        @Autowired
        UserService userService;
        
        @Autowired
        IdentitySessionService identitySessionService;
        
        private void testPermission(IdentityInfo info, Permission perm) throws PermissionDeniedException {
                Set<Permission> permissions = new HashSet();
                if (UserPermission.isUserPermission(perm)) {
                        permissions = info.getUser().getUserPermissions();
                } else if (WorkspacePermission.isWorkspacePermission(perm)) {
                        if (info.getWorkspace() != null && info.getUser() != null)
                                permissions = workspaceService.getUserWorkspacePermission(info.getWorkspace().getWid(),
                                                                                          info.getUser().getUid());
                } else {
                        throw new PermissionDeniedException(UserPermission.CreatePermissionRule.getPermission());
                }
                if (!permissions.contains(perm))
                        throw new PermissionDeniedException(perm);
        }

        @Override
        public void requirePermission(UserIdentity identity, Permission perm) throws PermissionDeniedException {
                IdentityInfo info = identitySessionService.getIdentityInfo(identity.getUid());
                testPermission(info, perm);
        }
        
        @Override
        public void requirePermissionChange(UserIdentity identity, Set<Permission> perms) throws PermissionDeniedException {
                IdentityInfo info = identitySessionService.getIdentityInfo(identity.getUid());
                testPermission(info, UserPermission.ChangeUserPermissions.getPermission());
                for (Permission perm: perms)
                        testPermission(info, perm);
        }
        
        @Override
        public Set<Permission> getAllPermissions() {
                Set<Permission> permissions = new HashSet();
                for (WorkspacePermission p: WorkspacePermission.values()) {
                        permissions.add(p.getPermission());
                }
                for (UserPermission p: UserPermission.values()) {
                        permissions.add(p.getPermission());
                }
                return permissions;
        }
        
        @Override
        public Set<Permission> getPermissionsOf(UserIdentity identity) {
                return userService.getIdentityInfo(identity).getPermissions();
        }
        
        @Override
        public Set<Permission> getPermissionsOf(UserIdentity identity, Integer uid) throws ResourceMissingException {
                Workspace workspace = userService.getIdentityInfo(identity).getWorkspace();
                if (workspace == null)
                        throw new ResourceMissingException("Current login doesn't have a valid workspace.");
                Set<Permission> perms = userService.getUserPermissions(uid);
                perms.addAll(workspaceService.getUserWorkspacePermission(workspace.getWid(), uid));
                return perms;
        }
}
