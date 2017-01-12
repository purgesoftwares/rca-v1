package com.siv.controllers.country;

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
import com.siv.model.country.Country;
import com.siv.repository.country.CountryRepository;

@Path("/public/country")
public class CountryController {
	
	@Autowired
	private CountryRepository countryRepository;
	
	@POST
	@Produces("application/json")
	public Country create(Country country){
		
		if(country.getName() == null || country.getCountryCode() == null){
			throw new AllPropertyRequiredException();
		}
		return countryRepository.save(country);		
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Country findOne(@PathParam(value="id")String id){
		return countryRepository.findOne(id);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Page<Country> findAll(Pageable pageble){
		
		return new PageImpl<Country>(countryRepository.findAll(pageble).getContent(), pageble, 20);
	}
	
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Country update(@PathParam(value="id")String id, @Valid Country country){
		Country preCountry = countryRepository.findOne(id);
		country.setId(id);
		
		if(country.getName() == null){
			country.setName(preCountry.getName());
		} if(country.getCountryCode() == null) {
			country.setCountryCode(preCountry.getCountryCode());
		}
		return countryRepository.save(country);	
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Country delete(@PathParam(value="id")String id){
		Country country = countryRepository.findOne(id);
		countryRepository.delete(country);
		return country;
	}

}
