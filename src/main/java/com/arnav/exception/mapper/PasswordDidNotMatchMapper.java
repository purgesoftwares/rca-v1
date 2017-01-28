package com.arnav.exception.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.arnav.exceptions.ErrorResponseException;
import com.arnav.exceptions.PasswordDidNotMatchException;

@Provider
public class PasswordDidNotMatchMapper implements ExceptionMapper<PasswordDidNotMatchException>{
	
	@Override
	public Response toResponse(PasswordDidNotMatchException exception) {
		
		return Response.status(404).
				entity(new ErrorResponseException("Password not match.", 
						"Password and Confirm password should be same."))
			      .type(MediaType.APPLICATION_JSON)
			      .build();
	}

}
