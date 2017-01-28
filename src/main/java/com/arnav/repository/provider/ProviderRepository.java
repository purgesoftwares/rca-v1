package com.arnav.repository.provider;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import com.arnav.model.provider.Provider;

@RepositoryRestResource
public interface ProviderRepository extends CrudRepository<Provider, String>, MongoRepository<Provider, String> {

	@Transactional
	public Provider findByUserId(String userId);
	
}
