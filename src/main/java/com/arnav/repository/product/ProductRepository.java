package com.arnav.repository.product;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.arnav.model.product.Product;

import javax.ws.rs.PathParam;

@RepositoryRestResource
public interface ProductRepository extends PagingAndSortingRepository<Product, String> {
    
    public List<Product> findAllByProviderId(@PathParam("providerId") String providerId);
}
