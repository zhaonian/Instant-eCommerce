
package com.sanoxy.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanoxy.configuration.Constants;
import com.sanoxy.dao.user.User;
import com.sanoxy.dao.user.UserJoinWorkspace;
import com.sanoxy.repository.user.UserJoinWorkspaceRepository;
import com.sanoxy.repository.user.UserRepository;
import com.sanoxy.service.exception.DuplicatedUserException;
import com.sanoxy.service.exception.PermissionDeniedException;
import com.sanoxy.service.exception.UserNotExistException;
import com.sanoxy.service.util.IdentityInfo;
import com.sanoxy.service.util.Permission;
import com.sanoxy.service.util.UserIdentity;
import com.sanoxy.service.util.UserPermission;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
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
        ObjectMapper mapper = new ObjectMapper();
        
        private Set<Permission> getBasicPermission() {
                return new HashSet<Permission>() {{ this.add(UserPermission.CreateWorkspace.getPermission()); }};
        }
        
        @Override
        public void createNew(String userName, String passcode) throws DuplicatedUserException, PermissionDeniedException {
                if (userName.startsWith(Constants.ROOT_USER_PREFIX))
                        throw new PermissionDeniedException(UserPermission.CreateAdministrator.getPermission());
                
                if (userRepository.existsByName(userName))
                        throw new DuplicatedUserException();
                
                String encryptedPasscode = encoder.encode(passcode);
                
                User user;
                try {
                        user = new User(userName, encryptedPasscode, getBasicPermission());
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
                        userSessionService.putIdentityInfo(identity.getUid(), new IdentityInfo(user, null, null, user.getUserPermissions()));
                } else {
                        UserJoinWorkspace userJoinWorkspace = 
                                userJoinWorkspaceRepository.findByUserUidAndWorkspaceName(user.getUid(), workspaceName);
                        
                        if (userJoinWorkspace == null)
                                throw new UserNotExistException("User " + userName + " is not part of " + workspaceName);
                        identity = new UserIdentity();
                        Set<Permission> perms = user.getUserPermissions();
                        perms.addAll(userJoinWorkspace.getPermissions());
                        IdentityInfo info = new IdentityInfo(user, null, userJoinWorkspace.getWorkspace(), perms);
                        userSessionService.putIdentityInfo(identity.getUid(), info);
                }
                return identity;
        }

        @Override
        public void logout(UserIdentity identity) {
                userSessionService.removeIdentityInfo(identity.getUid());
        }
        
        @Override
        public Set<Permission> getUserPermissions(Integer uid) {
                String json = userRepository.getUserPermissionsJsonByUid(uid);
                try {
                        return mapper.readValue(json, new TypeReference<Set<Permission>>() {});
                } catch (IOException ex) {
                        Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
                return null;
        }
        
        @Override
        public IdentityInfo getIdentityInfo(UserIdentity identity) {
                return userSessionService.getIdentityInfo(identity.getUid());
        }
}
