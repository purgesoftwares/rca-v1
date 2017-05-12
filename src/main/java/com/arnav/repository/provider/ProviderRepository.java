package com.arnav.repository.provider;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import com.arnav.model.provider.Provider;

@RepositoryRestResource
public interface ProviderRepository extends MongoRepository<Provider, String>, CrudRepository<Provider, String>, PagingAndSortingRepository<Provider, String>{

	@Transactional
	public Provider findByUserId(String userId);
	
	@Transactional
	public void deleteByUserId(String userId);

	@Transactional
    public Provider findByMainEmail(String username);
}
