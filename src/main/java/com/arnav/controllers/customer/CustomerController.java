package com.arnav.controllers.customer;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.arnav.exceptions.AllPropertyRequiredException;
import com.arnav.exceptions.UsernameIsNotAnEmailException;
import com.arnav.model.SignupRequest;
import com.arnav.model.customer.Customer;
import com.arnav.model.user.User;
import com.arnav.repository.customer.CustomerRepository;
import com.arnav.repository.user.UserRepository;

@Path("/secured/customer")
public class CustomerController {
	
	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private UserRepository userRepository;
	
	@POST
	@Produces("application/json")
	public Customer create(Customer customer) throws UsernameIsNotAnEmailException{
		
		if(customer.getFirstName() == null || customer.getMainEmail() == null) {
			throw new AllPropertyRequiredException("First name and main email is required.");
		}
		
		boolean isValid = checkStringIsEmail(customer.getMainEmail());
		if(!isValid){
			throw new UsernameIsNotAnEmailException("Please input correct email type.");
		}
		
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		if(customer.getNewPassword() != null && customer.getConfirmPassword() != null && customer.getNewPassword().equals(customer.getConfirmPassword())) {
			customer.setPassword(encoder.encode(customer.getNewPassword()));
		}
		
		customer.setCreateDate(new Date());
		customer.setLastUpdate(new Date());
		return customerRepository.save(customer);		
	}
	
	private boolean checkStringIsEmail(String username) {
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(username);
		boolean matchFound = m.matches();
		if (matchFound) {
		    return true;
		}
		return false;
	}
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Customer findOne(@PathParam("id") String id){
		return customerRepository.findOne(id);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Page<Customer> findAll(Pageable pageble,@DefaultValue("0") @QueryParam("page") int page,
				@DefaultValue("id") @QueryParam("sort") String sort, 
				@DefaultValue("20") @QueryParam("size") int size){
		final PageRequest page1 = new PageRequest(
				  page, size, new Sort(
						    new Order(sort.equals("id")? Direction.DESC : Direction.ASC, sort)));
		return customerRepository.findAll(page1);

	}
	
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Customer update(@PathParam(value="id")String id, Customer customer){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		
		Customer preCustomer = customerRepository.findOne(id);
		customer.setId(id);
		customer.setCreateDate(preCustomer.getCreateDate());
		customer.setLastUpdate(new Date());
		
		if(customer.getFirstName() == null) {
			customer.setFirstName(preCustomer.getFirstName());
		} if(customer.getLastName() == null) {
			customer.setLastName(preCustomer.getLastName());
		} if(customer.getAddressId() == null) {
			customer.setAddressId(preCustomer.getAddressId());
		} if(customer.getFullName() == null) {
			customer.setFullName(preCustomer.getFullName());
		} if(customer.getMainEmail() == null) {
			customer.setMainEmail(preCustomer.getMainEmail());
		} if(customer.getNewPassword() != null && customer.getConfirmPassword() != null && customer.getNewPassword().equals(customer.getConfirmPassword())) {
			customer.setPassword(encoder.encode(customer.getNewPassword()));
		} if(customer.getNewPassword() == null) {
			customer.setPassword(preCustomer.getPassword());
		}
		
		return customerRepository.save(customer);
	}

	@PUT
	@Path("/update/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Customer updateCustomer(@PathParam(value="id")String id, SignupRequest signupRequest){
		PasswordEncoder encoder = new BCryptPasswordEncoder();

		Customer customer = customerRepository.findOne(id);
		customer.setLastUpdate(new Date());

		if(signupRequest.getUsername() != null) {
			customer.setFirstName(signupRequest.getUsername());
			customer.setFullName(signupRequest.getUsername());
		} if(signupRequest.getEmail() != null) {
			customer.setMainEmail(signupRequest.getEmail());
		} if(signupRequest.getPassword() != null && signupRequest.getCpassword() != null && signupRequest.getPassword()
				.equals(signupRequest.getCpassword())) {
			customer.setPassword(encoder.encode(signupRequest.getPassword()));
			User user = userRepository.findOne(customer.getUserId());
			user.setPassword(customer.getPassword());
			userRepository.save(user);
		}

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
