package com.sanoxy.repository.user;

import com.sanoxy.dao.user.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
        
        public User findByUid(Integer uid);
        public User findByName(String name);
	public boolean existsByName(String name);
        public boolean existsByNameAndUid(String name, Integer uid);
}