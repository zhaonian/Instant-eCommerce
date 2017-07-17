
package com.sanoxy.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sanoxy.configuration.Constants;
import com.sanoxy.dao.user.User;
import com.sanoxy.dao.user.UserJoinWorkspace;
import com.sanoxy.repository.user.UserJoinWorkspaceRepository;
import com.sanoxy.repository.user.UserRepository;
import com.sanoxy.service.exception.DuplicatedUserException;
import com.sanoxy.service.exception.PermissionDeniedException;
import com.sanoxy.service.exception.UserNotExistException;
import com.sanoxy.service.util.IdentityInfo;
import com.sanoxy.service.util.UserIdentity;
import com.sanoxy.service.util.UserPermission;
import java.io.IOException;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


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
        public void createNew(String userName, String passcode) throws DuplicatedUserException, PermissionDeniedException {
                if (userName.startsWith(Constants.ROOT_USER_PREFIX))
                        throw new PermissionDeniedException(UserPermission.CreateAdministrator.getPermission());
                
                if (userRepository.existsByName(userName))
                        throw new DuplicatedUserException();
                
                String encryptedPasscode = encoder.encode(passcode);
                
                User user;
                try {
                        user = new User(userName, encryptedPasscode, new HashSet<>());
                        userRepository.save(user);
                } catch (JsonProcessingException ex) {
                        Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
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
                                userJoinWorkspaceRepository.findByUserUidAndWorkspaceName(user.getUid(), workspaceName);
                        
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
