
package com.sanoxy.repository.user;

import com.sanoxy.dao.user.User;
import com.sanoxy.dao.user.UserJoinWorkspace;
import java.util.List;
import org.springframework.data.repository.CrudRepository;


public interface UserJoinWorkspaceRepository extends CrudRepository<UserJoinWorkspace, Integer> {

        public UserJoinWorkspace findByUserUidAndWorkspaceName(Integer uid, String name);
        public UserJoinWorkspace findByUserUidAndWorkspaceWid(Integer uid, Integer wid);
        public List<User> findUserByWorkspaceWid(Integer wid);

        public Long deleteByUserUidAndWorkspaceWid(Integer uid, Integer wid);
}
