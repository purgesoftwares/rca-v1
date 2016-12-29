package com.siv.repository.address;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.siv.model.address.Address;

@RepositoryRestResource
public interface AddressRepository extends CrudRepository<Address, String>, MongoRepository<Address, String>{

}
