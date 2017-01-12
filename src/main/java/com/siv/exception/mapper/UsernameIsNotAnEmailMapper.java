package com.siv.exception.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.siv.exceptions.ErrorResponseException;
import com.siv.exceptions.UsernameIsNotAnEmailException;

@Provider
public class UsernameIsNotAnEmailMapper implements ExceptionMapper<UsernameIsNotAnEmailException>{

	@Override
	public Response toResponse(UsernameIsNotAnEmailException exception) {
		
		return Response.status(404).
				entity(new ErrorResponseException("Please input correct email type.", 
						"Input correct email type."))
			      .type(MediaType.APPLICATION_JSON)
			      .build();
	}
}
