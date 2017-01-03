package com.siv.controllers.user;

import java.util.Date;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.siv.exceptions.PasswordDidNotMatchException;
import com.siv.model.user.User;
import com.siv.repository.user.UserRepository;

@Path("/secured/user")
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@POST
	@Produces("application/json")
	public User create(User user){
		user.setCreateDate(new Date());
		user.setLastUpdate(new Date());	
		
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		
		if(user.getNewPassword() != null) {
			user.setPassword(encoder.encode(user.getNewPassword()));
		}
		user.setEnabled(true);
		user.setRole("SuperAdmin");
		
		return userRepository.save(user);		
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
	public User update(@PathParam(value="id")String id, User user){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		
		User preUser = userRepository.findOne(id);
		user.setId(id);
		user.setCreateDate(preUser.getCreateDate());
		user.setLastUpdate(new Date());
		user.setEnabled(true);
		user.setIsActive(true);
		user.setRole("SuperAdmin");
		
		if(user.getCustomerId() == null) {
			user.setCustomerId(preUser.getCustomerId());
		} else if(user.getUsername() == null){
			user.setUsername(preUser.getUsername());
		} else if(user.getNewPassword() != null) {
			user.setPassword(encoder.encode(user.getNewPassword()));
		} else if(user.getNewPassword() == null) {
			user.setPassword(preUser.getPassword());
		}
		
		return userRepository.save(user);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public User delete(@PathParam(value="id")String id){
		User user = userRepository.findOne(id);
		userRepository.delete(user);
		return user;
	}
	
	@POST
	@Path("/reset-password")
	@Produces(MediaType.APPLICATION_JSON)
	public User resetPassword(@HeaderParam(value="email")String email, 
			@HeaderParam(value="newPassword")String newPassword,
			@HeaderParam(value="confirmPassword")String confirmPassword) {
		
		User preUser = userRepository.findByUsername(email);
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		
		if(preUser != null) {
			if(newPassword != null && newPassword.equals(confirmPassword)) {
				preUser.setPassword(encoder.encode(newPassword));
			} else {
				throw new PasswordDidNotMatchException("Password did not match.");
			}
		
		} else  {
			throw new  UsernameNotFoundException("User is not exists.");
		}
				
		return userRepository.save(preUser);
		
	}
	
	@POST
	@Path("/forget-password")
	@Produces(MediaType.APPLICATION_JSON)
	public User forgetPassword(@HeaderParam(value="email")String email,
			@HeaderParam(value="newPassword")String newPassword,
			@HeaderParam(value="confirmPassword")String confirmPassword) {
		
		User preUser = userRepository.findByUsername(email);
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		
		if(preUser != null) {
			if(newPassword != null && newPassword.equals(confirmPassword)) {
				preUser.setPassword(encoder.encode(newPassword));
			} else {
				throw new PasswordDidNotMatchException("Password did not match.");
			}
		
		} else  {
			throw new  UsernameNotFoundException("User is not exists.");
		}	
		
		return userRepository.save(preUser);
	}

}
