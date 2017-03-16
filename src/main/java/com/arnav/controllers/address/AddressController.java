package com.arnav.controllers.address;

import java.util.Date;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.arnav.exceptions.CustomNotFoundException;
import com.arnav.model.address.CustomerAddressRequest;
import com.arnav.model.customer.Customer;
import com.arnav.repository.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.arnav.model.address.Address;
import com.arnav.repository.address.AddressRepository;

@Path("/secured/address")
public class AddressController {
	
	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private CustomerRepository customerRepository;
	
	@POST
	@Produces("application/json")
	public Address create(Address address){
		address.setCreateDate(new Date());
		address.setLastUpdate(new Date());
		return addressRepository.save(address);		
	}

	@POST
	@Path("/update-customer-location")
	@Produces("application/json")
	public Address updateCustomerLocation(CustomerAddressRequest addressRequest) throws CustomNotFoundException {

		if(addressRequest.getCustomerId() == null || addressRequest.getAddress() == null)
			throw new CustomNotFoundException("All Proper values not found!");

		Customer customer = customerRepository.findOne(addressRequest.getCustomerId());

		if(customer == null)
			throw new CustomNotFoundException("Not a valid request!");

		if(customer.getAddressId()==null){

			Address address = addressRequest.getAddress();
			address.setCreateDate(new Date());
			address.setLastUpdate(new Date());
			address = addressRepository.save(address);
			customer.setAddressId(address.getId());
			customerRepository.save(customer);
			return address;

		}else{

			Address address = addressRepository.findOne(customer.getAddressId());

			if(addressRequest.getAddress().getCity() != null)
				address.setCity(addressRequest.getAddress().getCity());

			if(addressRequest.getAddress().getLocation() != null)
				address.setLocation(addressRequest.getAddress().getLocation());

			if(addressRequest.getAddress().getAddress1() != null)
				address.setAddress1(addressRequest.getAddress().getAddress1());

			if(addressRequest.getAddress().getCountry() != null)
				address.setCountry(addressRequest.getAddress().getCountry());

			address.setCreateDate(new Date());
			address.setLastUpdate(new Date());
			return addressRepository.save(address);
		}

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
		} if(address.getAddress2() == null) {
			address.setAddress2(preAddress.getAddress2());
		} if(address.getCity() == null) {
			address.setCity(preAddress.getCity());
		} if(address.getDistrict() == null) {
			address.setDistrict(preAddress.getDistrict());
		} if(address.getHomePhone() == null) {
			address.setHomePhone(preAddress.getHomePhone());
		} if(address.getPostalCode() == null) {
			address.setPostalCode(preAddress.getPostalCode());
		} if(address.getPhone() == null) {
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
