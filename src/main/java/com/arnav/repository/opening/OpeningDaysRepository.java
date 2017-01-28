package com.arnav.repository.opening;

import javax.ws.rs.PathParam;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import com.arnav.model.openings.OpeningDays;
import com.arnav.model.openings.OpeningTime;

import java.util.List;

@RepositoryRestResource
public interface OpeningDaysRepository extends MongoRepository<OpeningDays, String>, CrudRepository<OpeningDays, String>{
	
	@Transactional
	public OpeningDays findByProviderIdAndDay(@PathParam("providerId")String providerId, OpeningTime day);

    public List<OpeningDays> findByProviderId(@PathParam("providerId") String providerId);
}
