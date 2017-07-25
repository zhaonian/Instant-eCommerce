
package com.sanoxy.service;

import com.sanoxy.service.exception.PermissionDeniedException;
import com.sanoxy.service.exception.ResourceMissingException;
import com.sanoxy.service.util.Permission;
import com.sanoxy.service.util.UserIdentity;
import java.util.Set;


public interface SecurityService {
        public void requirePermission(UserIdentity identity, Permission perm) throws PermissionDeniedException;
        public void requirePermissionChange(UserIdentity identity, Set<Permission> perms) throws PermissionDeniedException;
        public Set<Permission> getAllPermissions();
        public Set<Permission> getPermissionsOf(UserIdentity identity);
        public Set<Permission> getPermissionsOf(UserIdentity identity, Integer uid) throws ResourceMissingException;
}
