package com.siv.controllers.opening;

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

import com.siv.exceptions.RequestedIdIsNotExists;
import com.siv.model.openings.OpeningDays;
import com.siv.model.openings.OpeningTime;
import com.siv.model.provider.Provider;
import com.siv.repository.opening.OpeningDaysRepository;
import com.siv.repository.provider.ProviderRepository;

@Path("/secured/opening-day")
public class OpeningDaysController {
	
	@Autowired
	private OpeningDaysRepository openingDaysRepository;
	
	@Autowired
	private ProviderRepository providerRepository;
	
	@POST
	@Produces("application/json")
	public OpeningDays create(OpeningDays days){
		
		Provider provider = providerRepository.findOne(days.getProviderId());
		if(provider == null) {
			throw new RequestedIdIsNotExists("This Provider is not exits,please enter differne one.");
		}

		for(OpeningTime day : days.getDays()) {
			OpeningDays openingDays = new OpeningDays();
			openingDays.setDay(day);
			openingDays.setProviderId(days.getProviderId());
			openingDaysRepository.save(openingDays);
		}
		
		return days;		
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public OpeningDays findOne(@PathParam(value="id")String id){
		return openingDaysRepository.findOne(id);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Page<OpeningDays> findAll(Pageable pageble){
		return openingDaysRepository.findAll(pageble);
	}
	
	@PUT
	@Path("/{providerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public OpeningDays update(@PathParam(value="providerId")String providerId, OpeningDays days){
		OpeningDays preOpenDays = openingDaysRepository.findByProviderIdAndDay(providerId, days.getDay());
		days.setId(preOpenDays.getId());
		
		return openingDaysRepository.save(days);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public OpeningDays delete(@PathParam(value="id")String id){
		OpeningDays days = openingDaysRepository.findOne(id);
		openingDaysRepository.delete(days);
		return days;
	}

}
