
package com.sanoxy.service;

import com.sanoxy.service.exception.PermissionDeniedException;
import com.sanoxy.service.util.Permission;
import com.sanoxy.service.util.UserIdentity;
import org.springframework.stereotype.Service;

/**
 * @author davis
 */
@Service
public class SecurityServiceImpl implements SecurityService {

        @Override
        public void requirePermission(UserIdentity identity, Permission perm) throws PermissionDeniedException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
}
