
package com.sanoxy.service;

import com.sanoxy.dao.user.User;
import com.sanoxy.service.exception.DuplicatedWorkspaceException;
import com.sanoxy.service.util.IdentityInfo;
import com.sanoxy.service.util.Permission;
import java.util.Collection;
import java.util.Set;


public interface WorkspaceService {
       public IdentityInfo createNewWorkspace(String name) throws DuplicatedWorkspaceException;
       public boolean deleteWorkspace(Integer wid);
       public Collection<User> getWorkspaceUsers(Integer wid);
       public Set<Permission> getUserWorkspacePermission(Integer wid, Integer uid);
       public boolean addUserToWorkspace(Integer wid, Integer uid, Set<Permission> perms);
       public boolean removeUserToWorkspace(Integer wid, Integer uid);
       public boolean changeUserWorkspacePermission(Integer wid, Integer uid, Set<Permission> perms);
}
