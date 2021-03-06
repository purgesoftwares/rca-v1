package com.arnav.controllers.provider;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.arnav.exceptions.UsernameIsNotAnEmailException;
import com.arnav.model.address.Address;
import com.arnav.model.provider.Provider;
import com.arnav.model.provider.ProviderRequest;
import com.arnav.model.user.User;
import com.arnav.repository.address.AddressRepository;
import com.arnav.repository.provider.ProviderRepository;
import com.arnav.repository.user.UserRepository;

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
	public Page<Provider> findAll(Pageable pageble,@DefaultValue("0") @QueryParam("page") int page,
			@DefaultValue("id") @QueryParam("sort") String sort, @DefaultValue("20") @QueryParam("size") int size){
		final PageRequest page1 = new PageRequest(
				  page, size, new Sort(
						    new Order(sort.equals("id")? Direction.DESC : Direction.ASC, sort)));
		return providerRepository.findAll(page1);
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

		System.out.println(providerRequest);

		boolean isValid = false;
		boolean isSecondayEmailValid = false;
		if(providerRequest.getMainEmail() != null){
			 isValid = checkStringIsEmail(providerRequest.getMainEmail());
			 if(!isValid){
					throw new UsernameIsNotAnEmailException("Please input correct email type.");
			}
		}
		
		if(providerRequest.getSecondaryEmail() != null && providerRequest.getSecondaryEmail() != ""){
			isSecondayEmailValid = checkStringIsEmail(providerRequest.getSecondaryEmail());
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
		if(providerRequest.getLocation()!=null){
			address.setLocation(providerRequest.getLocation());
		}
		
		addressRepository.save(address);
		userRepository.save(preUser);
		
		return providerRepository.save(preProduct);
	}

	
}
