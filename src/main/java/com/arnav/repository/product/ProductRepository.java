package com.arnav.repository.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.arnav.model.product.Product;

import javax.ws.rs.PathParam;

@RepositoryRestResource
public interface ProductRepository extends PagingAndSortingRepository<Product, String> {

    public Page<Product> findByProviderId(@PathParam("providerId") String providerId, Pageable pageable);
}
