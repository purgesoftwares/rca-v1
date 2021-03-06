package com.arnav.controllers.user;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.arnav.exceptions.NoCurrentProviderException;
import com.arnav.exceptions.UsernameIsNotAnEmailException;
import com.arnav.model.customer.Customer;
import com.arnav.model.provider.Provider;
import com.arnav.repository.customer.CustomerRepository;
import com.arnav.repository.provider.ProviderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.arnav.model.user.User;
import com.arnav.repository.user.UserRepository;

@Path("/secured/user")
public class UserController {
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	private ProviderRepository providerRepository;
	@Autowired
	private CustomerRepository customerRepository;
	
	@POST
	@Produces("application/json")
	public User create(User user) throws UsernameIsNotAnEmailException{
		user.setCreateDate(new Date());
		user.setLastUpdate(new Date());	
		
		boolean isValid = checkStringIsEmamil(user.getUsername());
		if(!isValid){
			throw new UsernameIsNotAnEmailException("Please input correct email type.");
		}
		
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		if(user.getNewPassword() != null && user.getConfirmPassword() != null && user.getNewPassword().equals(user.getConfirmPassword())) {
			user.setPassword(encoder.encode(user.getNewPassword()));
		}
		user.setEnabled(true);
		user.setRole("SuperAdmin");
		
		return userRepository.save(user);		
	}
	
	private boolean checkStringIsEmamil(String username) {
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
	@Produces(MediaType.APPLICATION_JSON)
	public User findOne(@PathParam(value="id")String id){
		return userRepository.findOne(id);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Page<User> findAll(Pageable pageble){
		return userRepository.findAll(pageble);
	}
	
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public User update(@PathParam(value="id")String id, User user) throws UsernameIsNotAnEmailException{
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		
		boolean isValid = checkStringIsEmamil(user.getUsername());
		if(!isValid){
			throw new UsernameIsNotAnEmailException("Please input correct email type.");
		}
		
		User preUser = userRepository.findOne(id);
		user.setId(id);
		user.setCreateDate(preUser.getCreateDate());
		user.setLastUpdate(new Date());
		user.setEnabled(true);
		user.setRole("SuperAdmin");
		
		if(user.getCustomerId() == null) {
			user.setCustomerId(preUser.getCustomerId());
		} if(user.getUsername() == null){
			user.setUsername(preUser.getUsername());
		} if(user.getNewPassword() != null && user.getConfirmPassword() != null && user.getNewPassword().equals(user.getConfirmPassword())) {
			user.setPassword(encoder.encode(user.getNewPassword()));
		} if(user.getNewPassword() == null) {
			user.setPassword(preUser.getPassword());
		} if(user.getIsActive() == null) {
			user.setIsActive(false);
		}
		
		return userRepository.save(user);
	}

	@GET
	@Path("/current-provider")
	@Produces(MediaType.APPLICATION_JSON)
	public Provider findCurrentProvider(){
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User activeUser = userRepository.findByUsername(username);
		Provider provider = null;

		if(!username.equals("anonymousUser") && activeUser != null) {

			provider = providerRepository.findByUserId(activeUser.getId());
		} else {
			throw new NoCurrentProviderException("There is no current provider, please first login.");
		}


		return provider;
	}

	@GET
	@Path("/current-customer")
	@Produces(MediaType.APPLICATION_JSON)
	public Customer findCurrentCustomer(){
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User activeUser = userRepository.findByUsername(username);
		Customer customer = null;

		if(!username.equals("anonymousUser") && activeUser != null) {

			customer = customerRepository.findByUserId(activeUser.getId());
		} else {
			throw new NoCurrentProviderException("There is no current provider, please first login.");
		}


		return customer;
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public User delete(@PathParam(value="id")String id){
		User user = userRepository.findOne(id);
		userRepository.delete(user);
		providerRepository.deleteByUserId(id);
		return user;
	}
	
	@GET
	@Path("/is-active/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public User isActive(@PathParam(value="username")String username){
		User user = userRepository.findByUsername(username);
		user.setIsActive(true);
		
		return userRepository.save(user);
	}

}
