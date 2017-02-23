package com.arnav.oauth;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.arnav.exceptions.UserNotFoundException;
import com.arnav.model.customer.Customer;
import com.arnav.model.user.SocialLoginRequest;
import com.arnav.repository.customer.CustomerRepository;
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
import org.springframework.web.bind.annotation.RequestBody;
import com.arnav.publicapi.Base64Coder;
import com.arnav.model.user.UserLogin;

@Path("/oauth/token")
public class OauthConfigurationController {
	
	protected URL oauthURL;

	@Autowired
	private CustomerRepository customerRepository;

	@POST
	public String getOauthAccessToken(UserLogin user) throws HttpException {
		
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = null;
		JSONObject jsonData = null;

		try {
			
			httppost = new HttpPost(new URL("http://54.161.216.233:8090" + "/oauth/token").toURI());
			String encoding = Base64Coder.encodeString("test_client:12345");
			httppost.setHeader("Authorization", "Basic " + encoding);
						
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("grant_type", "password"));
			nameValuePairs.add(new BasicNameValuePair("client_id", "test_client"));
			nameValuePairs.add(new BasicNameValuePair("client_secret", "12345"));
			nameValuePairs.add(new BasicNameValuePair("username", user.getUsername()));
			nameValuePairs.add(new BasicNameValuePair("password", user.getPassword()));
			
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
		}
				
		return null;
		
	}

	@POST
	@Path("/client")
	public String getClientOauthAccessToken(UserLogin user) throws UserNotFoundException,HttpException {

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = null;
		JSONObject jsonData = null;

		Customer customer = customerRepository.findByMainEmail(user.getUsername());
		if(customer == null || customer.getId().isEmpty()){
			throw new UserNotFoundException("User not found! Invalid Email or Password!");
		}
		try {

			httppost = new HttpPost(new URL("http://54.161.216.233:8090" + "/oauth/token").toURI());
			String encoding = Base64Coder.encodeString("test_client:12345");
			httppost.setHeader("Authorization", "Basic " + encoding);

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("grant_type", "password"));
			nameValuePairs.add(new BasicNameValuePair("client_id", "test_client"));
			nameValuePairs.add(new BasicNameValuePair("client_secret", "12345"));
			nameValuePairs.add(new BasicNameValuePair("username", user.getUsername()));
			nameValuePairs.add(new BasicNameValuePair("password", user.getPassword()));

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
		}

		return null;

	}

	@POST
	@Path("/fb")
	public String getClientFbToken(SocialLoginRequest user) throws UserNotFoundException,HttpException {

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = null;
		JSONObject jsonData = null;

		Customer customer = customerRepository.findByMainEmail(user.getUsername());
		if(customer == null || customer.getId().isEmpty()){
			if(user.getType().equals("fb")){
				if(!customer.getFacebookId().equals(user.getSocialId())){
					customer.setFacebookId(user.getSocialId());
				}
			}else if(user.getType().equals("google")){
				if(!customer.getGoogleId().equals(user.getSocialId())){
					customer.setGoogleId(user.getSocialId());
				}
			}else
			throw new UserNotFoundException("User not found! Invalid Request!");
		}
		try {

			httppost = new HttpPost(new URL("http://54.161.216.233:8090" + "/oauth/token").toURI());
			String encoding = Base64Coder.encodeString("test_client:12345");
			httppost.setHeader("Authorization", "Basic " + encoding);

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("grant_type", "password"));
			nameValuePairs.add(new BasicNameValuePair("client_id", "test_client"));
			nameValuePairs.add(new BasicNameValuePair("client_secret", "12345"));
			nameValuePairs.add(new BasicNameValuePair("username", user.getUsername()));
			nameValuePairs.add(new BasicNameValuePair("password", customer.getPassword()));

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
		}

		return null;

	}
}
