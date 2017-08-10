
package com.sanoxy.service;

import com.sanoxy.service.exception.DuplicatedUserException;
import com.sanoxy.service.exception.PermissionDeniedException;
import com.sanoxy.service.exception.UserNotExistException;
import com.sanoxy.service.util.IdentityInfo;
import com.sanoxy.service.util.Permission;
import com.sanoxy.service.util.UserIdentity;
import java.util.Set;
import javax.naming.AuthenticationException;


public interface UserService {
        
        public void createNew(String userName, String passcode) throws DuplicatedUserException, PermissionDeniedException;
        public UserIdentity authenticate(String workspaceName, String userName, String passcode) throws UserNotExistException,
                                                                                                 AuthenticationException;
        public void logout(UserIdentity identity);
        public Set<Permission> getUserPermissions(Integer uid);
        public IdentityInfo getIdentityInfo(UserIdentity identity);
}
