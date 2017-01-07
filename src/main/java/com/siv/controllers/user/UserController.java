package com.siv.controllers.user;

import java.util.Date;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.siv.exceptions.NoCurrentProviderException;
import com.siv.model.provider.Provider;
import com.siv.repository.provider.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.siv.model.user.User;
import com.siv.repository.user.UserRepository;

@Path("/secured/user")
public class UserController {
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	private ProviderRepository providerRepository;
	
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
	//@CrossOrigin(origins="http://192.168.1.118:5555/")
	public Page<User> findAll(Pageable pageble){
		System.out.println("fadfdf");
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
		} if(user.getUsername() == null){
			user.setUsername(preUser.getUsername());
		} if(user.getNewPassword() != null) {
			user.setPassword(encoder.encode(user.getNewPassword()));
		} if(user.getNewPassword() == null) {
			user.setPassword(preUser.getPassword());
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

		if(!username.equals("anonymousUser")) {

			provider = providerRepository.findByUserId(activeUser.getId());
		} else {
			throw new NoCurrentProviderException("There is no current provider, please first login.");
		}


		return provider;
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public User delete(@PathParam(value="id")String id){
		User user = userRepository.findOne(id);
		userRepository.delete(user);
		return user;
	}

}
