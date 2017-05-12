package com.arnav.repository.product;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.arnav.model.product.ProductCategory;

@RepositoryRestResource
public interface ProductCategoryRepository extends MongoRepository<ProductCategory, String>, CrudRepository<ProductCategory, String>, 
			PagingAndSortingRepository<ProductCategory, String>{

}
