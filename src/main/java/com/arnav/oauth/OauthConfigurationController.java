package com.arnav.oauth;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

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
import org.springframework.web.bind.annotation.RequestBody;
import com.arnav.publicapi.Base64Coder;
import com.arnav.model.user.UserLogin;

@Path("/oauth/token")
public class OauthConfigurationController {
	
	protected URL oauthURL;	
	
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
}
