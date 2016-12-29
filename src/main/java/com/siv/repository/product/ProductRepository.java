package com.siv.repository.product;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.siv.model.product.Product;

@RepositoryRestResource
public interface ProductRepository extends CrudRepository<Product, String>, MongoRepository<Product, String>{

}
