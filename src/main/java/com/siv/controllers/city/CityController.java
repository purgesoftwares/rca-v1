package com.siv.controllers.city;

import javax.validation.Valid;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.siv.exceptions.AllPropertyRequiredException;
import com.siv.model.city.City;
import com.siv.repository.city.CityRepository;

@Path("/public/city")
public class CityController {
	
	@Autowired
	private CityRepository cityRepository;
	
	@POST
	@Produces("application/json")
	public City create(City city){
		
		if(city.getName() == null || city.getCountryCode() == null){
			throw new AllPropertyRequiredException();
		}
		return cityRepository.save(city);		
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public City findOne(@PathParam(value="id")String id){
		return cityRepository.findOne(id);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Page<City> findAll(Pageable pageble){
		
		return new PageImpl<City>(cityRepository.findAll(pageble).getContent(), pageble, 20);
	}
	
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public City update(@PathParam(value="id")String id, @Valid City city){
		City preCity = cityRepository.findOne(id);
		city.setId(id);
		
		if(city.getName() == null){
			city.setName(preCity.getName());
		} if(city.getCountryCode() == null) {
			city.setCountryCode(preCity.getCountryCode());
		}
		return cityRepository.save(city);	
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public City delete(@PathParam(value="id")String id){
		City City = cityRepository.findOne(id);
		cityRepository.delete(City);
		return City;
	}

}
