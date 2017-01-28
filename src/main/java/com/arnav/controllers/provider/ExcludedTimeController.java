package com.arnav.controllers.provider;

import com.arnav.model.openings.ExcludedTime;
import com.arnav.repository.opening.ExcludedTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by Shankar on 1/8/2017.
 */

@Path("/secured/excluded-time")
public class ExcludedTimeController {

    @Autowired
    private ExcludedTimeRepository excludedTimeRepository;

    @POST
    @Produces("application/json")
    public ExcludedTime create(ExcludedTime excludedTime) {
        return excludedTimeRepository.save(excludedTime);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ExcludedTime findOne(@PathParam(value = "id") String id) {
        return excludedTimeRepository.findOne(id);
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Page<ExcludedTime> findAll(Pageable pageble) {
        return excludedTimeRepository.findAll(pageble);
    }

    @GET
    @Path("/get-by-openingid/{openingId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Iterable<ExcludedTime> findByOpeningId(@PathParam(value = "openingId") String openingId) {
        return excludedTimeRepository.findByOpeningId(openingId);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ExcludedTime delete(@PathParam(value = "id") String id) {
        ExcludedTime excludedTime = excludedTimeRepository.findOne(id);
        excludedTimeRepository.delete(excludedTime);
        return excludedTime;
    }
}
