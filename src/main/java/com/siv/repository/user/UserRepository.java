package com.siv.repository.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.siv.model.user.User;

@RepositoryRestResource
public interface UserRepository extends CrudRepository<User, String>, MongoRepository<User, String>{

	public User findByUsername(String username);

}
