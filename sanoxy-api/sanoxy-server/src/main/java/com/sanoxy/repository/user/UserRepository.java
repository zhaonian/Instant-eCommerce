package com.sanoxy.repository.user;

import com.sanoxy.dao.user.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
        
        public User findById(Integer id);
        public User findByName(String name);
	public boolean existsByName(String name);
        public boolean existsByNameAndId(String name, Integer id);
}