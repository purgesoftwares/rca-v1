package com.siv.repository.product;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.siv.model.product.Product;

@RepositoryRestResource
public interface ProductRepository extends PagingAndSortingRepository<Product, String> {

}
