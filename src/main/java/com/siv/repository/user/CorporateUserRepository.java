package com.siv.repository.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.siv.model.user.CorporateUser;

@RepositoryRestResource
public interface CorporateUserRepository extends MongoRepository<CorporateUser, String>, CrudRepository<CorporateUser, String> {

}
