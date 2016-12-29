package com.siv.controllers.provider;

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
import com.siv.model.provider.ProviderInformation;
import com.siv.repository.provider.ProviderInformationRepository;


@Path("/provider-information")
public class ProviderInformationController {
	
	@Autowired
	private ProviderInformationRepository providerInformationRepository;
	
	@POST
	@Produces("application/json")
	public ProviderInformation create(ProviderInformation providerInformation){
		providerInformation.setCreateDate(new Date());
		providerInformation.setLastUpdate(new Date());		
		return providerInformationRepository.save(providerInformation);		
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ProviderInformation findOne(@PathParam(value="id")String id){
		return providerInformationRepository.findOne(id);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Page<ProviderInformation> findAll(Pageable pageble){
		return providerInformationRepository.findAll(pageble);
	}
	
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ProviderInformation update(ProviderInformation providerInformation){
		providerInformation.setLastUpdate(new Date());
		return providerInformationRepository.save(providerInformation);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ProviderInformation delete(@PathParam(value="id")String id){
		ProviderInformation providerInformation = providerInformationRepository.findOne(id);
		providerInformationRepository.delete(providerInformation);
		return providerInformation;
	}

}
