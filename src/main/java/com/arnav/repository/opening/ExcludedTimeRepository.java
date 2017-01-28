package com.arnav.repository.opening;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.arnav.model.openings.ExcludedTime;

import javax.ws.rs.PathParam;

@RepositoryRestResource
public interface ExcludedTimeRepository extends MongoRepository<ExcludedTime, String>, CrudRepository<ExcludedTime, String>{

    public Iterable<ExcludedTime> findByOpeningId(@PathParam("openingId") String openingId);

    public Page<ExcludedTime> findByOpeningId(@PathParam("openingId") String openingId, Pageable pageable);
}
