package com.siv.repository.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import com.siv.model.user.User;
	
@RepositoryRestResource
public interface UserRepository extends CrudRepository<User, String>, MongoRepository<User, String>{
	
	@Transactional
	public User findByUsername(String username);

}
