package com.arnav.publicapi;

import com.arnav.model.pages.CMSPages;
import com.arnav.repository.pages.CMSPagesRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
/**
 * Created by Shankar on 2/13/2017.
 */
@Path("/pages")
public class PageController {

    @Autowired
    private CMSPagesRepository cmsRepository;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public CMSPages findOne(@PathParam(value="id")String id){
        return cmsRepository.findOne(id);
    }

}

