
package com.sanoxy.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanoxy.configuration.Constants;
import com.sanoxy.dao.user.User;
import com.sanoxy.dao.user.UserJoinWorkspace;
import com.sanoxy.dao.user.Workspace;
import com.sanoxy.repository.user.UserJoinWorkspaceRepository;
import com.sanoxy.repository.user.UserRepository;
import com.sanoxy.repository.user.WorkspaceRepository;
import com.sanoxy.service.exception.DuplicatedWorkspaceException;
import com.sanoxy.service.util.IdentityInfo;
import com.sanoxy.service.util.Permission;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class WorkspaceServiceImpl implements WorkspaceService {
        
       @Autowired
       UserRepository userRepository;
       
       @Autowired
       WorkspaceRepository workspaceRepository;
       
       @Autowired
       UserJoinWorkspaceRepository userJoinWorkspaceRepository;
       
       @PersistenceContext
       private EntityManager entityManager;
       
       PasswordEncoder encoder = new BCryptPasswordEncoder();
       
       public Set<Permission> genFullWorkspacePermissions() {
               Set<Permission> perms = new HashSet();
               return perms;
       }
       
       public Set<Permission> genFullUserPermissions() {
               Set<Permission> perms = new HashSet();
               return perms;
       }

       @Override
       public IdentityInfo createNewWorkspace(String name) throws DuplicatedWorkspaceException {
               if (workspaceRepository.existsByName(name))
                       throw new DuplicatedWorkspaceException();
               Workspace workspace = new Workspace(name);
               workspaceRepository.save(workspace);
               
               String userName = Constants.ROOT_USER_PREFIX + name;
               String passcode = UUID.randomUUID().toString();
               User root;
               try {
                       root = new User(userName, encoder.encode(passcode), genFullUserPermissions());
               } catch (JsonProcessingException ex) {
                       Logger.getLogger(WorkspaceServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                       return null;
               }
               userRepository.save(root);
               
               ObjectMapper mapper = new ObjectMapper();
               try {
                       return new IdentityInfo(root, workspace, mapper.writeValueAsString(genFullWorkspacePermissions()));
               } catch (JsonProcessingException ex) {
                       Logger.getLogger(WorkspaceServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
               } catch (IOException ex) {
                       Logger.getLogger(WorkspaceServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
               }
               return null;
       }
       
       @Override
       public void deleteWorkspace(Integer wid) {
               workspaceRepository.deleteByWid(wid);
       }
       
       @Override
       public List<User> getWorkspaceUsers(Integer wid) {
               return userJoinWorkspaceRepository.findUserByWorkspaceWid(wid);
       }
       
       @Override
       public Set<Permission> getUserWorkspacePermission(Integer wid, Integer uid) {
               return userJoinWorkspaceRepository.findByUserUidAndWorkspaceWid(uid, wid).getPermissions();
       }
       
       @Override
       public void addUserToWorkspace(Integer wid, Integer uid, Set<Permission> perms) {
               User user = entityManager.getReference(User.class, uid);
               Workspace workspace = entityManager.getReference(Workspace.class, wid);
               try {
                       userJoinWorkspaceRepository.save(new UserJoinWorkspace(user, workspace, perms));
               } catch (JsonProcessingException ex) {
                       Logger.getLogger(WorkspaceServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
               }
       }
       
       @Override
       public void removeUserToWorkspace(Integer wid, Integer uid) {
               userJoinWorkspaceRepository.deleteByUserUidAndWorkspaceWid(uid, wid);
       }
       
       @Override
       public void changeUserWorkspacePermission(Integer wid, Integer uid, Set<Permission> perms) {
               addUserToWorkspace(wid, uid, perms);
       }
}
