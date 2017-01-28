package com.arnav.repository.provider;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.arnav.model.provider.ProviderInformation;

import javax.ws.rs.PathParam;

@RepositoryRestResource
public interface ProviderInformationRepository extends CrudRepository<ProviderInformation, String>, MongoRepository<ProviderInformation, String>{

    public ProviderInformation findByProviderId(@PathParam("providerId")String providerId);
}
