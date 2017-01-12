package com.siv.exception.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.siv.exceptions.ErrorResponseException;
import com.siv.exceptions.RequestedIdIsNotExists;

@Provider
public class RequestedIdIsNotExistsMapper implements ExceptionMapper<RequestedIdIsNotExists>{

	@Override
	public Response toResponse(RequestedIdIsNotExists exception) {
		
		return Response.status(404).
				entity(new ErrorResponseException("Requested Id is not exists.", 
						"Requested Id is not exists."))
			      .type(MediaType.APPLICATION_JSON)
			      .build();
	}
}
