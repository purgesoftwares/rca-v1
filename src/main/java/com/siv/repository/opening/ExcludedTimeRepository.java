package com.siv.repository.opening;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.siv.model.openings.ExcludedTime;

@RepositoryRestResource
public interface ExcludedTimeRepository extends MongoRepository<ExcludedTime, String>, CrudRepository<ExcludedTime, String>{

}
