package com.arnav.publicapi;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.arnav.exceptions.UserNotFoundException;
import com.arnav.model.FbSignupRequest;
import com.arnav.model.SignupRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.arnav.exceptions.AllPropertyRequiredException;
import com.arnav.exceptions.PasswordDidNotMatchException;
import com.arnav.exceptions.UsernameIsNotAnEmailException;
import com.arnav.model.address.Address;
import com.arnav.model.customer.Customer;
import com.arnav.model.provider.Provider;
import com.arnav.model.provider.ProviderRequest;
import com.arnav.model.publicapi.CustomerRequest;
import com.arnav.model.user.User;
import com.arnav.repository.address.AddressRepository;
import com.arnav.repository.customer.CustomerRepository;
import com.arnav.repository.user.UserRepository;

@Path("/public/customer")
public class PublicCustomerApiController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private AddressRepository addressRepository;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/signup")
	public Customer signup(SignupRequest signupRequest) throws UsernameIsNotAnEmailException {

		if(!(signupRequest.getCpassword() != null
				&& signupRequest.getPassword() != null && signupRequest.getUsername() !=null
				&& signupRequest.getEmail() !=null)){
			throw new AllPropertyRequiredException("Please fill all the required information.");
		}

		boolean isValid = checkStringIsEmail(signupRequest.getEmail());

		if(!isValid){
			throw new UsernameIsNotAnEmailException("Please input correct email type.");
		}
		Customer customer = new Customer();

		User user = new User();

		user.setUsername(signupRequest.getEmail());
		user.setCreateDate(new Date());
		user.setLastUpdate(new Date());
		PasswordEncoder encoder = new BCryptPasswordEncoder();

		if(!signupRequest.getPassword().equals(signupRequest.getCpassword())) {
			throw  new PasswordDidNotMatchException("Password not match.");
		}
		user.setPassword(encoder.encode(signupRequest.getPassword()));
		user.setEnabled(true);
		user.setRole("SuperAdmin");
		user.setIsActive(true);
		userRepository.save(user);

		customer.setUserId(user.getId());
		customer.setFirstName(signupRequest.getUsername());
		customer.setFullName(signupRequest.getUsername());
		customer.setMainEmail(signupRequest.getEmail());
		user.setPassword(signupRequest.getPassword());
		customer.setCreateDate(new Date());
		customer.setLastUpdate(new Date());

		return customerRepository.save(customer);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/fbsignup")
	public String fbsignup(FbSignupRequest signupRequest) throws UsernameIsNotAnEmailException {

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = null;
		JSONObject jsonData = null;

		if(!(signupRequest.getEmail() !=null
				&& signupRequest.getName() !=null)){
			throw new AllPropertyRequiredException("Please fill all the required information.");
		}

		boolean isValid = checkStringIsEmail(signupRequest.getEmail());

		if(!isValid){
			throw new UsernameIsNotAnEmailException("Please input correct email type.");
		}
		System.out.println(signupRequest);
		Customer precustomer = customerRepository.findByMainEmail(signupRequest.getEmail());
		System.out.println(precustomer);

		if(precustomer == null || precustomer.getId().isEmpty()){

			Customer customer = new Customer();

			User user = new User();

			user.setUsername(signupRequest.getEmail());
			user.setCreateDate(new Date());
			user.setLastUpdate(new Date());
			PasswordEncoder encoder = new BCryptPasswordEncoder();
			String password = this.nextSessionId();
			user.setPassword(encoder.encode(password));
			user.setEnabled(true);
			user.setRole("SuperAdmin");
			user.setIsActive(true);
			userRepository.save(user);

			customer.setUserId(user.getId());
			customer.setFirstName(signupRequest.getFirst_name());
			customer.setFullName(signupRequest.getName());
			customer.setMainEmail(signupRequest.getEmail());
			customer.setPassword(password);
			customer.setCreateDate(new Date());
			customer.setLastUpdate(new Date());

			precustomer = customerRepository.save(customer);
		}else {
			if(precustomer.getFacebookId() == null || !precustomer.getFacebookId().equals(signupRequest.getId())){
				precustomer.setFacebookId(signupRequest.getId());
				customerRepository.save(precustomer);
			}
		}

		try {

			httppost = new HttpPost(new URL("http://54.161.216.233:8090" + "/oauth/token").toURI());
			String encoding = Base64Coder.encodeString("test_client:12345");
			httppost.setHeader("Authorization", "Basic " + encoding);

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("grant_type", "password"));
			nameValuePairs.add(new BasicNameValuePair("client_id", "test_client"));
			nameValuePairs.add(new BasicNameValuePair("client_secret", "12345"));
			nameValuePairs.add(new BasicNameValuePair("username", precustomer.getMainEmail()));
			nameValuePairs.add(new BasicNameValuePair("password", precustomer.getPassword()));

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);

			String jsonString = EntityUtils.toString(response.getEntity());

			jsonData = new JSONObject(jsonString);
			return jsonData.get("access_token").toString();

		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (JSONException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (HttpException e) {
			e.printStackTrace();
		}

		return null;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/googlesignup")
	public String googlesignup(FbSignupRequest signupRequest) throws UsernameIsNotAnEmailException {

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = null;
		JSONObject jsonData = null;

		if(!(signupRequest.getEmail() !=null
				&& signupRequest.getName() !=null)){
			throw new AllPropertyRequiredException("Please fill all the required information.");
		}

		boolean isValid = checkStringIsEmail(signupRequest.getEmail());

		if(!isValid){
			throw new UsernameIsNotAnEmailException("Please input correct email type.");
		}
		System.out.println(signupRequest);
		Customer precustomer = customerRepository.findByMainEmail(signupRequest.getEmail());
		System.out.println(precustomer);

		if(precustomer == null || precustomer.getId().isEmpty()){

			Customer customer = new Customer();

			User user = new User();

			user.setUsername(signupRequest.getEmail());
			user.setCreateDate(new Date());
			user.setLastUpdate(new Date());
			PasswordEncoder encoder = new BCryptPasswordEncoder();
			String password= this.nextSessionId();
			user.setPassword(encoder.encode(password));
			user.setEnabled(true);
			user.setRole("SuperAdmin");
			user.setIsActive(true);
			userRepository.save(user);

			customer.setUserId(user.getId());
			customer.setFirstName(signupRequest.getFirst_name());
			customer.setFullName(signupRequest.getName());
			customer.setMainEmail(signupRequest.getEmail());
			customer.setPassword(password);
			customer.setCreateDate(new Date());
			customer.setLastUpdate(new Date());

			precustomer = customerRepository.save(customer);
		}else {
			if(precustomer.getFacebookId() == null || !precustomer.getFacebookId().equals(signupRequest.getId())){
				precustomer.setFacebookId(signupRequest.getId());
				customerRepository.save(precustomer);
			}
		}

		try {

			httppost = new HttpPost(new URL("http://54.161.216.233:8090" + "/oauth/token").toURI());
			String encoding = Base64Coder.encodeString("test_client:12345");
			httppost.setHeader("Authorization", "Basic " + encoding);

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("grant_type", "password"));
			nameValuePairs.add(new BasicNameValuePair("client_id", "test_client"));
			nameValuePairs.add(new BasicNameValuePair("client_secret", "12345"));
			nameValuePairs.add(new BasicNameValuePair("username", precustomer.getMainEmail()));
			nameValuePairs.add(new BasicNameValuePair("password", precustomer.getPassword()));

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);

			String jsonString = EntityUtils.toString(response.getEntity());

			jsonData = new JSONObject(jsonString);
			return jsonData.get("access_token").toString();

		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (JSONException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (HttpException e) {
			e.printStackTrace();
		}

		return null;
	}


	private SecureRandom random = new SecureRandom();

	public String nextSessionId() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 18) {
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/create")
	public Customer create(CustomerRequest customerRequest) throws UsernameIsNotAnEmailException {

		if(!(customerRequest.getAddress() != null && customerRequest.getCity() != null
				&& customerRequest.getConfirmPassword() != null
				&& customerRequest.getPassword() != null && customerRequest.getFirstName() !=null
				&& customerRequest.getCountry() !=null
				&& customerRequest.getMainEmail() !=null)){
			throw new AllPropertyRequiredException("Please fill all the required information.");
		}

		boolean isValid = checkStringIsEmail(customerRequest.getMainEmail());
		if(!isValid){
			throw new UsernameIsNotAnEmailException("Please input correct email type.");
		}
		Customer customer = new Customer();

		Address address = new Address();
		address.setAddress1(customerRequest.getAddress());
		address.setCity(customerRequest.getCity());
		address.setCountry(customerRequest.getCountry());
		address.setCreateDate(new Date());
		address.setLastUpdate(new Date());

		address = addressRepository.save(address);

		User user = new User();

		user.setUsername(customerRequest.getMainEmail());
		user.setCreateDate(new Date());
		user.setLastUpdate(new Date());
		PasswordEncoder encoder = new BCryptPasswordEncoder();

		if(!customerRequest.getPassword().equals(customerRequest.getConfirmPassword())) {
			throw  new PasswordDidNotMatchException("Password not match.");
		}
		user.setPassword(encoder.encode(customerRequest.getPassword()));
		user.setEnabled(true);
		user.setRole("SuperAdmin");
		user.setIsActive(true);
		userRepository.save(user);

		customer.setAddressId(address.getId());
		customer.setUserId(user.getId());
		customer.setFirstName(customerRequest.getFirstName());
		customer.setLastName(customerRequest.getLastName());
		customer.setFullName(customerRequest.getFullName());
		customer.setMainEmail(customerRequest.getMainEmail());
		customer.setCreateDate(new Date());
		customer.setLastUpdate(new Date());

		return customerRepository.save(customer);
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

	@PUT
	@Path("/signup/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Customer update(@PathParam(value="id")String id, CustomerRequest customerRequest) throws UsernameIsNotAnEmailException{
		Customer preCustomer = customerRepository.findOne(id);
		User preUser = userRepository.findOne(preCustomer.getUserId());
		Address address = addressRepository.findOne(preCustomer.getAddressId());

		boolean isValid = false;
		if(customerRequest.getMainEmail() != null){
			isValid = checkStringIsEmail(customerRequest.getMainEmail());
			if(!isValid){
				throw new UsernameIsNotAnEmailException("Please input correct email type.");
			}
		}

		preCustomer.setLastUpdate(new Date());

		if(customerRequest.getAddress() != null){
			address.setAddress1(customerRequest.getAddress());
		} if(customerRequest.getCity() != null){
			address.setCity(customerRequest.getCity());
		} if(customerRequest.getCountry() != null){
			address.setCountry(customerRequest.getCountry());
		} if(customerRequest.getFirstName() !=null) {
			preCustomer.setFirstName(customerRequest.getFirstName());
		} if(customerRequest.getMainEmail() != null){
			preCustomer.setMainEmail(customerRequest.getMainEmail());
			preUser.setUsername(customerRequest.getMainEmail());
		} if(customerRequest.getPassword() != null && customerRequest.getPassword().equals(customerRequest.getConfirmPassword())){
			preCustomer.setPassword(customerRequest.getPassword());
			PasswordEncoder encoder = new BCryptPasswordEncoder();
			preUser.setPassword(encoder.encode(customerRequest.getPassword()));
		} if(customerRequest.getLastName() != null){
			preCustomer.setLastName(customerRequest.getLastName());
		} if(customerRequest.getFullName() != null){
			preCustomer.setFullName(customerRequest.getFullName());
		}

		addressRepository.save(address);
		userRepository.save(preUser);

		return customerRepository.save(preCustomer);
	}

}
