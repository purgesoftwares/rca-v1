package com.siv.controllers.customer;

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
import com.siv.model.customer.Customer;
import com.siv.repository.customer.CustomerRepository;

@Path("/customer")
public class CustomerController {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@POST
	@Produces("application/json")
	public Customer create(Customer customer){
		customer.setCreateDate(new Date());
		customer.setLastUpdate(new Date());
		return customerRepository.save(customer);		
	}
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Customer findOne(@PathParam("id") String id){
		return customerRepository.findOne(id);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Page<Customer> findAll(Pageable pageble){
		return customerRepository.findAll(pageble);
	}
	
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Customer update(@PathParam(value="id")String id, Customer customer){
		Customer preCustomer = customerRepository.findOne(id);
		customer.setId(id);
		customer.setCreateDate(preCustomer.getCreateDate());
		customer.setLastUpdate(new Date());
		return customerRepository.save(customer);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Customer delete(@PathParam(value="id")String id){
		Customer customer = customerRepository.findOne(id);
		customerRepository.delete(customer);
		return customer;
	}

}
