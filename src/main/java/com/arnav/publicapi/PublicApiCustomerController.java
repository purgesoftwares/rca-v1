package com.arnav.publicapi;

import javax.mail.MessagingException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.arnav.exceptions.PasswordDidNotMatchException;
import com.arnav.model.customer.Customer;
import com.arnav.model.user.UserRequest;
import com.arnav.repository.customer.CustomerRepository;

@Path("/public/customer")
public class PublicApiCustomerController {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@POST
	@Path("/reset-password")
	@Produces(MediaType.APPLICATION_JSON)
	public Customer resetPassword(UserRequest userRequest) throws MessagingException {
		
		Customer preCustomer = customerRepository.findByMainEmail(userRequest.getEmail());
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		
		if(preCustomer != null) {
			if(userRequest.getNewPassword() != null 
					&& userRequest.getNewPassword().equals(userRequest.getConfirmPassword())) {
				preCustomer.setPassword(encoder.encode(userRequest.getNewPassword()));
			} else {
				throw new PasswordDidNotMatchException("Password did not match.");
			}
		
		} else  {
			throw new  UsernameNotFoundException("User is not exists.");
		}
				
		return customerRepository.save(preCustomer);
		
	}

}
