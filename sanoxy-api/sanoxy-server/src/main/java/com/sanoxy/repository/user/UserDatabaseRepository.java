
package com.sanoxy.repository.user;

import com.sanoxy.dao.user.UserDatabase;
import org.springframework.data.repository.CrudRepository;

/**
 * @author davis
 */
public interface UserDatabaseRepository extends CrudRepository<UserDatabase, Integer> {
        
        UserDatabase findByDbid(Integer dbid);
        UserDatabase findByName(String name);
}
