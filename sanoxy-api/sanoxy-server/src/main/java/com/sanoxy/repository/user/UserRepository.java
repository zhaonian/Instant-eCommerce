package com.sanoxy.repository.user;

import com.sanoxy.dao.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Integer>{
	@Query("SELECT COUNT(U) > 0 FROM User U WHERE U.name = (:username)")
	public boolean existsByName(@Param(value = "username") String userName);
	
	@Query("SELECT U FROM User U WHERE U.name = (:username) AND "
			+ "U.salt = (:password)")
	public User findByNameAndPassword(@Param(value = "username") String userName,
					       @Param(value = "password") String password);
        
        @Query("SELECT U FROM User U WHERE U.name = (:username)")
	public User findByName(@Param(value = "username") String userName);
	
	@Query("SELECT COUNT(U) = 1 FROM User U WHERE U.name = (:username) AND "
			+ "U.id = (:id)")
	public boolean existsByNameAndId(@Param(value = "username") String userName,
					       @Param(value = "id") String id);
}