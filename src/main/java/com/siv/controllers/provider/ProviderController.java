package com.siv.controllers.provider;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.siv.exceptions.UsernameIsNotAnEmailException;
import com.siv.model.address.Address;
import com.siv.model.provider.Provider;
import com.siv.model.provider.ProviderRequest;
import com.siv.model.user.User;
import com.siv.repository.address.AddressRepository;
import com.siv.repository.provider.ProviderRepository;
import com.siv.repository.user.UserRepository;

@Path("/secured/provider")
public class ProviderController {
	
	@Autowired
	private ProviderRepository providerRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Page<Provider> findAll(Pageable pageble){
		
		return providerRepository.findAll(pageble);
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
		
		boolean isValid = false;
		boolean isSecondayEmailValid = false;
		if(providerRequest.getMainEmail() != null){
			 isValid = checkStringIsEmamil(providerRequest.getMainEmail());
			 if(!isValid){
					throw new UsernameIsNotAnEmailException("Please input correct email type.");
			}
		}
		
		if(providerRequest.getSecondaryEmail() != null && providerRequest.getSecondaryEmail() != ""){
			 isValid = checkStringIsEmamil(providerRequest.getSecondaryEmail());
			 if(!isSecondayEmailValid){
					throw new UsernameIsNotAnEmailException("Please input correct email type.");
			}
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
