package com.siv.controllers.user;

import java.util.Date;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;

import com.siv.model.user.CorporateUser;
import com.siv.repository.user.CorporateUserRepository;

@Path("/corporate-user")
public class CorporateUserController {
	
	@Autowired
	private CorporateUserRepository corporateUserRepository;
	
	@POST
	@Produces("application/json")
	public CorporateUser create(CorporateUser user){
		user.setCreateDate(new Date());
		user.setLastUpdate(new Date());		
		return corporateUserRepository.save(user);		
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public CorporateUser findOne(@PathParam(value="id")String id){
		return corporateUserRepository.findOne(id);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Page<CorporateUser> findAll(Pageable pageble){
		return corporateUserRepository.findAll(pageble);
	}
	
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public CorporateUser update(CorporateUser user){
		user.setLastUpdate(new Date());
		return corporateUserRepository.save(user);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public CorporateUser delete(@PathParam(value="id")String id){
		CorporateUser user = corporateUserRepository.findOne(id);
		corporateUserRepository.delete(user);
		return user;
	}

}
