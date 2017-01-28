package com.arnav.repository.country;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.arnav.model.country.Country;

@RepositoryRestResource
public interface CountryRepository extends CrudRepository<Country, String>, MongoRepository<Country, String>{

}
