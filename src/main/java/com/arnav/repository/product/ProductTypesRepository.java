package com.arnav.repository.product;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.arnav.model.product.ProductTypes;

@RepositoryRestResource
public interface ProductTypesRepository extends MongoRepository<ProductTypes, String>, CrudRepository<ProductTypes, String>{

}
