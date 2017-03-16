package com.arnav.exception.mapper;

import com.arnav.exceptions.CustomNotFoundException;
import com.arnav.exceptions.ErrorResponseException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by HP on 3/16/2017.
 */
@Provider
public class CustomNotFoundExceptionMapper  implements ExceptionMapper<CustomNotFoundException> {

    @Override
    public Response toResponse(CustomNotFoundException exception) {

        return Response.status(404).
                entity(new ErrorResponseException(exception.getLocalizedMessage(),
                        exception.getMessage()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
