package com.siv.exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AllPropertyRequriedMapper implements ExceptionMapper<AllPropertyRequiredException>{

	@Override
	public Response toResponse(AllPropertyRequiredException exception) {
		
		return Response.status(404).
				entity(new ErrorResponseException("Please fill all the required information.", 
						"Fill all the required fields."))
			      .type(MediaType.APPLICATION_JSON)
			      .build();
	}
}
