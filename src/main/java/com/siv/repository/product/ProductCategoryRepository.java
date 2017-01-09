package com.siv.repository.product;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.siv.model.product.ProductCategory;

@RepositoryRestResource
public interface ProductCategoryRepository extends MongoRepository<ProductCategory, String>, CrudRepository<ProductCategory, String>{

}
