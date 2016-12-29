package com.siv.repository.customer;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.siv.model.customer.Customer;

@RepositoryRestResource
public interface CustomerRepository extends CrudRepository<Customer, String>, MongoRepository<Customer, String>{

}
