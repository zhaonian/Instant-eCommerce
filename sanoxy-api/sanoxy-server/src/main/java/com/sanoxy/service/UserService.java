
package com.sanoxy.service;

import com.sanoxy.service.exception.DuplicatedUserException;
import com.sanoxy.service.exception.UserNotExistException;
import com.sanoxy.service.util.UserIdentity;
import javax.naming.AuthenticationException;

/**
 * @author davis
 */
public interface UserService {
        
        public void createNew(String userName, String passcode) throws DuplicatedUserException;
        public UserIdentity authenticate(String workspaceName, String userName, String passcode) throws UserNotExistException,
                                                                                                 AuthenticationException;
        public void logout(UserIdentity identity);
}
