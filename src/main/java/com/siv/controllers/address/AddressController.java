package com.siv.controllers.address;

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
import com.siv.model.address.Address;
import com.siv.repository.address.AddressRepository;

@Path("/address")
public class AddressController {
	
	@Autowired
	private AddressRepository addressRepository;
	
	@POST
	@Produces("application/json")
	public Address create(Address address){
		address.setCreateDate(new Date());
		address.setLastUpdate(new Date());
		return addressRepository.save(address);		
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Address findOne(@PathParam(value="id")String id){
		return addressRepository.findOne(id);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Page<Address> findAll(Pageable pageble){
		return addressRepository.findAll(pageble);
	}
	
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Address update(@PathParam(value="id")String id, Address address){
		
		Address preAddress = addressRepository.findOne(id);
		address.setId(id);
		address.setLastUpdate(new Date());
		address.setCreateDate(preAddress.getCreateDate());
		
		if(address.getAddress1() == null){
			address.setAddress1(preAddress.getAddress1());
		} else if(address.getAddress2() == null) {
			address.setAddress2(preAddress.getAddress2());
		} else if(address.getCity() == null) {
			address.setCity(preAddress.getCity());
		} else if(address.getDistrict() == null) {
			address.setDistrict(preAddress.getDistrict());
		} else if(address.getHomePhone() == null) {
			address.setHomePhone(preAddress.getHomePhone());
		} else if(address.getPostalCode() == null) {
			address.setPostalCode(preAddress.getPostalCode());
		} else if(address.getPhone() == null) {
			address.setPhone(preAddress.getPhone());
		}
		return addressRepository.save(address);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Address delete(@PathParam(value="id")String id){
		Address address = addressRepository.findOne(id);
		addressRepository.delete(address);
		return address;
	}

}
