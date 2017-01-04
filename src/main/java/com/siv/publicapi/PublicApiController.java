package com.siv.publicapi;

import javax.ws.rs.HeaderParam;
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
import com.siv.repository.user.UserRepository;

@Path("/public/user")
public class PublicApiController {
	
	@Autowired
	private UserRepository userRepository;
	
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
