
package com.sanoxy.repository.user;

import com.sanoxy.dao.user.User;
import com.sanoxy.dao.user.UserJoinWorkspace;
import org.springframework.data.repository.CrudRepository;

/**
 * @author davis
 */
public interface UserJoinWorkspaceRepository extends CrudRepository<User, Integer> {

        public UserJoinWorkspace findUserJoinWorkspaceByUidAndName(Integer uid, String name);
        
}
