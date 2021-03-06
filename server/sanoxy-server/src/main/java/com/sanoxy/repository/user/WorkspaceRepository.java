
package com.sanoxy.repository.user;

import com.sanoxy.dao.user.Workspace;
import org.springframework.data.repository.CrudRepository;


public interface WorkspaceRepository extends CrudRepository<Workspace, Integer> {
        
        public Workspace findByWid(Integer wid);
        public Workspace findByName(String name);

        public boolean existsByName(String name);

        public Long deleteByWid(Integer wid);
}
