package com.siv.config;

import javax.inject.Singleton;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Singleton
@Provider
public class CROSFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        final int ACCESS_CONTROL_MAX_AGE_IN_SECONDS = 12 * 60 * 60;
        MultivaluedMap<String, Object> headers = responseContext.getHeaders();

        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("ACCESS_CONTROL_ALLOW_HEADERS", "origin, content-type, accept, authorization");
        headers.add("ACCESS_CONTROL_ALLOW_CREDENTIALS", "true");
        headers.add("ACCESS_CONTROL_ALLOW_METHODS", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        headers.add("ACCESS_CONTROL_MAX_AGE", ACCESS_CONTROL_MAX_AGE_IN_SECONDS);
        headers.add("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, X-Codingpedia");

    }
}