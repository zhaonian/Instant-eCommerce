
package com.sanoxy.service;

import com.sanoxy.service.exception.PermissionDeniedException;
import com.sanoxy.service.util.Permission;
import com.sanoxy.service.util.UserIdentity;


public interface SecurityService {
        public void requirePermission(UserIdentity identity, Permission perm) throws PermissionDeniedException;
}
