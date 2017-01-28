package com.arnav.exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.dao.DuplicateKeyException;

@Provider
public class DuplicateKeyFoundMapper implements ExceptionMapper<DuplicateKeyException>{

	@Override
	public Response toResponse(DuplicateKeyException exception) {
		
		return Response.status(404).
				entity(new ErrorResponseException("Duplicate Record found, please choose different one.", "Duplicate Email found, please choose different one."))
			      .type(MediaType.APPLICATION_JSON)
			      .build();
	}

}
