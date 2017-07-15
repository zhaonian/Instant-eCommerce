
package com.sanoxy.repository.user;

import com.sanoxy.dao.user.Workspace;
import org.springframework.data.repository.CrudRepository;

/**
 * @author davis
 */
public interface WorkspaceRepository extends CrudRepository<Workspace, Integer> {
        
        Workspace findByWid(Integer wid);
        Workspace findByName(String name);
}
