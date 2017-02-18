package com.arnav.publicapi;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.arnav.exceptions.PasswordDidNotMatchException;
import com.arnav.exceptions.TokenExpiredException;
import com.arnav.exceptions.UserNotFoundException;
import com.arnav.model.user.User;
import com.arnav.model.user.UserRequest;
import com.arnav.repository.token.PasswordResetTokenRepository;
import com.arnav.repository.user.UserRepository;
import com.arnav.services.EmailService;
import com.arnav.token.PasswordResetToken;

@Path("/public/user")
public class PublicApiController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PasswordResetTokenRepository passwordTokenRepository;
	
	@GET
	@Path("/reset-password/{email:.+}")
	@Produces(MediaType.APPLICATION_JSON)
	public User resetPassword(@PathParam("email") String email,
			@Context HttpServletRequest request) throws MessagingException {
		
		User user = null;
		if (email != null && !email.isEmpty()) {
			user = userRepository.findByUsername(email);
			if(user != null) {
				//create token and url for user via email.
				final String token = UUID.randomUUID().toString();
		        createPasswordResetTokenForUser(user, token);
		        
		        //url to change password of user.
		        //final String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		        final String appUrl = "http://54.161.216.233:4201";
				sendResetTokenEmail(appUrl, token, user);
			
			} else {
				throw new UserNotFoundException("User name is not valid !");
			}
		
		} else {
			throw new UserNotFoundException("User name is not valid !");
		}
		
		return user;
	}
	
	@POST
	@Path("/change-password")
	@Produces(MediaType.APPLICATION_JSON)
	public User changePassword(@QueryParam("id") final String id, 
			@QueryParam("token") final String token, UserRequest userRequest){
		
		final PasswordResetToken passwordToken = passwordTokenRepository.findByToken(token);
		User user = passwordToken.getUser();
		
		if(user != null && user.getId().equals(id)) {
			
			final Calendar cal = Calendar.getInstance();
			
			if ((passwordToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
				//token expire
				throw new TokenExpiredException("Token is expired, Try Again !");
				
			} else {
				PasswordEncoder encoder = new BCryptPasswordEncoder();
   	    		if(userRequest.getNewPassword() != null 
    					&& userRequest.getNewPassword().equals(userRequest.getConfirmPassword())) {
    				user.setPassword(encoder.encode(userRequest.getNewPassword()));
    				userRepository.save(user);
					passwordTokenRepository.delete(passwordToken);
    			} else {
    				throw new PasswordDidNotMatchException("Password did not match.");
    			}
    		
    		} 
        }
		return user;
	}

	@GET
	@Path("/check-token/{id}/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean checkToken(@PathParam("id") final String id,
							  @PathParam("token") final String token){

		PasswordResetToken passwordToken = passwordTokenRepository.findByToken(token);

		if(passwordToken != null) {
			User user = passwordToken.getUser();

			if (user != null && user.getId().equals(id)) {

				final Calendar cal = Calendar.getInstance();

				if ((passwordToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
					//token expire
					return false;

				} else {
					return true;
				}
			}
		}
		return false;
	}

	@GET
	@Path("/check-account/{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean checkAccount(@PathParam("email") final String email){

		User user = userRepository.findByUsername(email);
		if(user != null){
			return true;
		}else{
			return false;
		}
	}

	@GET
	@Path("/get-mail/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getEmail(@PathParam("id") final String id){

		User user = userRepository.findOne(id);
		if(user != null){
			return user.getUsername();
		}else{
			return "";
		}
	}

	private void sendResetTokenEmail(final String contextPath,
			 final String token,
			 final User user) throws MessagingException {

		// URL to include into email sent to user for reset password
		// Including API Version for the process change password token URL 
		//final String url = contextPath + "/public/user/change-password" + "?id=" +
				//user.getId() + "&token=" + token;
		final String url = contextPath + "/reset-password" + "?id=" +
		  user.getId() + "&token=" + token;

		Map<String, String> processData = new HashMap<String, String>();
		
		processData.put("resetLink", url);
		
		System.out.println(processData.get("resetLink"));
		
		emailService.sendMailWithTemplate(user.getUsername(),
				user.getUsername(),   //enter valid email like - "mamta.soni@xtreemsolution.com"
		"Reset Password",
		"ForgetPassword",
		processData);

	}

	public void createPasswordResetTokenForUser(final User user, final String token) {
			final PasswordResetToken myToken = new PasswordResetToken(token, user);
			passwordTokenRepository.deleteAll();
			passwordTokenRepository.save(myToken);
	}
	
	@GET
	@Path("/forget-password/{email:.+}")
	@Produces(MediaType.APPLICATION_JSON)
	public User forgetPassword(UserRequest userRequest, @PathParam("email") String email,
			@Context HttpServletRequest request) throws MessagingException {
		
		User user = null;
		if (email != null && !email.isEmpty()) {
			user = userRepository.findByUsername(email);
			if(user != null) {
				//create token and url for user via email.
				final String token = UUID.randomUUID().toString();
		        createPasswordResetTokenForUser(user, token);
		        final String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
				sendResetTokenEmail(appUrl, token, user);
			
			} else {
				throw new UserNotFoundException("User name is not valid !");
			}
		
		} else {
			throw new UserNotFoundException("User name is not valid !");
		}
		
		return user;
	}

}
