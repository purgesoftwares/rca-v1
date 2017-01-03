package com.siv.controllers.provider;

import java.util.Date;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.UserDeniedAuthorizationException;
import com.siv.exceptions.PasswordDidNotMatchException;
import com.siv.model.address.Address;
import com.siv.model.provider.Provider;
import com.siv.model.provider.ProviderRequest;
import com.siv.model.user.User;
import com.siv.repository.address.AddressRepository;
import com.siv.repository.provider.ProviderRepository;
import com.siv.repository.user.UserRepository;

@Path("/provider")
public class ProviderController {
	
	@Autowired
	private ProviderRepository providerRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@POST
	@Produces("application/json")
	@Path("/signup")
	public Provider create(ProviderRequest providerRequest){
		
		if(!(providerRequest.getAddress() != null && providerRequest.getCity() != null 
				&& providerRequest.getConfirmPassword() != null
				&& providerRequest.getPassword() != null && providerRequest.getContactName() !=null
				&& providerRequest.getCountry() !=null && providerRequest.getProviderName() != null
				&& providerRequest.getMainEmail() !=null)){
			throw new UserDeniedAuthorizationException("Please fill all the required information.");
		}
		
		Provider provider = new Provider();
		
		Address address = new Address();
		address.setAddress1(providerRequest.getAddress());
		address.setCity(providerRequest.getCity());
		address.setCountry(providerRequest.getCountry());
		address.setCreateDate(new Date());
		address.setLastUpdate(new Date());
		
		address = addressRepository.save(address);
				
		User user = new User();
		
		user.setUsername(providerRequest.getMainEmail());
		user.setCreateDate(new Date());
		user.setLastUpdate(new Date());		
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		
		if(!providerRequest.getPassword().equals(providerRequest.getConfirmPassword())) {
			throw  new PasswordDidNotMatchException("Password not match.");
		}
		user.setPassword(encoder.encode(providerRequest.getPassword()));
		user.setEnabled(true);
		user.setRole("SuperAdmin");
		userRepository.save(user);
		
		provider.setAddressId(address.getId());
		provider.setUserId(user.getId());
		provider.setMainEmail(providerRequest.getMainEmail());
		provider.setSecondaryEmail(providerRequest.getSecondaryEmail());
		provider.setProvider_name(providerRequest.getProviderName());
		provider.setContactName(providerRequest.getContactName());
		provider.setCreateDate(new Date());
		provider.setLastUpdate(new Date());	
		
		return providerRepository.save(provider);		
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Provider findOne(@PathParam(value="id")String id){
		return providerRepository.findOne(id);
	}

}
