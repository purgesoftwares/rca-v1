package com.arnav.repository.customer;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import com.arnav.model.customer.Customer;

@RepositoryRestResource
public interface CustomerRepository extends CrudRepository<Customer, String>, MongoRepository<Customer, String>{
	
	@Transactional
	public Customer findByMainEmail(String mainEmail);
}
