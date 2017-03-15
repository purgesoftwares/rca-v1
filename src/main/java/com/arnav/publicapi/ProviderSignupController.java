package com.arnav.publicapi;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.arnav.exceptions.AllPropertyRequiredException;
import com.arnav.exceptions.PasswordDidNotMatchException;
import com.arnav.exceptions.UsernameIsNotAnEmailException;
import com.arnav.model.address.Address;
import com.arnav.model.provider.Provider;
import com.arnav.model.provider.ProviderRequest;
import com.arnav.model.user.User;
import com.arnav.repository.address.AddressRepository;
import com.arnav.repository.provider.ProviderRepository;
import com.arnav.repository.user.UserRepository;

@Path("/provider")
public class ProviderSignupController {
	
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
			throw new AllPropertyRequiredException("Please fill all the required information.");
		}
		
		boolean isValid = checkStringIsEmail(providerRequest.getMainEmail());
		boolean isSecondayEmailValid = false;

		if(providerRequest.getSecondaryEmail() != null && providerRequest.getSecondaryEmail() != ""){
			isSecondayEmailValid = checkStringIsEmail(providerRequest.getSecondaryEmail());
			if(!isSecondayEmailValid){
				throw new UsernameIsNotAnEmailException("Please input correct email type.");
			}
		}
		if(!isValid){
			throw new UsernameIsNotAnEmailException("Please input correct email type.");
		}
 		Provider provider = new Provider();
		
		Address address = new Address();
		address.setAddress1(providerRequest.getAddress());
		address.setCity(providerRequest.getCity());
		address.setCountry(providerRequest.getCountry());
		address.setCreateDate(new Date());
		address.setLastUpdate(new Date());

		if(providerRequest.getLocation()!=null){
			address.setLocation(providerRequest.getLocation());
		}

		address = addressRepository.save(address);
				
		User user = new User();
		
		user.setUsername(providerRequest.getMainEmail());
		user.setCreateDate(new Date());
		user.setLastUpdate(new Date());		
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		
		if(!providerRequest.getPassword().equals(providerRequest.getConfirmPassword())) {
			throw new PasswordDidNotMatchException("Password not match.");
		}
		user.setPassword(encoder.encode(providerRequest.getPassword()));
		user.setEnabled(true);
		user.setRole("SuperAdmin");
		user.setIsActive(true);
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
	
	private boolean checkStringIsEmail(String username) {
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(username);
		boolean matchFound = m.matches();
		if (matchFound) {
		    return true;
		}
		return false;
	}

}
