package com.siv.publicapi;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.siv.exceptions.PasswordDidNotMatchException;
import com.siv.model.user.User;
import com.siv.model.user.UserRequest;
import com.siv.repository.user.UserRepository;

@Path("/public/user")
public class PublicApiController {
	
	@Autowired
	private UserRepository userRepository;
	
	@POST
	@Path("/reset-password")
	@Produces(MediaType.APPLICATION_JSON)
	public User resetPassword(UserRequest userRequest) {
		
		User preUser = userRepository.findByUsername(userRequest.getEmail());
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		
		if(preUser != null) {
			if(userRequest.getNewPassword() != null 
					&& userRequest.getNewPassword().equals(userRequest.getConfirmPassword())) {
				preUser.setPassword(encoder.encode(userRequest.getNewPassword()));
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
	public User forgetPassword(UserRequest userRequest) {
		
		User preUser = userRepository.findByUsername(userRequest.getEmail());
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		
		if(preUser != null) {
			if(userRequest.getNewPassword() != null 
					&& userRequest.getNewPassword().equals(userRequest.getConfirmPassword())) {
				preUser.setPassword(encoder.encode(userRequest.getNewPassword()));
			} else {
				throw new PasswordDidNotMatchException("Password did not match.");
			}
		
		} else  {
			throw new  UsernameNotFoundException("User is not exists.");
		}	
		
		return userRepository.save(preUser);
	}

}
