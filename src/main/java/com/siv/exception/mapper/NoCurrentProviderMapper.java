package com.siv.exception.mapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.siv.exceptions.ErrorResponseException;
import com.siv.exceptions.NoCurrentProviderException;

@Provider
public class NoCurrentProviderMapper implements ExceptionMapper<NoCurrentProviderException>{
	
	@Override
	public Response toResponse(NoCurrentProviderException exception) {
		
		return Response.status(404).
				entity(new ErrorResponseException("No Current Provider.", 
						"There is no current provider, please first login."))
			      .type(MediaType.APPLICATION_JSON)
			      .build();
	}

}
