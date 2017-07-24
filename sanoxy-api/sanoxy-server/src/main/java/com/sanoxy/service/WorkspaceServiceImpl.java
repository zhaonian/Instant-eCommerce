package com.sanoxy.service;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import com.sanoxy.service.util.UserPermission;
import com.sanoxy.service.util.WorkspacePermission;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
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
                for (WorkspacePermission p : WorkspacePermission.values()) {
                        perms.add(p.getPermission());
                }
                return perms;
        }

        public Set<Permission> genFullUserPermissions() {
                Set<Permission> perms = new HashSet();
                for (UserPermission p : UserPermission.values()) {
                        perms.add(p.getPermission());
                }
                return perms;
        }

        @Override
        @Transactional
        public IdentityInfo createNewWorkspace(String name) throws DuplicatedWorkspaceException {
                if (workspaceRepository.existsByName(name)) {
                        throw new DuplicatedWorkspaceException();
                }
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
                
                try {
                        userJoinWorkspaceRepository.save(new UserJoinWorkspace(root, workspace, genFullWorkspacePermissions()));
                } catch (JsonProcessingException ex) {
                        Logger.getLogger(WorkspaceServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                        return null;
                }

                Set<Permission> perms = new HashSet();
                perms.addAll(genFullWorkspacePermissions());
                perms.addAll(genFullUserPermissions());
                return new IdentityInfo(root, passcode, workspace, perms);
        }

        @Override
        public boolean deleteWorkspace(Integer wid) {
                return workspaceRepository.deleteByWid(wid) == 1;
        }

        @Override
        public Collection<User> getWorkspaceUsers(Integer wid) {
                return userJoinWorkspaceRepository.findUserByWorkspaceWid(wid);
        }

        @Override
        public Set<Permission> getUserWorkspacePermission(Integer wid, Integer uid) {
                return userJoinWorkspaceRepository.findByUserUidAndWorkspaceWid(uid, wid).getPermissions();
        }

        @Override
        public boolean addUserToWorkspace(Integer wid, Integer uid, Set<Permission> perms) {
                User user = entityManager.getReference(User.class, uid);
                Workspace workspace = entityManager.getReference(Workspace.class, wid);
                try {
                        return null != userJoinWorkspaceRepository.save(new UserJoinWorkspace(user, workspace, perms));
                } catch (JsonProcessingException ex) {
                        Logger.getLogger(WorkspaceServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
                return false;
        }

        @Override
        public boolean removeUserToWorkspace(Integer wid, Integer uid) {
                return 1 == userJoinWorkspaceRepository.deleteByUserUidAndWorkspaceWid(uid, wid);
        }

        @Override
        public boolean changeUserWorkspacePermission(Integer wid, Integer uid, Set<Permission> perms) {
                return addUserToWorkspace(wid, uid, perms);
        }
}
