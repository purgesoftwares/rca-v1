package com.arnav.exception.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.arnav.exceptions.ErrorResponseException;
import com.arnav.exceptions.UserNotFoundException;

@Provider
public class UserNotFoundMapper implements ExceptionMapper<UserNotFoundException>{
	
	@Override
	public Response toResponse(UserNotFoundException exception) {
		
		return Response.status(404).
				entity(new ErrorResponseException("User not found.", 
						"This user is not exists, try again."))
			      .type(MediaType.APPLICATION_JSON)
			      .build();
	}

}
