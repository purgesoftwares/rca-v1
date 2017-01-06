package com.siv.controllers.provider;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.UserDeniedAuthorizationException;

import com.siv.exceptions.PasswordDidNotMatchException;
import com.siv.exceptions.UsernameIsNotAnEmailException;
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
	public Provider create(ProviderRequest providerRequest) throws UsernameIsNotAnEmailException{
		
		if(!(providerRequest.getAddress() != null && providerRequest.getCity() != null 
				&& providerRequest.getConfirmPassword() != null
				&& providerRequest.getPassword() != null && providerRequest.getContactName() !=null
				&& providerRequest.getCountry() !=null && providerRequest.getProviderName() != null
				&& providerRequest.getMainEmail() !=null)){
			throw new UserDeniedAuthorizationException("Please fill all the required information.");
		}
		
		boolean isValid = checkStringIsEmamil(providerRequest.getMainEmail());
		boolean isSecondayEmailValid = checkStringIsEmamil(providerRequest.getSecondaryEmail());
		if(!isValid || !isSecondayEmailValid){
			throw new UsernameIsNotAnEmailException("Please input correct email type.");
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
	public Provider findOne(@PathParam(value="id")String id){
		return providerRepository.findOne(id);
	}
	
	@PUT
	@Path("/signup/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Provider update(@PathParam(value="id")String id, ProviderRequest providerRequest) throws UsernameIsNotAnEmailException{
		Provider preProduct = providerRepository.findOne(id);
		User preUser = userRepository.findOne(preProduct.getUserId());
		Address address = addressRepository.findOne(preProduct.getAddressId());
		
		boolean isValid = checkStringIsEmamil(providerRequest.getMainEmail());
		boolean isSecondayEmailValid = checkStringIsEmamil(providerRequest.getSecondaryEmail());
		if(!isValid || !isSecondayEmailValid){
			throw new UsernameIsNotAnEmailException("Please input correct email type.");
		}
		
		preProduct.setLastUpdate(new Date());

		if(providerRequest.getAddress() != null){
			address.setAddress1(providerRequest.getAddress());
		} if(providerRequest.getCity() != null){
			address.setCity(providerRequest.getCity());
		} if(providerRequest.getCountry() != null){
			address.setCountry(providerRequest.getCountry());
		} if(providerRequest.getContactName() !=null) {
			preProduct.setContactName(providerRequest.getContactName());
		} if(providerRequest.getProviderName() != null){
			preProduct.setProvider_name(providerRequest.getProviderName());
		} if(providerRequest.getMainEmail() != null){
			preProduct.setMainEmail(providerRequest.getMainEmail());
			preUser.setUsername(providerRequest.getMainEmail());
		} if(providerRequest.getSecondaryEmail() != null){
			preProduct.setSecondaryEmail(providerRequest.getSecondaryEmail());
		} if(providerRequest.getPassword() != null && providerRequest.getPassword().equals(providerRequest.getConfirmPassword())){
			PasswordEncoder encoder = new BCryptPasswordEncoder();
			preUser.setPassword(encoder.encode(providerRequest.getPassword()));
		}
		
		addressRepository.save(address);
		userRepository.save(preUser);
		
		return providerRepository.save(preProduct);
	}

}
