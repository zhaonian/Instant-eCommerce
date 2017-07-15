
package com.sanoxy.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanoxy.dao.user.User;
import com.sanoxy.dao.user.Workspace;
import com.sanoxy.repository.user.UserJoinWorkspaceRepository;
import com.sanoxy.repository.user.UserRepository;
import com.sanoxy.repository.user.WorkspaceRepository;
import com.sanoxy.service.exception.DuplicatedWorkspaceException;
import com.sanoxy.service.util.IdentityInfo;
import com.sanoxy.service.util.Permission;
import com.sanoxy.service.util.WorkspacePermission;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author davis
 */
@Service
public class WorkspaceServiceImpl implements WorkspaceService {
        
       @Autowired
       UserRepository userRepository;
       
       @Autowired
       WorkspaceRepository workspaceRepository;
       
       @Autowired
       UserJoinWorkspaceRepository userWorkspaceRepository;
       
       PasswordEncoder encoder = new BCryptPasswordEncoder();
       
       public Set<Permission> genFullpermission() {
               Set<Permission> perms = new HashSet();
               return perms;
       }

       @Override
       public IdentityInfo createNewWorkspace(String name) throws DuplicatedWorkspaceException {
               if (workspaceRepository.existsByName(name))
                       throw new DuplicatedWorkspaceException();
               Workspace workspace = new Workspace(name);
               workspaceRepository.save(workspace);
               
               String userName = "/root/" + name;
               String passcode = UUID.randomUUID().toString();
               User root = new User(userName, encoder.encode(passcode));
               userRepository.save(root);
               
               ObjectMapper mapper = new ObjectMapper();
               try {
                       return new IdentityInfo(root, workspace, mapper.writeValueAsString(genFullpermission()));
               } catch (JsonProcessingException ex) {
                       Logger.getLogger(WorkspaceServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
               } catch (IOException ex) {
                       Logger.getLogger(WorkspaceServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
               }
               return null;
       }
       
       @Override
       public void deleteWorkspace(Integer wid) {
       }
       
       @Override
       public List<User> getWorkspaceUsers(Integer wid) {
               return null;
       }
       
       @Override
       public Set<WorkspacePermission> getUserWorkspacePermission(Integer wid, Integer uid) {
               return null;
       }
       
       @Override
       public void addUserToWorkspace(Integer wid, Integer uid, Set<WorkspacePermission> perms) {
       }
       
       @Override
       public void removeUserToWorkspace(Integer wid, Integer uid) {
       }
       
       @Override
       public void changeUserWorkspacePermission(Integer wid, Integer uid, Set<WorkspacePermission> perms) {
       }
}
