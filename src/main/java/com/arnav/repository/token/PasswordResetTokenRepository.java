package com.arnav.repository.token;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.arnav.model.user.User;
import com.arnav.token.PasswordResetToken;

@RepositoryRestResource
public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, String>, MongoRepository<PasswordResetToken, String>{

	  PasswordResetToken findByToken(String token);
	  
	  PasswordResetToken findByUser(User user);
}
