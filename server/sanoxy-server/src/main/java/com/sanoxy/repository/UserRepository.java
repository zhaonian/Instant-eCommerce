package com.sanoxy.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.sanoxy.dao.user.User;

public interface UserRepository extends CrudRepository<User, Integer>{
	@Query("SELECT COUNT(U) > 0 FROM User U WHERE "
			+ "U.name = (:userName)")
	public boolean existsByName(@Param(value = "userName") String userName);
}