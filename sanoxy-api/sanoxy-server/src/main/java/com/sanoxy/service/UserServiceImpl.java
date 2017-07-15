
package com.sanoxy.service;

import com.sanoxy.dao.user.User;
import com.sanoxy.dao.user.UserJoinWorkspace;
import com.sanoxy.repository.user.UserJoinWorkspaceRepository;
import com.sanoxy.repository.user.UserRepository;
import com.sanoxy.service.exception.DuplicatedUserException;
import com.sanoxy.service.exception.UserNotExistException;
import com.sanoxy.service.util.IdentityInfo;
import com.sanoxy.service.util.UserIdentity;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author davis
 */
@Service
public class UserServiceImpl implements UserService {
        @Autowired
        private UserRepository userRepository;
        
        @Autowired
        private UserJoinWorkspaceRepository userJoinWorkspaceRepository;
        
        @Autowired
        private IdentitySessionService userSessionService;
        
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        
        @Override
        public void createNew(String userName, String passcode) throws DuplicatedUserException {
                if (userName.startsWith("master_"))
                        throw new DuplicatedUserException();
                
                if (userRepository.existsByName(userName))
                        throw new DuplicatedUserException();
                
                String encryptedPasscode = encoder.encode(passcode);
                
                User user = new User(userName, encryptedPasscode);
                userRepository.save(user);
        }

        @Override
        public UserIdentity authenticate(String workspaceName, String userName, String passcode) throws UserNotExistException,
                                                                                                 AuthenticationException {
                User user = userRepository.findByName(userName);
                if (user == null)
                        throw new UserNotExistException();
                
                if (!encoder.matches(passcode, user.getEncryptedPasscode())) 
                        throw new AuthenticationException("Password does not match");
                
                UserIdentity identity;
                if (workspaceName.equals("imaginarydb")) {
                        identity = new UserIdentity();
                        try {
                                userSessionService.putIdentityInfo(identity.getUid(), new IdentityInfo(user, null, "[]"));
                        } catch (IOException ex) {
                                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                        }
                } else {
                        UserJoinWorkspace userJoinWorkspace = 
                                userJoinWorkspaceRepository.findUserJoinWorkspaceByUidAndName(user.getUid(), workspaceName);
                        
                        if (userJoinWorkspace == null)
                                throw new UserNotExistException("User " + userName + " is not part of " + workspaceName);
                        identity = new UserIdentity();
                        
                        IdentityInfo info = null;
                        try {
                                info = new IdentityInfo(user, userJoinWorkspace.getWorkspace(), userJoinWorkspace.getPermissionsJson());
                        } catch (IOException ex) {
                                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        userSessionService.putIdentityInfo(identity.getUid(), info);
                }
                return identity;
        }

        @Override
        public void logout(UserIdentity identity) {
                userSessionService.removeIdentityInfo(identity.getUid());
        }
        
}
