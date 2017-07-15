
package com.sanoxy.service;

import com.sanoxy.dao.user.User;
import com.sanoxy.service.exception.DuplicatedWorkspaceException;
import com.sanoxy.service.util.IdentityInfo;
import com.sanoxy.service.util.WorkspacePermission;
import java.util.List;
import java.util.Set;

/**
 * @author davis
 */
public interface WorkspaceService {
       public IdentityInfo createNewWorkspace(String name) throws DuplicatedWorkspaceException;
       public void deleteWorkspace(Integer wid);
       public List<User> getWorkspaceUsers(Integer wid);
       public Set<WorkspacePermission> getUserWorkspacePermission(Integer wid, Integer uid);
       public void addUserToWorkspace(Integer wid, Integer uid, Set<WorkspacePermission> perms);
       public void removeUserToWorkspace(Integer wid, Integer uid);
       public void changeUserWorkspacePermission(Integer wid, Integer uid, Set<WorkspacePermission> perms);
}
