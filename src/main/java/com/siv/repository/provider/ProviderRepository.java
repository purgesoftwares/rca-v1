package com.siv.repository.provider;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.siv.model.provider.Provider;

@RepositoryRestResource
public interface ProviderRepository extends CrudRepository<Provider, String>, MongoRepository<Provider, String> {

}
