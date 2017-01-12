package com.siv.repository.city;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.siv.model.city.City;

@RepositoryRestResource
public interface CityRepository extends CrudRepository<City, String>, MongoRepository<City, String>{

}
